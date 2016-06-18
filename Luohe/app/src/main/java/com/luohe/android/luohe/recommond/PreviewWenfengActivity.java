package com.luohe.android.luohe.recommond;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.find.SendWishActivity;
import com.luohe.android.luohe.mine.WenFengDetailBean;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.model.ArticleDetailBean;
import com.luohe.android.luohe.richtext.ImageFixCallback;
import com.luohe.android.luohe.richtext.ImageHolder;
import com.luohe.android.luohe.richtext.RichText;
import com.luohe.android.luohe.user.LoginHelper;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.ImageUtils;
import com.luohe.android.luohe.utils.TimeUtils;
import com.luohe.android.luohe.utils.ToastUtil;
import com.luohe.android.luohe.utils.share.ShareHelper;
import com.luohe.android.luohe.utils.share.ShareItem;
import com.luohe.android.luohe.utils.share.ShareUtils;
import com.luohe.android.luohe.view.AbsPopWindow;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Author: GanQuan Date: 16/6/12 Email:ganquan3640@gmail.com
 */

public class PreviewWenfengActivity extends AppCompatActivity {
	@Bind(R.id.share_btn)
	LinearLayout share_btn;
	@Bind(R.id.comment_btn)
	LinearLayout comment_btn;
	@Bind(R.id.more_btn)
	LinearLayout more_btn;

	@Bind(R.id.img_avatar)
	ImageView img_avatar;
	@Bind(R.id.tv_name)
	TextView tv_name;
	@Bind(R.id.tv_time)
	TextView tv_time;
	@Bind(R.id.title)
	TextView title;
	@Bind(R.id.content)
	TextView content;
	@Bind(R.id.tv_like_count)
	TextView tv_like_count;
	@Bind(R.id.tv_read_count)
	TextView tv_read_count;

	MorePop mMorePop;
	private int mTitleId;

	private WenFengDetailBean mArticleBean;

	@Override
	protected void init(Bundle savedInstanceState) {
		getTitlebar().setTitle("查看文章");
		getTitlebar().setLeftImageResource(R.drawable.back_btn);
		initBottomViews();
		loadData();
	}

	private void loadData() {
		getLoadingHelper().showLoadingView();
		mTitleId = getIntent().getIntExtra(ConstantsUtil.id, 0);
		if (mTitleId == 0) {
			getLoadingHelper().showNetworkError();
			return;
		}
		ApiLoader.getApiService().styleDetail(mTitleId).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result<WenFengDetailBean>>(this) {
					@Override
					public void onSuccess(Result<WenFengDetailBean> result) {
						getLoadingHelper().showContentView();
						if (result.isHasReturnValidCode() && result.getResult() != null) {
							mArticleBean = result.getResult();

							ImageUtils.displayImage(result.getResult().headUrl, img_avatar);
							tv_time.setText(TimeUtils.getFormatTime(result.getResult().createTime + ""));
							tv_name.setText(result.getResult().nickName);
							title.setText(result.getResult().titleName);
							String text = doContent(result.getResult());
							final String IMAGE = "<p>大家壕，周二了，你们的周一综合症好了吧，没借口偷懒了吧，赶紧上班去吧</p>"
									+ "<img  src=\"http://g.hiphotos.baidu.com/image/pic/item/241f95cad1c8a7866f726fe06309c93d71cf5087.jpg\">大家壕，周二了，你们的周一综合症好了吧，没借口偷懒了吧，赶紧上班去吧"
									+ "<img src=\"http://img.ugirls.com/uploads/cooperate/baidu/20160519menghuli.jpg\" />";

							RichText.from(text).autoFix(false).fix(new ImageFixCallback() {
								@Override
								public void onFix(ImageHolder holder, boolean imageReady) {
									if (holder.getWidth() > 200 && holder.getHeight() > 200) {
										holder.setAutoFix(true);
									}
								}
							}).into(content);
							// todo
							// content.setText(result.getResult().titleContent);
							tv_like_count.setText(result.getResult().supportNum + "");
							tv_read_count.setText("阅读 " + result.getResult().readNum);
						} else {
							getLoadingHelper().showNetworkError();
						}
					}
				}.onError(new CommonSubscriber.ErrorHandler() {
					@Override
					public void onError(Throwable e) {
						getLoadingHelper().showNetworkError();
					}
				}));
	}

	private String doContent(WenFengDetailBean result) {
		String content = result.styleContent;
		String[] fileImgs = null, imgUrls = null;
		if (!TextUtils.isEmpty(result.file)) {
			fileImgs = result.file.split(";");
		}
		if (!TextUtils.isEmpty(result.urlImg)) {
			imgUrls = result.urlImg.split(";");

		}
		String imgReat = "<img src=\"%s\"/>";
		if (fileImgs == null || fileImgs.length == 0)
			return content;
		for (int i = 0; i < fileImgs.length; i++) {
			content=content.replaceAll(fileImgs[i], String.format(imgReat, imgUrls[i]));
		}
		Log.e("gq", "content: " + content);

		return content;
	}

	private void initBottomViews() {

		setBottomBtn(R.drawable.btn_share, "分享", share_btn);
		setBottomBtn(R.drawable.btn_comment, "评论", comment_btn);
		setBottomBtn(R.drawable.btn_more, "更多", more_btn);

	}

	public void setBottomBtn(int resId, String name, View view) {
		ImageView imageView = (ImageView) view.findViewById(R.id.icon);

		TextView textView = (TextView) view.findViewById(R.id.name);
		imageView.setImageResource(resId);
		textView.setText(name);

	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.priview_article_activity;
	}

	@OnClick({ R.id.share_btn, R.id.comment_btn, R.id.more_btn })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.share_btn:
			List<ShareItem> shares = new ArrayList<>();
			ShareHelper.preShareWindow(this, shares, new ShareHelper.ShareClickListener() {
				@Override
				public void onItemClick(int position, Object object) {
					ShareUtils.showShare(position + 2, mArticleBean.styleContent, null, null, mArticleBean.titleName,
							null);
				}
			});
			ShareHelper.showPopWindow(this, v);
			break;
		case R.id.comment_btn:
			startActivity(CommentListActivity.getStartIntent(this, mTitleId));

			break;
		case R.id.more_btn:
			new LoginHelper(this).doLogin(new LoginHelper.ILoginListener() {
				@Override
				public void onLogin() {
					if (mMorePop == null) {
						mMorePop = new MorePop(getContext(), mArticleBean);
					}
					mMorePop.showUp(more_btn);

				}
			});
			break;

		}
	}

	public static class MorePop extends AbsPopWindow implements View.OnClickListener {

		@Bind(R.id.collect)
		TextView collect;
		@Bind(R.id.send_wish)
		TextView sendWish;
		@Bind(R.id.tv_report)
		TextView tvReport;
		@Bind(R.id.tv_edit)
		TextView tvEdit;
		@Bind(R.id.tv_delete)
		TextView tvDelete;
		WenFengDetailBean mArticleBean;

		public MorePop(Context context, WenFengDetailBean mArticleBean) {
			super(context);
			this.mArticleBean = mArticleBean;
			initActionts();
		}

		private void initActionts() {
			collect.setOnClickListener(this);
			sendWish.setOnClickListener(this);
			tvReport.setOnClickListener(this);
			tvEdit.setOnClickListener(this);
			tvDelete.setOnClickListener(this);
		}

		@Override
		protected int getLayoutId() {
			return R.layout.pop_pre_more;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.collect:
				ApiLoader.getApiService().collArti(mArticleBean.styleId, 1, 3).subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new CommonSubscriber<Result>(getContext()) {
							@Override
							public void onSuccess(Result result) {
								if (result.isHasReturnValidCode()) {
									ToastUtil.showToast(getContext().getString(R.string.collect_send_suc));
								} else {
									ToastUtil.showToast(getContext().getString(R.string.collect_send_fail));
								}
							}
						});
				break;
			case R.id.send_wish:

				getContext().startActivity(
						SendWishActivity.getStartIntent(getContext(), mArticleBean.userId, mArticleBean.nickName));
				break;
			case R.id.tv_report:
				getContext().startActivity(new Intent(getContext(), ReportActivity.class));
				break;
			case R.id.tv_edit:
				break;
			case R.id.tv_delete:
				break;
			}
			this.dismiss();

		}

		@Override
		public void showUp(View view) {
			checkUserId();
			super.showUp(view);

		}

		private void checkUserId() {
			if (mArticleBean == null)
				return;
			if (UserInfoUtil.getInstance().checkUid(mArticleBean.userId)) {
				tvEdit.setVisibility(View.VISIBLE);
				tvDelete.setVisibility(View.VISIBLE);
			} else {
				tvEdit.setVisibility(View.GONE);
				tvDelete.setVisibility(View.GONE);
			}
		}

	}

	public static Intent getStartIntent(Context context, int titleId) {
		Intent it = new Intent(context, PreviewWenfengActivity.class);
		it.putExtra(ConstantsUtil.id, titleId);
		return it;
	}
}
