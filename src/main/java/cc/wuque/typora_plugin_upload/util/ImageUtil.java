package cc.wuque.typora_plugin_upload.util;

import cn.hutool.core.img.ImgUtil;

import java.awt.*;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author: 无缺
 * @createTime: 2023/12/04 12:51
 * @description: 图片工具类
 */
public class ImageUtil {

	public static void imagePressTest(InputStream input, OutputStream output, String text){
		ImgUtil.pressText(input,
				output,
				text,
				Color.PINK, new Font("黑体",Font.BOLD, 18),
				0,
				0,
				0.6f);

	}

}
