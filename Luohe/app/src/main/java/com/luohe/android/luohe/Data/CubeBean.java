package com.luohe.android.luohe.Data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GanQuan on 16/3/5.
 */
public class CubeBean implements Serializable {
    @Expose
    public String ok;
    @Expose
    public String str;
    @Expose
    public String result;
    @Expose
    public String server_time;

    @Override
    public String toString() {
        return "CubeBean{" +
                "ok='" + ok + '\'' +
                ", str='" + str + '\'' +
                ", result='" + result + '\'' +
                ", server_time='" + server_time + '\'' +
                '}';
    }
}
