package com.rpc.framework.consumer;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    public <T> T getProxy(Class<T> type) {
        if (!type.isInterface()) {
            throw new IllegalArgumentException();
        }


        return (T)Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{type},
                new RpcInvokeHandler());
    }

}
