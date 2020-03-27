package net.aihelp.localization.data.service;

import net.aihelp.localization.data.model.InitData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by JoeLjt on 2020/3/11.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public interface DownloadApi {

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

}
