package com.luohe.android.luohe.mine;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author: GanQuan Date: 16/5/28 Email:ganquan3640@gmail.com
 */
public class UserRequest implements Serializable {
	@SerializedName("e.nickname") //用户昵称
	public String nickname;
	@SerializedName("e.trueName")	//用户真实姓名
	public String trueName;
	@SerializedName("e.accountDesc")	//用户个人介绍
	public String accountDesc;
	@SerializedName("e.birthday")	//用户生日
	public String birthday;
	@SerializedName("e.province")  //用户归属省份
	public String province;
	@SerializedName("e.city")  //用户归属城市
	public String city;
	@SerializedName("e.sex")	//用户性别
	public String sex;
	@SerializedName("isOnlyFriend") 	//消息是否只是朋友的
	public String isOnlyFriend;

}
