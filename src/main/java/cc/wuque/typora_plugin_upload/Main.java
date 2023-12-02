package cc.wuque.typora_plugin_upload;

import cc.wuque.typora_plugin_upload.upload.FtpUpload;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.log.StaticLog;

import java.io.*;
import java.nio.file.Files;

/**
 * @ClassName Main
 * @date 2021/11/7 15:01
 * @auth 无缺
 * @Description ftp上传入口
 */
public class Main {

    public static void main(String[] args) throws IOException {

        if (args.length == 0) {
            return;
        }
        InputStream inputStream;

        FtpUpload ftpUpload = new FtpUpload();
        HttpResponse response = null;
        String fileName = "";
        for (String filePath : args) {
//            StaticLog.debug("文件路径：{}", filePath);
            if (!new File(filePath).exists()) {
                StaticLog.info(filePath);
                response = HttpRequest.get(filePath)
                        .header("Connection","keep-alive")
                        .execute();
                inputStream = response.bodyStream();
                fileName = RandomUtil.randomString(10) + ".png";
            }else {
                File file = new File(filePath);
//                inputStream = FileUtil.getInputStream(file);
                inputStream = Files.newInputStream(file.toPath());
                // 输出流到控制台
                fileName = RandomUtil.randomString(10) + "_" + file.getName();
//                StaticLog.debug("文件名：{}", file.getName());
//                StaticLog.debug("文件大小：{}", file.length());
//                StaticLog.debug("文件路径：{}", file.getAbsolutePath());
//                StaticLog.debug("文件流:", inputStream.read());
//                FileUtil.writeFromStream(inputStream, new File("/Users/wuque/Desktop/work/code/git/wuque/typora_plugin_upload/src/main/resources/" + file.getName()));
            }
//            String fileName = RandomUtil.randomString(10) + ".png";
//            String fileName = IdUtil.fastSimpleUUID() + ;
            String upload = ftpUpload.upload(fileName, inputStream);

            if (response != null) {
                response.close();
            }
            System.out.println(upload);
        }

    }

    /**
     * @Author: 无缺
     * @Description: 判断文件是否存在
     * @DateTime: 2021/11/7 16:53
     * @Params:
     * @Return
     */
    public static Boolean checkArgs(String[] args) {
        boolean checkResult = true;
        //如果没有传入参数则直接退出程序
        if (args.length == 0) {
            checkResult = false;
        }
        for (String filePath : args) {
            if (!new File(filePath).exists()) {
                checkResult = false;
            }
        }
        return checkResult;
    }

}
