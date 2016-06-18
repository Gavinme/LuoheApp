package com.luohe.android.luohe.net.http.service;

import java.io.Serializable;

/**
 * Created by GanQuan on 16/4/16.推荐、文风
 */
public class RecommendListItem implements Serializable {
	public int id;
	public String titleName;
	public String nickName;
	public String userId;
	public String publishTime;
	public String titleSubCon;// des
	public int source;//文章来源 0：落和令文章 1：文风

}
