package com.rpc.framework.container;

import com.rpc.framework.annotation.RpcService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ServiceLoader {

    private static final String SPI_CONFIG_PATH = "META-INF/rpc/";

    /**
     * 缓存接口名字和它的实现类的实例，SPI 实现
     */
    public static final Map<String, Object> serivceMap = new HashMap<>();

    public static void load() throws IOException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Enumeration<URL> resources = contextClassLoader.getResources(SPI_CONFIG_PATH);
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            String path = url.getPath();
            File dir = new File(path);
            if (dir.isDirectory() && dir.canRead()) {
                File[] files = dir.listFiles();
                for (File f : files) {
                    // 文件名为接口名
                    String interfaceName = f.getName();
                    try(BufferedReader reader = new BufferedReader(new FileReader(f))) {
                        // 文件内容为实现类的全名
                        String implClassName = reader.readLine();
                        System.out.println(interfaceName + " : " + implClassName);

                        try {
                            // 是否是一个接口
                            Class<?> interfaceClass = contextClassLoader.loadClass(interfaceName);
                            if (!interfaceClass.isInterface()) {
                                return;
                            }

                            // 使用上下文加载器加载实现类
                            Class<?> implClass = contextClassLoader.loadClass(implClassName);

                            // 只会将 加了 @RpcService 注解的类注册为服务
                            if (!implClass.isAnnotationPresent(RpcService.class)) {
                                continue;
                            }

                            // 检查 接口 和 实现类 是否匹配
                            Class<?>[] interfaces = implClass.getInterfaces();
                            boolean match = false;
                            if (interfaces != null && interfaces.length > 0) {
                                for (Class<?> anInterface : interfaces) {
                                    if (anInterface.getName().equals(interfaceName)) {
                                        match = true;
                                        break;
                                    }
                                }
                            }
                            if (!match) {
                                continue;
                            }

                            // 创建一个实例
                            Object implInstance = implClass.newInstance();

                            serivceMap.put(interfaceName, implInstance);
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

}
