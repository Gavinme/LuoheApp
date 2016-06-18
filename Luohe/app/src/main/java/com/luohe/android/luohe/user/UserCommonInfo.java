package com.luohe.android.luohe.user;

import java.util.List;

/**
 * Created by GanQuan on 16/4/25.
 */
public class UserCommonInfo {
    public String nickName;

    public String province;   //地点

    public String birthday;

    public String sex;

    public String headUrl;

    public String accountDesc;     //个人介绍

    public String memberRank;  //会员等级

    public int attentionCount;  //关注数

    public int fansCount;

    public int allValue;

    public int nowValue;

    public int allRank;

    public int isFocus;

    public List<UserStyle> userStyle;

	public static class UserStyle {
		public int id;

		public String baseName;

		public int isMainStyle;

	}
}
