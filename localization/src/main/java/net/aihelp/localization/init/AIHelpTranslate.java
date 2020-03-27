package net.aihelp.localization.init;

import android.content.Context;


import net.aihelp.localization.base.net.ApiRequest;
import net.aihelp.localization.logic.presenter.InitPresenter;

import java.util.HashMap;

/**
 * Created by JoeLjt on 2020/3/9.
 * Email: lijiateng1219@gmail.com
 * Description: SDK 入口方法，入参上下文和相应的配置信息
 */
public class AIHelpTranslate {

    private Context mContext;

    private InitPresenter mPresenter;

    public void init(Context context, AIHelpConfig config) {

        if (config == null) {
            throw new IllegalArgumentException("AIHelpConfig cannot be null");
        }

        this.mContext = context;

        // 初始化全局配置
        initGlobalConfigs();

        // 加载本地缓存资源 & 初始化操作
        mPresenter.doInit(config);

    }

    private void initGlobalConfigs() {

//        ApiRequest.init(BuildConfig.DEBUG ? ApiRequest.LOCAL_BASE_URL : ApiRequest.RELEASE_BASE_URL);

        ApiRequest.init("http://10.0.20.5:11120");

        mPresenter = new InitPresenter(mContext);

    }

    public void forceUpdate() {
        checkSdkInitStatus();
        if (mPresenter != null) {
            mPresenter.requestTranslateFile();
        }
    }

    public String getPhraseById(String code) {
        checkSdkInitStatus();
        return mPresenter.getStringById(code);
    }

    public HashMap<String, String> getAllPhrase() {
        checkSdkInitStatus();
        return mPresenter.getAllData();
    }

    public String getTranslateFileLocation() {
        checkSdkInitStatus();
        return mPresenter.getCacheLocation();
    }

    // ===================== getInstance =======================

    public static AIHelpTranslate getInstance() {
        return Holder.getInstance();
    }

    private static class Holder {
        private static AIHelpTranslate instance;

        private static AIHelpTranslate getInstance() {
            if (instance == null) {
                instance = new AIHelpTranslate();
            }
            return instance;
        }
    }

    private void checkSdkInitStatus() {
        if (mContext == null) {
            throw new RuntimeException("have you called AIHelpTranslate#init() yet?");
        }
    }

}
