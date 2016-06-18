package com.luohe.android.luohe.user;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by GanQuan on 16/4/25.
 */
public class UserLoginInfo implements Serializable {
	@SerializedName("token")
	public String token;// user login token
	@SerializedName("id")
	public int id;// userid
	@SerializedName("account")
	public String account;// username
	@SerializedName("trueName")
	public String trueName;

	@SerializedName("nickName")
	public String nickName;
	@SerializedName("headUrl")
	public String headUrl;
	@SerializedName("rank")
	public String rank;
	@SerializedName("sex")
	public String sex;
	@SerializedName("brithday")
	public String brithday;
	@SerializedName("city")
	public String city;
	@SerializedName("password")
	public String password;//im login pw
	@SerializedName("accountDesc")
	public String accountDesc;
}
