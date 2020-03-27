package net.aihelp.localization.base.net.interceptor;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import net.aihelp.localization.init.AIHelpConfig;
import net.aihelp.localization.BuildConfig;
import net.aihelp.localization.utils.DeviceUtil;

import java.io.IOException;

import androidx.annotation.NonNull;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;


/**
 * Created by JoeLjt on 2020/3/11.
 * Email: lijiateng1219@gmail.com
 * Description: Header 拦截器，补充通用头信息
 */
public class HeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request request = chain.request();
        RequestBody requestBody = request.body();
        if (request.method().equals("GET")) {
            String sUrl = request.url().toString();
            if (!sUrl.contains(".json")) {
                int index = sUrl.indexOf("?");
                if (index > 0) {
                    sUrl = sUrl + "&";
                } else {
                    sUrl = sUrl + "?";
                }
                sUrl = sUrl + String.format("deviceId=%s&lan=%s&platform=%s&sdkVersion=%s",
                        DeviceUtil.getDeviceId(), AIHelpConfig.DEFAULT_LAN, AIHelpConfig.PLATFORM_ANDROID, BuildConfig.VERSION_NAME);
                request = request.newBuilder().url(sUrl).build();   //重新构建
            }
        } else if (request.method().equals("POST")) {
            if (requestBody instanceof FormBody) {
                FormBody.Builder builder = new FormBody.Builder();
                FormBody formBody = (FormBody) requestBody;
                for (int i = 0; i < formBody.size(); i++) {    // 如果要对已有的参数做进一步处理可以这样拿到参数
                    builder.addEncoded(formBody.encodedName(i), formBody.encodedValue(i));
                }
                builder.addEncoded("deviceId", DeviceUtil.getDeviceId());  //添加公共参数
                builder.addEncoded("lan", AIHelpConfig.DEFAULT_LAN);
                builder.addEncoded("platform", String.valueOf(AIHelpConfig.PLATFORM_ANDROID));
                builder.addEncoded("sdkVersion", BuildConfig.VERSION_NAME);
                request = request.newBuilder().post(builder.build()).build();  //重新构建
                return chain.proceed(request);
            }

            MediaType mediaType = request.body().contentType();
            if (mediaType != null && "json".equals(mediaType.subtype())) {
                String postJson = bodyToString(request.body());
                if (!TextUtils.isEmpty(postJson)) {
                    try {
                        JSON.parseArray(postJson);
                    } catch (Exception e) {
                        JSONObject jsonObject = JSONObject.parseObject(postJson);
                        jsonObject.put("deviceId", DeviceUtil.getDeviceId());
                        jsonObject.put("lan", AIHelpConfig.DEFAULT_LAN);
                        jsonObject.put("platform", AIHelpConfig.PLATFORM_ANDROID);
                        jsonObject.put("sdkVersion", BuildConfig.VERSION_NAME);
                        RequestBody newBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                                jsonObject.toJSONString());
                        request = request.newBuilder().post(newBody).build();
                    }
                }
            } else {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("deviceId", DeviceUtil.getDeviceId());
                jsonObject.put("lan", AIHelpConfig.DEFAULT_LAN);
                jsonObject.put("platform", AIHelpConfig.PLATFORM_ANDROID);
                jsonObject.put("sdkVersion", BuildConfig.VERSION_NAME);
                RequestBody newBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                        jsonObject.toJSONString());
                request = request.newBuilder().post(newBody).build();
            }
        }
        return chain.proceed(request);
    }

    private String bodyToString(final RequestBody request) {
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if (copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "";
        }
    }

}
