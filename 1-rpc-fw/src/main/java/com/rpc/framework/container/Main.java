package com.rpc.framework.container;

import java.io.IOException;

/**
 * 服务提供者
 *
 * 启动服务容器，对外提供服务实现，供远程调用
 */
public class Main {
    public static void main(String[] args) throws NoSuchMethodException, IOException, ClassNotFoundException {
        new RpcServer().start();
    }
}
