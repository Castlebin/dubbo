package heller.socket.rpc.demo.provider;

import com.rpc.framework.annotation.RpcService;
import heller.socket.rpc.demo.api.UploadService;

@RpcService
public class UploadServiceImpl implements UploadService {
    @Override
    public void upload(String filePath) {
        System.out.println("上传文件成功！+ filePath: " + filePath);
    }
}
