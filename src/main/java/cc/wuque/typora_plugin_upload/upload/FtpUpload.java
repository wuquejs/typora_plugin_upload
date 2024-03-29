package cc.wuque.typora_plugin_upload.upload;

import cc.wuque.typora_plugin_upload.util.ImageUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.ftp.Ftp;
import cn.hutool.extra.ftp.FtpMode;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.setting.dialect.Props;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.ImageOutputStreamImpl;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
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

//    public String upload(String fileName, InputStream input, OutputStream output){
//
//    }

    /**
     * @Author: 无缺
     * @Description:
     * @DateTime: 2021/11/7 16:52
     * @Params: fileName 文件名
     * @Return url链接
     */
    public String upload(String fileName, InputStream input){
        try {
            Ftp ftp = new Ftp(url, Integer.parseInt(port), username, password);
            ftp.setMode(FtpMode.Passive);
            String format = DateUtil.format(new Date(), "yyyyMMdd");
            String newPath = path + "/" + format;
            File file = FileUtil.newFile(FileUtil.getTmpDirPath() + fileName);
            file.createNewFile();
//        boolean cd = ftp.cd(path);
            OutputStream outputStream = FileUtil.getOutputStream(file);

            ImageUtil.imagePressTest(input,outputStream,"img.wuque.cc");
            BufferedInputStream inputStream = FileUtil.getInputStream(file);


            boolean upload = ftp.upload(newPath, fileName, inputStream);
            if (!upload){
                System.out.println("上传失败");
            }
            FileUtil.del(file);
            return resultPath + "/" +newPath +"/"+  fileName;

        }catch (Exception e){
            return "上传失败:" + JSONUtil.toJsonStr(e);
        }


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
