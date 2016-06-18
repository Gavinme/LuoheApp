package com.luohe.android.luohe.net.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GanQuan on 16/4/3.
 */
public class LuoheTagBean implements Serializable {
    public int baseId;
    public String baseName;

    public int getBaseId() {
        return baseId;
    }

    public void setBaseId(int baseId) {
        this.baseId = baseId;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    @Override
    public String toString() {
        return "LuoheTagBean{" +
                "baseId='" + baseId + '\'' +
                ", baseName='" + baseName + '\'' +
                '}';
    }
}
