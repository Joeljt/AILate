package net.aihelp.localization.data.service;

import net.aihelp.localization.data.model.InitData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by JoeLjt on 2020/3/9.
 * Email: lijiateng1219@gmail.com
 * Description:
 */
public interface InitApi {

    @GET("/api/Weblate/Init")
    Call<InitData> init(
            @Query("appkey") String appKey, @Query("hashkey") String hashkey
    );

}
