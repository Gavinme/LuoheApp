package com.luohe.android.luohe.base;

import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.reflect.TypeToken;
import com.luohe.android.luohe.net.data.GsonRequest;
import com.luohe.android.luohe.net.data.RequestManager;

import java.util.Map;

/**
 * Created by GanQuan on 16/2/28.
 */
public class VolleyBaseFragment extends BaseFragment {
    protected Response.ErrorListener VOLLEY_ERROR_LISTENER = errorListener();

    @Override
    public void onDestroy() {
        super.onDestroy();
        RequestManager.cancelAll(this);
        VOLLEY_ERROR_LISTENER = null;
    }


    protected Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        };
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
    protected void init(View view) {

    }

    @Override
    protected int onBindLayoutId() {
        return 0;
    }
}
