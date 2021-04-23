package com.rpc.framework;

import com.rpc.framework.container.RpcServer;

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
