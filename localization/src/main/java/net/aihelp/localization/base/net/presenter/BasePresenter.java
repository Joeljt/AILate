package net.aihelp.localization.base.net.presenter;

import android.content.Context;
import android.util.Log;

import net.aihelp.localization.base.net.ApiRequest;
import net.aihelp.localization.base.net.callback.ApiCallBack;
import net.aihelp.localization.base.repo.BaseRepository;

import retrofit2.Call;

/**
 * Created by JoeLjt on 2020/3/11.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public class BasePresenter<R extends BaseRepository> {

    protected R mRepo;
    protected Context mContext;

    public BasePresenter(Context context) {
        this.mContext = context;
        mRepo = initRepository(context);
    }

    public R initRepository(Context context) {
        return null;
    }

    public <T> void makeRequest(Call<T> call, ApiCallBack<T> callback) {
        call.enqueue(callback);
    }

}
