package com.llv.chapiion.netdemo.net.client;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

public class Utils {
    public static final boolean DEVELOP_MODE = true;
    public static final int LOG_LEVEL;
    public static final String DEVICE_TYPE = "Android";

    static {
        if (DEVELOP_MODE) {
            LOG_LEVEL = Log.DEBUG;
        } else {
            LOG_LEVEL = Log.ERROR;
        }
    }

    public static Display getContextDisplay(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay();
    }

    /**
     * 验证手机格式
     *
     * @param mobiles
     * @return
     * @author azzbcc E-mail: azzbcc@sina.com
     * @version 创建时间：2015年12月22日 下午2:54:53
     */
    public static boolean isMobileNo(String mobiles) {
        /*
         * "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
         * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188、182
         * 联通：130、131、132、152、155、156、185、186
         * 电信：133、153、180、189、177、（1349卫通）
         * 虚拟：170
         */
        String telRegex = "^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0-9])|(18[0-9]))\\d{8}$";
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    public static boolean isUrl(String url) {
        String urlRegex = "^((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\." +
                "[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))" +
                "(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?$";
        if (TextUtils.isEmpty(url)) return false;
        else return url.matches(urlRegex);
    }

    /**
     * @param context
     * @return
     * @Title getAppVersionName
     * @Description 获取APP版本号
     * @author azzbcc EmailNotification: azzbcc@sina.com
     * @date 创建时间 2016年7月27日 上午11:45:22
     **/
    public static int getAppVersionCode(Context context) {
        int versionCode = 0;
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionCode = packageInfo.versionCode;
            if (versionCode == 0) {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    public static String getAppVersionName(Context context) {
        String versionName = "1.0.0";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }
}
