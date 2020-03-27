package net.aihelp.localization.base.net;

import net.aihelp.localization.base.net.interceptor.HeaderInterceptor;
import net.aihelp.localization.data.service.DownloadApi;
import net.aihelp.localization.data.service.InitApi;

public class ApiRequest {

    public static final String REQUEST_LOG_TAG = "request";

    public static final String LOCAL_BASE_URL = "https://local.aihelp.net";
    public static final String TEST_BASE_URL = "https://test.aihelp.net";
    public static final String RELEASE_BASE_URL = "https://aihelp.net";

    private static ApiRequestHelper apiRequestHelper;
    private static InitApi initApi;
    private static DownloadApi downloadApi;

    public static void init(String baseUrl) {
        apiRequestHelper = ApiRequestHelper.getInstance();
        apiRequestHelper.setBaseUrl(baseUrl);
        initApi = apiRequestHelper.createService(InitApi.class);
        downloadApi = apiRequestHelper.createStreamingService(DownloadApi.class);
    }

    public static void refreshUrl(String baseUrl) {
        apiRequestHelper.setBaseUrl(baseUrl);
    }

    public static InitApi getInitApi() {
        return initApi;
    }

    public static DownloadApi getDownloadApi() {
        return downloadApi;
    }

    public static void setDownloadApi(DownloadApi downloadApi) {
        ApiRequest.downloadApi = downloadApi;
    }
}
