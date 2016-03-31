package com.luohe.android.luohe.utils.share;

import java.io.Serializable;

/**
 * Desc   : 分享的bean
 */
public class ShareItem implements Serializable {
    private int icon;
    private String name;

    public ShareItem(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
