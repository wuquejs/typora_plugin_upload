package cc.wuque.typora_plugin_upload.upload;

import cn.hutool.setting.dialect.Props;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;

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
        FTPClient ftpClient = getFTPClient();
        ftpClient.setControlEncoding("UTF-8");

        try {
            //ftpClient.storeFile(new String(fileName.getBytes("UTF-8"),"iso-8859-1"), input);
            ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftpClient.enterLocalActiveMode();
            ftpClient.makeDirectory(path);
            ftpClient.changeWorkingDirectory(path);
            ftpClient.setBufferSize(1024);
            ftpClient.storeFile(fileName,input);
            input.close();
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (ftpClient.isConnected()){
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //System.out.println(resultPath + "/wuque/" + fileName);
        return resultPath + "/" +path +"/"+  fileName;

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
            ftpClient.login(username, password);
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())){
                System.out.println("未能成功链接到FTP，用户名或密码错误!");
                ftpClient.disconnect();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftpClient;
    }
}
