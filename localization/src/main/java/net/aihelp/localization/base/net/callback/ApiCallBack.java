package net.aihelp.localization.base.net.callback;

import android.util.Log;

import net.aihelp.localization.base.net.ApiRequest;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class ApiCallBack<T> implements Callback<T> {

    private static final int ERROR_CODE_400 = 400;
    private static final int ERROR_CODE_500 = 500;

    protected abstract void onResponse(T t);

    protected abstract void onError(int code, String msg);

    protected abstract void onHttpFailed(String errMsg);

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        try {

            int httpCode = response.code();
            T body = response.body();
            String errMsg = response.errorBody() != null ? response.errorBody().string() : null;
            if (response.isSuccessful() || httpCode == ERROR_CODE_400 || httpCode == ERROR_CODE_500) {

                if (httpCode == 200) {
                    onResponse(body);
                } else {
                    onError(httpCode, errMsg);
                    Log.e(ApiRequest.REQUEST_LOG_TAG, String.format("Error: %s, %s", httpCode, errMsg));
                }

            } else {
                onError(httpCode, errMsg);
                Log.e(ApiRequest.REQUEST_LOG_TAG, String.format("Error: %s, %s", httpCode, errMsg));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getResponseBodyString(T body) {
        return body == null ? null : body.toString();
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onHttpFailed(t.getMessage());
    }

}
