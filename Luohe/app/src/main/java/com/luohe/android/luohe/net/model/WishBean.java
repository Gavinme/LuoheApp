package com.luohe.android.luohe.net.model;

import java.io.Serializable;

/**
 * Created by cegrano on 16/5/7.
 */
public class WishBean implements Serializable {
    public int id;
    public int userId;
    public int wishUserId;
    public int wishValue;
    public String wishContent;
    public String wishTime;
    public String wishIsIgnore;
    public String userName;
    public int isAnonymous;
    public int wishIsAccept;
}
