package cc.wuque.typora_plugin_upload.upload;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpMode;
import cn.hutool.setting.dialect.Props;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @ClassName FtpUpload
 * @date 2021/11/7 15:07
 * @auth 无缺
 * @Description 上传文件到FTP服务器
 */
public class FtpUpload {
    /**
     * @Author: 无缺
     * @Description: 从config.properties中读取配置信息
     * @DateTime: 2021/11/7 16:51
     */
    static Props props = new Props("config.properties");
    private static final String url = props.getStr("ftp.url");
    private static final String username = props.getStr("ftp.username");
    private static final String password = props.getStr("ftp.password");
    private static final String port = props.getStr("ftp.port");
    private final String resultPath = props.getStr("ftp.resPath");
    private final String path = props.getStr("ftp.path");

    /**
     * @Author: 无缺
     * @Description:
     * @DateTime: 2021/11/7 16:52
     * @Params: fileName 文件名
     * @Return url链接
     */
    public String upload(String fileName, InputStream input){
//        FTPClient ftpClient = getFTPClient();
//        ftpClient.setControlEncoding("UTF-8");

//        try {
//            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
//            ftpClient.enterLocalActiveMode();
//            boolean b2 = ftpClient.makeDirectory(path);
//            boolean b1 = ftpClient.changeWorkingDirectory(path);
//            ftpClient.setBufferSize(1024);
//            boolean b = ftpClient.storeFile(fileName, input);
//            if (!b){
//                System.out.println("上传失败");
//            }
//            input.close();
//            ftpClient.logout();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally {
//            if (ftpClient.isConnected()){
//                try {
//                    ftpClient.disconnect();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        Ftp ftp = new Ftp(url, Integer.parseInt(port), username, password);
        ftp.setMode(FtpMode.Passive);
        String format = DateUtil.format(new Date(), "yyyyMMdd");
        String newPath = path + "/" + format;
//        boolean cd = ftp.cd(path);
        boolean upload = ftp.upload(newPath, fileName, input);
        if (!upload){
            System.out.println("上传失败");
        }
        return resultPath + "/" +newPath +"/"+  fileName;

    }

    /**
     * @Author: 无缺
     * @Description: 创建FTP客户端
     * @DateTime: 2021/11/7 16:53
     * @Return FTP客户端
     */
    public static FTPClient getFTPClient(){
        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(url, Integer.parseInt(port));
            boolean login = ftpClient.login(username, password);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode()) && !login){
                System.out.println("未能成功链接到FTP，用户名或密码错误!");
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }
}
