package com.rpc.framework.container;


import com.rpc.framework.remote.Invocation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcServer {
    private Integer port = 5678;

    public void start() throws IOException, ClassNotFoundException, NoSuchMethodException {
        // 服务注册
        ServiceLoader.load();


        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            try (Socket socket = serverSocket.accept();
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ) {

                Object o = in.readObject();
                if (o instanceof Invocation) {
                    Invocation invocation = (Invocation) o;
                    String interfaceName = invocation.getInterfaceName();
                    String methodName = invocation.getMethodName();
                    Class[] paramTypes = invocation.getParamTypes();
                    Object[] params = invocation.getParams();

                    Object impl = ServiceLoader.serivceMap.get(interfaceName);

                    Method method = impl.getClass().getMethod(methodName, paramTypes);

                    Object invokeResult = method.invoke(impl, params);

                    out.writeObject(invokeResult);
                    out.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
