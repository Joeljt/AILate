package net.aihelp.localization.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by JoeLjt on 2020/3/9.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class Connectivity {

    public static boolean isOnline(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        return networkInfo != null && networkInfo.isConnected();
    }

    public static boolean isTargetNetworkAllowed(Context context, NetworkType targetType) {
        NetworkType currentType = getCurrentNetworkType(context);

        boolean wantAllType = currentType == NetworkType.ALL && targetType != NetworkType.UNKNOWN;
        boolean wantWifi = currentType == NetworkType.WIFI && targetType != NetworkType.WIFI;
        boolean wantCellular = currentType == NetworkType.CELLULAR && targetType != NetworkType.CELLULAR;

        if (wantAllType || wantWifi || wantCellular) {
            return true;
        }

        return false;

    }

    public static NetworkType getCurrentNetworkType(Context context) {
        NetworkInfo networkInfo = getNetworkInfo(context);
        if (networkInfo != null && networkInfo.isConnected()) {
            return NetworkType.fromNetworkInfo(networkInfo.getType());
        }
        return NetworkType.UNKNOWN;
    }

    private static NetworkInfo getNetworkInfo(Context context) {
        ConnectivityManager connManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connManager.getActiveNetworkInfo();
    }

    public enum NetworkType {
        UNKNOWN, ALL, CELLULAR, WIFI;

        public static NetworkType fromNetworkInfo(int type) {
            switch (type) {
                case ConnectivityManager.TYPE_ETHERNET:
                case ConnectivityManager.TYPE_WIFI:
                    return WIFI;
                case ConnectivityManager.TYPE_MOBILE:
                    return CELLULAR;
                default:
                    return UNKNOWN;
            }
        }
    }

}
