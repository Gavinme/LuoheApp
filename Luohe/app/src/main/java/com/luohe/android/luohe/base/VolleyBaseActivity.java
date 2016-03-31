package com.luohe.android.luohe.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.luohe.android.luohe.net.data.GsonRequest;
import com.luohe.android.luohe.net.data.RequestManager;
import com.luohe.android.luohe.view.swipeback.SwipeBackActivity;

import java.util.Map;

/**
 * Created by GanQuan on 16/2/28.
 * 带有网络请求的activity
 */
public class VolleyBaseActivity extends SwipeBackActivity {
    protected Activity mActivity;
    protected Response.ErrorListener VOLLEY_ERROR_LISTENER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
        VOLLEY_ERROR_LISTENER = errorListener();
    }

    protected <T> GsonRequest createGsonRequest(String url, TypeToken<T> typeToken, Map<String, String> params, Response.Listener<T> listener) {
        return new GsonRequest<T>(url, typeToken, params, listener, VOLLEY_ERROR_LISTENER);
    }

    protected void executeRequest(Request<?> request) {
        RequestManager.addRequest(request, this);
    }

    /**
     * quick send a post request
     *
     * @param url
     * @param typeToken
     * @param params
     * @param listener
     * @param <T>
     */
    protected <T> void sendPostRequest(String url, TypeToken<T> typeToken, Map<String, String> params, Response.Listener<T> listener) {
        executeRequest(createGsonRequest(url, typeToken, params, listener));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //do net error
//                Toast.makeText(mActivity, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        };
    }
}
