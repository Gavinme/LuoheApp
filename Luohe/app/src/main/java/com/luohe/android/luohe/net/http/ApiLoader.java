package com.luohe.android.luohe.net.http;

import android.content.Context;

import com.luohe.android.luohe.common.RetrofitImp;
import com.luohe.android.luohe.net.data.ApiParams;
import com.luohe.android.luohe.net.http.service.ApiServiceBus;

/**
 * Created by GanQuan on 16/4/2.
 */
public class ApiLoader {

    private static ApiServiceBus mService;


    public static ApiServiceBus getApiService() {
        if (mService == null) {
            mService = RetrofitImp.getInstance().create(ApiServiceBus.class);
        }
        return mService;
    }

}
