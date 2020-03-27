package net.aihelp.localization.logic.callback;

/**
 * Created by JoeLjt on 2020/3/12.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public interface LoadingStateListener {

    void onPreExecute();

    void onDataRetrieved();

    void onFailure(Throwable throwable);

}
