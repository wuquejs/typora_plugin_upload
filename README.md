****

**本插件是基于markdown编辑器Typora软件写的一个自动上传小插件**



# 功能

- typora自动上传图片到FTP服务器，并自动返回Url



# 使用方法

以我当前使用的版本为例(0.11.13)

运行jar包需要先配置config.properties文件里面的信息

## 方式一

打开路径 —–> 文件------>偏好设置----->图像---->

- 插入图片时…选择上传图片

- 上传服务设置选中CUSTOM Command

- 命令填入如下代码，以我打包好的jar包为例

  ```
  java -cp jar所在的绝对路径.jar cc.wuque.typora_plugin_upload.Main
  ```

![image-20211107163412621](http://ftp.25wz.cn/study/image-20211107163412621.png)



## 方式二

打开路径 —–> 文件------>偏好设置----->图像---->

- 插入图片时…选择其它选项

- 上传服务设置选中CUSTOM Command

- 命令填入如下代码，以我打包好的jar包为例

  ```
  java -cp jar所在的绝对路径.xx.jar  cc.wuque.typora_plugin_upload.Main
  ```

- 在编辑markdown时直接粘贴图片然后右键上传图片即可