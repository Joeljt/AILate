package net.aihelp.localization;

import android.content.Context;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import net.aihelp.localization.data.model.AuthConfig;
import net.aihelp.localization.init.AIHelpConfig;
import net.aihelp.localization.init.AIHelpTranslate;

import java.util.Random;

/**
 * Created by JoeLjt on 2020/3/14.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class AIHelpSDK {

    static {
        System.loadLibrary("native-lib");
    }

    private static void init(Context context, String appKey, String hashKey, String lan) {
        AIHelpConfig sdkConfig = new AIHelpConfig.Builder()
                .setTargetLanguage(lan)
                .setAuthConfig(new AuthConfig(appKey, hashKey))
                .build();
        AIHelpTranslate.getInstance().init(context, sdkConfig);
    }

    /**
     * 测试 bintray 同版本更新
     * @return
     */
    public boolean testUpdateBintray() {
        int i = new Random().nextInt(100);
        if (i % 17 == 3) {
            Log.e("AIHelpSDK", "target i is " + i);
            return true;
        }
        String json = "{\n" +
                "\"isrefresh\": \"0\",\n" +
                "\"faqkey\": \"1585302540504\",\n" +
                "\"faqlist\": [ ]\n" +
                "}";

        JSONObject jsonObject = JSONObject.parseObject(json);
        Log.e("AIHelpSDK", jsonObject.toJSONString());

        return false;
    }

    private static String getString(String code) {
        // 测试更新
        return AIHelpTranslate.getInstance().getPhraseById(code);
    }

    private static String getSDKInstance() {
        return AIHelpTranslate.getInstance().toString();
    }

    public native void initFromJNI(Context context, String appKey, String hashKey, String lan);

    public native String getStringFromJNI(String code);

    public native String getInstance();

}
