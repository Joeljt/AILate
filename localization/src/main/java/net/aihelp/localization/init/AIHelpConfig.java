package net.aihelp.localization.init;

import net.aihelp.localization.data.model.AuthConfig;

import net.aihelp.localization.logic.callback.LoadingStateListener;
import net.aihelp.localization.utils.Connectivity.NetworkType;

/**
 * Created by JoeLjt on 2020/3/9.
 * Email: lijiateng1219@gmail.com
 * Description: SDK 配置信息
 */
public class AIHelpConfig {

    public static final String DEFAULT_LAN = "zh_cn";
    public static final int PLATFORM_ANDROID = 2;

    private int platform = PLATFORM_ANDROID;
    private String targetLanguage = DEFAULT_LAN;
    private NetworkType networkType = NetworkType.ALL;
    private long updateInterval = -1;
    private boolean isShowDefaultLoading;
    private AuthConfig authConfig;
    private LoadingStateListener listener;

    public LoadingStateListener getListener() {
        return listener;
    }

    public void setListener(LoadingStateListener listener) {
        this.listener = listener;
    }

    public boolean isShowDefaultLoading() {
        return isShowDefaultLoading;
    }

    public void setShowDefaultLoading(boolean showDefaultLoading) {
        isShowDefaultLoading = showDefaultLoading;
    }

    public int getPlatform() {
        return platform;
    }

    public void setPlatform(int platform) {
        this.platform = platform;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }

    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    public NetworkType getNetworkType() {
        return networkType;
    }

    public void setNetworkType(NetworkType networkType) {
        this.networkType = networkType;
    }

    public long getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(long updateInterval) {
        this.updateInterval = updateInterval;
    }

    public AuthConfig getAuthConfig() {
        return authConfig;
    }

    public void setAuthConfig(AuthConfig authConfig) {
        this.authConfig = authConfig;
    }

    private AIHelpConfig(){}

    public static class Builder{
        private String targetLanguage = DEFAULT_LAN;
        private NetworkType networkType = NetworkType.ALL;
        private long updateInterval = -1;
        private boolean isShowDefaultLoading;
        private AuthConfig authConfig;
        private LoadingStateListener listener;

        public Builder setLoadingStateListener(LoadingStateListener listener) {
            this.listener = listener;
            return this;
        }

        public Builder setShowDefaultLoading(boolean showDefaultLoading) {
            isShowDefaultLoading = showDefaultLoading;
            return this;
        }

        public Builder setTargetLanguage(String targetLanguage) {
            this.targetLanguage = targetLanguage;
            return this;
        }

        public Builder setUpdateInterval(long updateInterval) {
            this.updateInterval = updateInterval;
            return this;
        }

        public Builder setAuthConfig(AuthConfig authConfig) {
            this.authConfig = authConfig;
            return this;
        }

        public AIHelpConfig build() {
            AIHelpConfig aiHelpConfig = new AIHelpConfig();
            aiHelpConfig.setTargetLanguage(targetLanguage);
            aiHelpConfig.setUpdateInterval(updateInterval);
            aiHelpConfig.setAuthConfig(authConfig);
            aiHelpConfig.setPlatform(PLATFORM_ANDROID);
            aiHelpConfig.setListener(listener);
            aiHelpConfig.setShowDefaultLoading(isShowDefaultLoading);
            return aiHelpConfig;
        }

    }

}
