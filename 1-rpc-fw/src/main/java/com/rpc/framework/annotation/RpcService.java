package com.rpc.framework.annotation;

import java.lang.annotation.*;

/**
 * 标记 RPC 接口的实现类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RpcService {
}
