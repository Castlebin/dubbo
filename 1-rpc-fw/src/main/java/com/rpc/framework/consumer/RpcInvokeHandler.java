package com.rpc.framework.consumer;


import com.rpc.framework.remote.Invocation;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;

public class RpcInvokeHandler implements InvocationHandler {
    // 不管了，先将请求固定发送到一个socket服务器吧
    private String host = "localhost";
    private Integer port = 5678;
    private Socket socket;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (socket == null || socket.isClosed()) {
            socket = new Socket(host, port);
        }

        try (ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());) {

            Invocation invocation = new Invocation();
            invocation.setInterfaceName(method.getDeclaringClass().getName());
            invocation.setMethodName(method.getName());
            invocation.setParamTypes(method.getParameterTypes());
            invocation.setParams(args);


            out.writeObject(invocation);
            out.flush();

            Object o = in.readObject();

            return o;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
