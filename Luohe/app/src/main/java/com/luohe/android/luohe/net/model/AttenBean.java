package com.luohe.android.luohe.net.model;

import java.io.Serializable;

/**
 * Created by cegrano on 16/5/28.
 * 我的好友/粉丝
 */
public class AttenBean implements Serializable {
    //类型及范围 返回值 说明
    public int userId; //用户ID
    public String account; //用户账户
    public String nickName; //用户昵称
    public String headUrl; //头像地址
    public String password; //友盟发消息的密码
}
