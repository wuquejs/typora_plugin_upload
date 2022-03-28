package cc.wuque.typora_plugin_upload;

import cc.wuque.typora_plugin_upload.upload.FtpUpload;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.log.StaticLog;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @ClassName Main
 * @date 2021/11/7 15:01
 * @auth 无缺
 * @Description ftp上传入口
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        if (args.length == 0) {
            return;
        }
        InputStream inputStream;
        String fileName = RandomUtil.randomString(10) + ".jpg";
        FtpUpload ftpUpload = new FtpUpload();
        HttpResponse response = null;
        for (String filePath : args) {
            if (!new File(filePath).exists()) {
                StaticLog.info(filePath);
                response = HttpRequest.get(filePath)
                        .header("Connection","keep-alive")
                        .execute();
                inputStream = response.bodyStream();
            }else {
                File file = new File(filePath);
                inputStream = new FileInputStream(file);
            }
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
                //System.out.println(filePath + " 文件不存在");
                checkResult = false;
            }
        }
        return checkResult;
    }

}
