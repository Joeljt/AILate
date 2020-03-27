package net.aihelp.localization.base.net.interceptor;

import android.annotation.SuppressLint;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;

import net.aihelp.localization.base.net.ApiRequest;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

public final class LogInterceptor implements Interceptor {

    @SuppressLint("DefaultLocale")
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        long t1 = System.currentTimeMillis();//请求发起的时间

        String method = request.method();

        Log.e(ApiRequest.REQUEST_LOG_TAG, " ");
        Log.e(ApiRequest.REQUEST_LOG_TAG, " ");
        Log.e(ApiRequest.REQUEST_LOG_TAG, "=============================== Start ==============================");
        Log.e(ApiRequest.REQUEST_LOG_TAG, "Method:" + request.method());
        if ("POST".equals(method)) {
            if (request.body() instanceof FormBody) {
                FormBody body = (FormBody) request.body();
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < body.size(); i++) {
                    jsonObject.put(body.encodedName(i), body.encodedValue(i));
                }

                Log.e(ApiRequest.REQUEST_LOG_TAG, "URL:   " + request.url());
                Log.e(ApiRequest.REQUEST_LOG_TAG, "Params:" + jsonObject.toString());

            }

            // post json
            MediaType mediaType = request.body().contentType();
            if (mediaType != null && "json".equals(mediaType.subtype())) {
                String postJson = bodyToString(request.body());
                Log.e(ApiRequest.REQUEST_LOG_TAG, "URL:   " + request.url());
                Log.e(ApiRequest.REQUEST_LOG_TAG, "Params: " + postJson);
            }

        } else {
            Log.e(ApiRequest.REQUEST_LOG_TAG, "URL:   " + request.url());
        }
        Response response = chain.proceed(request);
        long t2 = System.currentTimeMillis();//收到响应的时间
        //这里不能直接使用response.body().string()的方式输出日志
        //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
        //个新的response给应用层处理
        ResponseBody responseBody = response.peekBody(1024 * 1024);
        Log.e(ApiRequest.REQUEST_LOG_TAG, "Return: " + responseBody.string());
        Log.e(ApiRequest.REQUEST_LOG_TAG, "Request Time: " + (t2 - t1) * 1.0 / 1000 + "s");
        Log.e(ApiRequest.REQUEST_LOG_TAG, "================================ End ===============================");
        Log.e(ApiRequest.REQUEST_LOG_TAG, " ");
        Log.e(ApiRequest.REQUEST_LOG_TAG, " ");
        return response;
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


