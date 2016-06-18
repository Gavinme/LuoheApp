package com.luohe.android.luohe.mine;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Author: GanQuan Date: 16/6/12 Email:ganquan3640@gmail.com
 */

public class WenFengDetailBean implements Serializable {
	@SerializedName("styleId")
	public int styleId;
	@SerializedName("userId")
	public int userId;
	@SerializedName("styleContent")
	public String styleContent;
	@SerializedName("createTime")
	public long createTime;
	@SerializedName("memberRank")
	public String memberRank;
	@SerializedName("nickName")
	public String nickName;
	@SerializedName("headUrl")
	public String headUrl;
	@SerializedName("urlImg")
	public String urlImg;
	@SerializedName("file")
	public String file;
	@SerializedName("supportTag")
	public int supportTag;
	@SerializedName("collectionTag")
	public int collectionTag;
	@SerializedName("supportNum")
	public int supportNum;
	@SerializedName("styleName")
	public String titleName;
	@SerializedName("readNum")
	public String readNum;
}
