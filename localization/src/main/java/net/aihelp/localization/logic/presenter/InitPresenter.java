package net.aihelp.localization.logic.presenter;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import net.aihelp.localization.init.AIHelpConfig;
import net.aihelp.localization.base.net.callback.ApiCallBack;
import net.aihelp.localization.base.net.presenter.BasePresenter;
import net.aihelp.localization.data.model.InitData;
import net.aihelp.localization.base.net.ApiRequest;
import net.aihelp.localization.logic.callback.LoadingStateListener;
import net.aihelp.localization.logic.repo.LocalRepository;
import net.aihelp.localization.logic.recur.RecurringManager;

import java.util.HashMap;

import androidx.work.PeriodicWorkRequest;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by JoeLjt on 2020/3/11.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class InitPresenter extends BasePresenter<LocalRepository> {

    private AIHelpConfig config;
    private String fileUrl;

    private LoadingStateListener mLoadingListener;

    public InitPresenter(Context context) {
        super(context);
    }

    @Override
    public LocalRepository initRepository(Context context) {
        return new LocalRepository(context);
    }

    public void doInit(final AIHelpConfig config) {

        this.config = config;
        registerObserver(config.getListener());

        mRepo.loadLocalData();

        Call<InitData> initCall = ApiRequest.getInitApi()
                .init(config.getAuthConfig().getAppKey(), config.getAuthConfig().getHashKey());

        ApiCallBack<InitData> apiCallBack = new ApiCallBack<InitData>() {
            @Override
            protected void onResponse(InitData initData) {
                fileUrl = initData.getData().getTranslateFile();
                mRepo.saveDataToLocal(initData);
                initRecurringWork();
            }

            @Override
            protected void onError(int code, String msg) {
                Log.e("TAG", String.format("onError-> code: %s, msg: %s", code, msg));
            }

            @Override
            protected void onHttpFailed(String errMsg) {

            }
        };

        if (mLoadingListener != null) {
            if (config.isShowDefaultLoading()) {
                Toast.makeText(mContext, "正在更新配置文件，请稍候...", Toast.LENGTH_SHORT).show();
            }
            mLoadingListener.onPreExecute();
        }

        makeRequest(initCall, apiCallBack);

    }

    private void initRecurringWork() {
        if (config.getUpdateInterval() >= PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS) {
            RecurringManager.setPeriodicUpdates(mContext, config);
        } else {
            RecurringManager.cancel(mContext);
            requestTranslateFile();
        }
    }

    public void requestTranslateFile() {

        if (TextUtils.isEmpty(fileUrl)) {
            return;
        }

        Call<ResponseBody> call = ApiRequest.getDownloadApi()
                .downloadFile(fileUrl);

        ApiCallBack<ResponseBody> apiCallBack = new ApiCallBack<ResponseBody>() {
            @Override
            protected void onResponse(ResponseBody responseBody) {
                mRepo.writeFileToDisk(responseBody.byteStream());
                mRepo.copyJsonToAndroidData();
                if (mLoadingListener != null) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (config.isShowDefaultLoading()) {
                                Toast.makeText(mContext, "配置文件更新成功", Toast.LENGTH_SHORT).show();
                            }
                            mLoadingListener.onDataRetrieved();
                        }
                    });
                }
            }

            @Override
            protected void onError(final int code, final String msg) {
                if (mLoadingListener != null) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (config.isShowDefaultLoading()) {
                                Toast.makeText(mContext, "配置文件更新失败", Toast.LENGTH_SHORT).show();
                            }
                            mLoadingListener.onFailure(new Throwable(String.format("onError-> code: %s, msg: %s", code, msg)));
                        }
                    });
                }
            }

            @Override
            protected void onHttpFailed(final String errMsg) {
                if (mLoadingListener != null) {
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (config.isShowDefaultLoading()) {
                                Toast.makeText(mContext, "配置文件更新失败", Toast.LENGTH_SHORT).show();
                            }
                            mLoadingListener.onFailure(new Throwable(errMsg));
                        }
                    });
                }
            }
        };

        makeRequest(call, apiCallBack);
    }

    public HashMap<String, String> getAllData() {
        return mRepo.getAllData();
    }

    public String getStringById(String code) {
        return mRepo.getStringById(code);
    }

    public void registerObserver(LoadingStateListener listener) {
        mLoadingListener = listener;
    }

    public void unRegisterObserver() {
        mLoadingListener = null;
    }

    public String getCacheLocation() {
        return mRepo.getCacheLocation();
    }
}
