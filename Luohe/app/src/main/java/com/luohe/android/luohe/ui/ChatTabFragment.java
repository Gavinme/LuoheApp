package com.luohe.android.luohe.ui;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.alibaba.mobileim.IYWLoginService;
import com.alibaba.mobileim.YWAPI;
import com.alibaba.mobileim.YWIMKit;
import com.alibaba.mobileim.YWLoginParam;
import com.alibaba.mobileim.aop.AdviceBinder;
import com.alibaba.mobileim.aop.PointCutEnum;
import com.alibaba.mobileim.channel.event.IWxCallback;
import com.alibaba.mobileim.channel.util.YWLog;
import com.alibaba.mobileim.conversation.IYWConversationService;
import com.alibaba.mobileim.conversation.YWConversation;
import com.alibaba.mobileim.conversation.YWConversationCreater;
import com.alibaba.mobileim.conversation.YWMessage;
import com.alibaba.mobileim.conversation.YWMessageChannel;
import com.androidplus.util.LayoutUtil;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.message.FriendAllListFragment;
import com.luohe.android.luohe.message.IMContactSample;
import com.luohe.android.luohe.message.IMConversationListSample;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by GanQuan on 16/2/20.
 */
public class ChatTabFragment extends BaseFragment {
	private static final String APP_KEY = "23325655";
	final String TAG = ChatTabFragment.class.getSimpleName();
	@Bind(R.id.search)
	SearchView search;
	// @Bind(R.id.btn_message)
	// Button btnMessage;
	// @Bind(R.id.btn_contact)
	// Button btnContact;
	@Bind(R.id.view_pager)
	ViewPager viewPager;
	@Bind(R.id.tab_layout)
	TabLayout mTabLayout;

	PagerAdapter mAdapter;
	private YWIMKit mIMKit;
	private IYWConversationService mConversationService;

	@Override
	protected void init(View view) {
		// 此实现不一定要放在Application onCreate中
		final String userid = "test1";
		// 此对象获取到后，保存为全局对象，供APP使用
		// 此对象跟用户相关，如果切换了用户，需要重新获取
		mIMKit = YWAPI.getIMKitInstance(userid, APP_KEY);

		AdviceBinder.bindAdvice(PointCutEnum.CONVERSATION_FRAGMENT_UI_POINTCUT, IMConversationListSample.class);
		AdviceBinder.bindAdvice(PointCutEnum.CONTACTS_UI_POINTCUT, IMContactSample.class);

		// 开始登录
		String password = "test";
		IYWLoginService loginService = mIMKit.getLoginService();

		YWLoginParam loginParam = YWLoginParam.createLoginParam(userid, password);
		loginService.login(loginParam, new IWxCallback() {

			@Override
			public void onSuccess(Object... arg0) {
				YWLog.i(TAG, "login success!");
				mConversationService = mIMKit.getConversationService();
				int px = LayoutUtil.GetPixelByDIP(getActivity(), 3);
				mTabLayout.setPadding(px, px, px, px);

				mTabLayout.setTabMode(TabLayout.MODE_FIXED);

				mAdapter = new ChatTabPagerAdapter(getChildFragmentManager());
				viewPager.setAdapter(mAdapter);
				mTabLayout.setupWithViewPager(viewPager);
				mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
				mTabLayout.setTabsFromPagerAdapter(mAdapter);

				// 创建一条文本或者表情消息
				YWMessage message = YWMessageChannel.createTextMessage("/:081");
				// 创建一个与消息接收者的聊天会话，userId：表示聊天对象id
				final YWConversationCreater conversationCreater = mConversationService.getConversationCreater();
				YWConversation conversation = conversationCreater.createConversationIfNotExist("test2");
				// 将消息发送给对方
				conversation.getMessageSender().sendMessage(message, 5000, new IWxCallback() {

					@Override
					public void onSuccess(Object... arg0) {
						// 发送成功

					}

					@Override
					public void onProgress(int arg0) {

					}

					@Override
					public void onError(int arg0, String arg1) {
						// 发送失败

					}
				});
			}

			@Override
			public void onProgress(int arg0) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onError(int errCode, String description) {
				// 如果登录失败，errCode为错误码,description是错误的具体描述信息
				YWLog.i(TAG, "login error!" + description);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		Log.e(TAG, "onHiddenChanged:" + hidden);
	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.fragment_chat_tab;
	}

	class ChatTabPagerAdapter extends FragmentStatePagerAdapter {

		public ChatTabPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			if (position == 0)
				return mIMKit.getConversationFragment();
			else
				return Fragment.instantiate(getActivity(), FriendAllListFragment.class.getName());
		}

		@Override
		public int getCount() {
			return 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			if (position == 0) {
				return "消息";
			} else {
				return "我的好友";
			}
		}
	}
}
