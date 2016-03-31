package com.luohe.android.luohe.common;

import android.util.Log;

import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSink;
import okio.Okio;
import okio.Sink;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by GanQuan on 16/3/5.
 */
public class RetrofitImp {

    protected static Retrofit mRetrofit;

    public static Retrofit getRetrofitInstance() {
        if (mRetrofit == null) {
            initClient();
        }
        return mRetrofit;
    }

    public static Request interceptRequest(Request request, String parameter)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        Sink sink = Okio.sink(baos);
        BufferedSink bufferedSink = Okio.buffer(sink);

        /**
         * Write old params
         * */
        request.body().writeTo(bufferedSink);

        /**
         * write to buffer additional params
         * */
        bufferedSink.writeString(parameter, Charset.defaultCharset());

        RequestBody newRequestBody = RequestBody.create(
                request.body().contentType(),
                bufferedSink.buffer().readUtf8()
        );

        return request.newBuilder().post(newRequestBody).build();
    }


    private static void initClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);//设置log
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        String parameter = "&" + "key" + "=" + "value";
                        Request newRequest = interceptRequest(request, parameter);
                        Log.e("okhttp", newRequest.body().toString());
                        return chain.proceed(request);

                    }
                }).addInterceptor(interceptor).retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS).build();
        mRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(
                        GsonConverterFactory.create())
                .baseUrl("http://cube-server.liaohuqiu.net/").build();
    }

}
