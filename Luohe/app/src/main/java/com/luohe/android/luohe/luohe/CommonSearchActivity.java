package com.luohe.android.luohe.luohe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.PathInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.base.BaseFragment;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.ui.MyWenFengListActivity;
import com.luohe.android.luohe.ui.RecommendListActivity;

import butterknife.Bind;

/**
 * Created by GanQuan on 16/3/9.
 */
public class CommonSearchActivity extends AppCompatActivity implements View.OnClickListener {
	LinearLayout btnLuohe;
	LinearLayout btnUser;
	LinearLayout btnArticle;

	@Bind(R.id.activity_content)
	FrameLayout activity_content;

	EditText mSearchEdit;
	final int LUO_HE = 0;
	final int USER = 1;
	final int ART = 2;
	int mSearchtype = 0;

	@Override
	protected void init(Bundle savedInstanceState) {
		View searchItemsView = getLayoutInflater().inflate(R.layout.search_init_view, null);
		activity_content.addView(searchItemsView);
		btnLuohe = (LinearLayout) searchItemsView.findViewById(R.id.luohe);
		btnUser = (LinearLayout) searchItemsView.findViewById(R.id.user);
		btnArticle = (LinearLayout) searchItemsView.findViewById(R.id.article);
		btnArticle.setOnClickListener(this);
		btnLuohe.setOnClickListener(this);
		btnUser.setOnClickListener(this);
		initBtns();
		initTitleBar();

	}

	private void initBtns() {
		initBtn(btnLuohe, R.drawable.search_icon_luohe, "落和");
		initBtn(btnArticle, R.drawable.search_icon_article, "文章");
		initBtn(btnUser, R.drawable.search_icon_user, "用户");
	}

	private void initBtn(View btn, int resId, String text) {
		ImageView imageView = (ImageView) btn.findViewById(R.id.image);
		imageView.setImageResource(resId);
		TextView textView = (TextView) btn.findViewById(R.id.text);
		textView.setText(text);
	}

	private void initTitleBar() {
		TitleBar titleBar = getTitlebar();
		View customView = getLayoutInflater().inflate(R.layout.search_view, null);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT);
		titleBar.setCustomTitle(customView, lp);
		mSearchEdit = (EditText) customView.findViewById(R.id.edit_search);
		mSearchEdit.setHint("搜索：落和");
		mSearchEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					performSearch();
					return true;
				}
				return false;
			}
		});
	}

	private void performSearch() {
		switch (mSearchtype) {
		case LUO_HE:
			searchLuohe();
			break;
		case USER:
			searchUser();

			break;
		case ART:
			searchArt();
			break;
		}

	}

	private void searchArt() {
		startActivity(RecommendListActivity.getStartIntent(this, mSearchEdit.getText().toString()));
	}

	private void searchUser() {
		startActivity(UserListActivity.getStartIntent(this, mSearchEdit.getText().toString()));
	}

	private void searchLuohe() {
		startActivity(LuoheListActivity.getStartIntent(this, mSearchEdit.getText().toString()));

	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.container_activity;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.luohe:
			mSearchEdit.setHint("搜索：落和");
			mSearchtype = LUO_HE;
			break;
		case R.id.user:
			mSearchEdit.setHint("搜索：用户");
			mSearchtype = USER;
			break;
		case R.id.article:
			mSearchEdit.setHint("搜索：文章");
			mSearchtype = ART;
			break;

		}

	}

	public interface ISearchFragment {
		Bundle getKeyBundle(String key);

		String getSearchKey(Fragment fragment);

		boolean isSearch(Fragment fragment);

	}

	public static class SearchFragmentImp implements ISearchFragment {

		@Override
		public Bundle getKeyBundle(String key) {
			Bundle bundle = new Bundle();
			bundle.putString(ConstantsUtil.key, key);
			bundle.putBoolean(ConstantsUtil.serach, true);
			return bundle;

		}

		@Override
		public String getSearchKey(Fragment fragment) {

			return fragment.getArguments().getString(ConstantsUtil.key);
		}

		public boolean isSearch(Fragment fragment) {

			return fragment.getArguments().getBoolean(ConstantsUtil.serach);
		}
	}
}
