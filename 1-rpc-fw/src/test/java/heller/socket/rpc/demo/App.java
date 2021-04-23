package heller.socket.rpc.demo;

import heller.socket.rpc.demo.api.UploadService;
import com.rpc.framework.consumer.ProxyFactory;
import heller.socket.rpc.demo.api.EchoService;

public class App {
    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        EchoService echoService = proxyFactory.getProxy(EchoService.class);
        String echo = echoService.echo("nice to meet you!");
        System.out.println(echo);

        System.out.println(echoService.echo("哈哈哈哈！"));


        UploadService uploadService = proxyFactory.getProxy(UploadService.class);
        uploadService.upload("c://xx//1.jpg");
        uploadService.upload("d://xxxx.txt");


    }
}
