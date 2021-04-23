package heller.socket.rpc.demo.provider;

import com.rpc.framework.annotation.RpcService;
import heller.socket.rpc.demo.api.EchoService;

@RpcService
public class EchoServiceImpl implements EchoService {
    @Override
    public String echo(String echo) {
        return " : " + echo;
    }
}
