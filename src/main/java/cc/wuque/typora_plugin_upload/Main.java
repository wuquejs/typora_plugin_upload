package cc.wuque.typora_plugin_upload;

import cc.wuque.typora_plugin_upload.upload.FtpUpload;

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

    public static void main(String[] args) {

        if (!checkArgs(args)) {
            return;
        }
        FtpUpload ftpUpload = new FtpUpload();

        for (String arg : args) {
            File file = new File(arg);
            try {
                InputStream inputStream = new FileInputStream(file);
                String upload = ftpUpload.upload(file.getName(), inputStream);
                System.out.println(upload);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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
        if (args.length == 0) {
            System.out.println("输入参数不能为空");
            checkResult = false;
        }
        for (String filePath : args) {
            if (!new File(filePath).exists()) {
                System.out.println(filePath + " 文件不存在");
                checkResult = false;
            }
        }
        return checkResult;
    }

}
