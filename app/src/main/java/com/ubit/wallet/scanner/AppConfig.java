package com.ubit.wallet.scanner;

import android.os.Build;
import android.os.Environment;

import com.ubit.wallet.BaseApp;
import com.ubit.wallet.R;

import java.io.File;

/**
 * 一句话功能简述
 * 功能详细描述
 *
 * @author WangJing on 2016/10/10 0010 14:45
 * @e-mail wangjinggm@gmail.com
 * @see [相关类/方法](可选)
 */

public class AppConfig {
    public static final String APP_NAME = BaseApp.getContext().getResources().getString(R.string.app_name);
    public static final String PRODUCT_VERSION = "1";//开发内部版本号
    //App versionName
//    public static final String VERSIONNAME = MyApplication.getInstance().getResources().getString(R.string.app_version_name);
    //帖子里面使用的内部版本号 post
    //地图的初始化默认位置
    //UserAgent配置
    public static final String USERAGENT = ("QianFan;" + AppConfig.APP_NAME + ";" + "Android;Mozilla/5.0;AppleWebkit/533.1;" + Build.MODEL + Build.BRAND + Build.VERSION.SDK_INT + ";").replace(" ", "");

    // sd卡路径
    public static final String SDCARD_PATH = getSDPath();
    // 客户端文件夹路径
    public static final String APP_FOLDER = SDCARD_PATH + File.separator + "ZongHeng" + File.separator;
    // 临时文件存放区--发布本地圈压缩的图片
    public static final String TEMP = APP_FOLDER + "temp" + File.separator;
    // 图片保存路径
    public static final String SAVE_PATH = APP_FOLDER + "images" + File.separator;


    // sd卡路径-图片
    public static final String SDCARD_PATH_PIC = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsolutePath();
    // 客户端文件夹路径-图片
    public static final String APP_FOLDER_PIC = SDCARD_PATH_PIC + File.separator + APP_NAME + File.separator;
    // 临时文件存放区-图片
    public static final String TEMP_PIC = APP_FOLDER_PIC + "temp" + File.separator;
    //裁剪后头像保存路径---只能用来对图片进行操作
    public static final String PERSON_AVATAR_IMAGE_PATH = AppConfig.TEMP_PIC + BaseApp.getContext().getResources().getString(R.string.app_name) + "_face.jpg";

    public static String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取根目录
        } else {
            BaseApp.getContext().getApplicationContext().getCacheDir().getAbsolutePath(); // 获取内置内存卡目录
        }
        return sdDir.toString();
    }

    public static final String ZXING_IMAGE_PATH = AppConfig.TEMP_PIC + BaseApp.getContext().getResources().getString(R.string.app_name) + "_zxing.jpg";
}
