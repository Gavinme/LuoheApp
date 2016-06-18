package com.luohe.android.luohe.net.model;

import com.luohe.android.luohe.utils.TimeUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by GanQuan on 16/4/4.
 */
public class LuoheWrapBean implements Serializable {
	public int id;
	public String fallOrderRand;
	public int fallOrderState;
	public String fallOrderName;
	public String fallOrderDes;
	public String fallOrderPublish;
	public String userName;
	public int userId;
	public String headUrl;
	public int type = 0;

	public List<SubjectBean> title;
	public SubjectBean subjectBean;// 用来转换主题

	// 0：悬赏中
	// 1：创作中
	// 2：（结束）无悬赏
	// 3：（结束）未创作爽约
	// 4：（结束）完成创作

	public String getAwardTitle() {
		String title = "";
		switch (fallOrderState) {
		case 0:
			title = "悬赏中";
			break;
		case 1:
			title = "创作中";
			break;
		case 2:
			title = "（结束）无悬赏";
			break;
		case 3:
			title = "（结束）未创作爽约";
			break;
		case 4:
			title = "（结束）完成创作";
			break;
		}
		return title;

	}

	public String getTime() {
		return TimeUtils.getFormatTime(fallOrderPublish);
	}

	/**
	 * 主题
	 * 
	 * @return
	 * @param userId
	 */
	public List<LuoheWrapBean> getTitles(int userId) {
		if (title != null && title.size() > 0) {
			return createSubjects(title, userId);
		}
		return null;
	}

	public static class SubjectBean implements Serializable {
		public int type = 1;
		public int artOrder;
		public String titleDes;
		public String publishTime;
		public String titleName;
		public int userId;
		public int titleId;
		public String userName;
		public String headUrl;
		public String artDes;
		public int artValue;
		public int titleState;
		public int pos;
		public int atLuoheUid;
		/**
		 * artId : 288 artTitle : 123 artUserId : 55 fallOrderName : 测试落和令
		 * fallOrderUserName : 13699263989 fallOrderState : 1
		 */

		public int artId;
		public String artTitle;
		public String artUserId;
		public String fallOrderName;
		public String fallOrderUserName;
		public String fallOrderState;

		public String getTime() {
			return TimeUtils.getFormatTime(publishTime);
		}

		public int getArtId() {
			return artId;
		}

		public void setArtId(int artId) {
			this.artId = artId;
		}

		public String getArtTitle() {
			return artTitle;
		}

		public void setArtTitle(String artTitle) {
			this.artTitle = artTitle;
		}

		public String getArtUserId() {
			return artUserId;
		}

		public void setArtUserId(String artUserId) {
			this.artUserId = artUserId;
		}

		public String getFallOrderName() {
			return fallOrderName;
		}

		public void setFallOrderName(String fallOrderName) {
			this.fallOrderName = fallOrderName;
		}

		public String getFallOrderUserName() {
			return fallOrderUserName;
		}

		public void setFallOrderUserName(String fallOrderUserName) {
			this.fallOrderUserName = fallOrderUserName;
		}

		public String getFallOrderState() {
			return fallOrderState;
		}

		public void setFallOrderState(String fallOrderState) {
			this.fallOrderState = fallOrderState;
		}
	}

	public static List<LuoheWrapBean> createSubjects(List<SubjectBean> subjectBeanList, int userId) {
		List<LuoheWrapBean> list = new ArrayList<>();
		LuoheWrapBean luoheWrapBean = null;
		int i = 0;
		for (SubjectBean bean : subjectBeanList) {
			luoheWrapBean = new LuoheWrapBean();
			luoheWrapBean.subjectBean = bean;
			luoheWrapBean.subjectBean.pos = i;
			i++;
			luoheWrapBean.type = 1;
			luoheWrapBean.subjectBean.atLuoheUid = userId;
			list.add(luoheWrapBean);
		}
		return list;
	}
}
