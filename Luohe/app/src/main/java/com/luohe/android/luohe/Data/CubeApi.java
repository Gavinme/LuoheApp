package com.luohe.android.luohe.Data;

/**
 * Created by GanQuan on 16/3/5.
 */

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface CubeApi {
    @GET("api_demo/reverse.php")
    Observable<CubeBean> getCube(@Query("str") String str);
    @FormUrlEncoded
    @POST("api_demo/reverse.php")
    Observable<CubeBean>postCube(@Field("str") String str);

}
