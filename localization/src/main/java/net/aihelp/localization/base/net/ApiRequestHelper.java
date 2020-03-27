package net.aihelp.localization.base.net;

import net.aihelp.localization.base.net.interceptor.HeaderInterceptor;
import net.aihelp.localization.base.net.interceptor.LogInterceptor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiRequestHelper {

    private String BASE_URL = ApiRequest.LOCAL_BASE_URL;

    private static final int DEFAULT_TIMEOUT = 80 * 1000;

    private volatile static ApiRequestHelper INSTANCE;

    private static OkHttpClient okHttpClient;

    private ApiRequestHelper() {
        okHttpClient = buildOkHttpClient();
    }

    public void setBaseUrl(String url) {
        this.BASE_URL = url;
    }

    public String getBaseUrl() {
        return BASE_URL;
    }

    public static ApiRequestHelper getInstance() {

        if (INSTANCE == null) {
            synchronized (ApiRequestHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiRequestHelper();
                }
            }
        }
        return INSTANCE;
    }

    public <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, BASE_URL);
    }

    public <S> S createService(Class<S> serviceClass, String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
        return retrofit.create(serviceClass);
    }

    public <S> S createStreamingService(Class<S> serviceClass) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(executorService)
                .baseUrl(BASE_URL)
                .build();
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient buildOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .cookieJar(new CookieJar() {
                    // OkHttpClient创建时，传入这个CookieJar的实现，就能完成对Cookie的自动管理
                    private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();

                    @Override
                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                        cookieStore.put(url, cookies);
                    }

                    @Override
                    public List<Cookie> loadForRequest(HttpUrl url) {
                        List<Cookie> cookies = cookieStore.get(url);
                        return cookies != null ? cookies : new ArrayList<Cookie>();
                    }
                })
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new LogInterceptor());
        return builder.build();

    }

    public void setOkHttpClient(OkHttpClient okHttpClient) {
        ApiRequestHelper.okHttpClient = okHttpClient;
    }

    public OkHttpClient getOkHttpClient() {
        return okHttpClient;
    }
}

