package net.aihelp.localization.data.model;

import android.os.Build;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

/**
 * Created by JoeLjt on 2020/3/9.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class AuthConfig {

    private String appKey;
    private String hashKey;

    public AuthConfig(String appKey, String hashKey) {
        if (appKey == null || hashKey == null) {
            throw new IllegalArgumentException("appKey and hashKey can not be null");
        }
        this.appKey = appKey;
        this.hashKey = hashKey;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
