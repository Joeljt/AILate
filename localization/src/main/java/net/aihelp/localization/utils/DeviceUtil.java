package net.aihelp.localization.utils;

import java.lang.reflect.Method;

/**
 * Created by JoeLjt on 2020/3/11.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class DeviceUtil {

    public static String getDeviceId() {
        try {
            Class c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class);
            return (String) get.invoke(c, "ro.serialno", "unknown");
        } catch (Exception e) { }
        return "";
    }

}
