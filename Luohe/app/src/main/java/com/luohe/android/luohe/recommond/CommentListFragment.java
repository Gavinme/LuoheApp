package com.luohe.android.luohe.recommond;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.BaseRecyclerViewAdapter;
import com.luohe.android.luohe.base.BaseRefreshListFragment;
import com.luohe.android.luohe.base.BindLayout;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.net.http.service.CommentBean;
import com.luohe.android.luohe.utils.TimeUtils;
import com.luohe.android.luohe.utils.ToastUtil;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/4/28.
 */
public class CommentListFragment extends BaseRefreshListFragment<CommentBean> {
	EditText inputEdit;
	TextView tv_send;
	private int mReplyUserId = 0;
	private int mReplyId = 0;

	@Override
	protected void onInit(View view, RecyclerView recyclerView) {
		super.onInit(view, recyclerView);
		getTitleBar().setDefauleBackBtn();
		getTitleBar().setTitle("评论");
		initRecycleView(recyclerView);
		refreshData();
		setListMode(MODE_DISABLE);
	}

	private void initRecycleView(RecyclerView recyclerView) {
		recyclerView.setPadding(0, 0, 0, 0);
		addCommentLayout();
	}

	private void addCommentLayout() {
		View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_comment_view, null);
		getFootView().addView(view,
				new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
		inputEdit = (EditText) view.findViewById(R.id.tv_input);
		tv_send = (TextView) view.findViewById(R.id.tv_send);
		initActions();
	}

	private void initActions() {
		tv_send.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mReplyUserId == 0) {
					publishComment();
				} else {
					replayOthers();
				}
			}
		});
	}

	@Override
	protected void onListItemClick(CommentBean bean, View view, int adapterPosition) {
		super.onListItemClick(bean, view, adapterPosition);
		mReplyId = bean.commentId;
		inputEdit.setHint("回复~" + bean.userName);

	}

	private void replayOthers() {
		ApiLoader.getApiService().comReply(mReplyId, mReplyUserId, inputEdit.getText().toString())
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result>(getActivity()) {
					@Override
					public void onSuccess(Result result) {
						if (result.isHasReturnValidCode() && result.getResult() != null) {
							ToastUtil.showToast(getActivity().getString(R.string.comment_send_suc));
							refreshData();
							hideInput();
							inputEdit.setText("");
						} else {
							ToastUtil.showToast(getActivity().getString(R.string.comment_send_fail));
							hideInput();

						}

					}
				}.onError(new CommonSubscriber.ErrorHandler() {
					@Override
					public void onError(Throwable e) {
						ToastUtil.showToast(getActivity().getString(R.string.comment_send_fail));
						hideInput();
					}
				}));
	}

	public void publishComment() {
		ApiLoader.getApiService()
				.publishCom(getArguments().getInt(ConstantsUtil.id), inputEdit.getText().toString(), 1)
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result>(getActivity()) {
					@Override
					public void onSuccess(Result result) {
						if (result.isHasReturnValidCode() && result.getResult() != null) {
							ToastUtil.showToast(getActivity().getString(R.string.comment_send_suc));
							refreshData();
							hideInput();
							inputEdit.setText("");
						} else {
							ToastUtil.showToast(getActivity().getString(R.string.comment_send_fail));
							hideInput();

						}

					}
				}.onError(new CommonSubscriber.ErrorHandler() {
					@Override
					public void onError(Throwable e) {
						ToastUtil.showToast(getActivity().getString(R.string.comment_send_fail));
						hideInput();
					}
				}));
	}

	public void hideInput() {
		InputMethodManager im = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
		im.hideSoftInputFromWindow(getActivity().getCurrentFocus().getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);

	}

	@Override
	protected void onPullDown(final AdapterManger<CommentBean> mAdapterManger) {
		ApiLoader.getApiService().articleCommentList(getArguments().getInt(ConstantsUtil.id))
				.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result<List<CommentBean>>>(getActivity()) {
					@Override
					public void onSuccess(Result<List<CommentBean>> result) {
						if (result.getResult() != null) {
							mAdapterManger.initListToAdapter(result.getResult());
						}
					}
				}.onError(new CommonSubscriber.ErrorHandler() {
					@Override
					public void onError(Throwable e) {

					}
				}));

	}

	@Override
	protected void onLoadMore(AdapterManger<CommentBean> adapter) {

	}

	@Override
	protected Class<? extends BaseRecyclerViewAdapter<CommentBean>> onGetAdapterType() {
		return ListAdapter.class;
	}

	static class ListAdapter extends BaseRecyclerViewAdapter<CommentBean> {

		public ListAdapter(Context context) {
			super(context);
		}

		@Override
		protected void onBindVHLayoutId(List<Class<?>> VhClazzList) {
			VhClazzList.add(ViewHolder.class);
		}
	}

	@BindLayout(id = R.layout.layout_comment_item)
	static class ViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<CommentBean> {

		@Bind(R.id.avatar)
		ImageView avatar;
		@Bind(R.id.state)
		ImageView state;
		@Bind(R.id.count)
		TextView count;
		@Bind(R.id.name)
		TextView name;
		@Bind(R.id.time)
		TextView time;
		@Bind(R.id.title)
		TextView title;
		@Bind(R.id.layout)
		LinearLayout layout;

		public ViewHolder(View itemView) {
			super(itemView);
		}

		@Override
		protected void bindView(final CommentBean bean, int position, final Context context) {
			loadRoundImagUrl(avatar, bean.headUrl);
			count.setText(String.format("%d", bean.praseNum));
			if (TextUtils.isEmpty(bean.replyUserName)) {
				name.setText(bean.userName);
			} else {
				name.setText(bean.userName + " 回复 " + bean.replyUserName);
			}

			title.setText(bean.commentText);
			time.setText(TimeUtils.getFormatTime(bean.commentTime));
			layout.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					ApiLoader.getApiService().praiseSub(1, bean.titleId).subscribeOn(Schedulers.io())
							.observeOn(AndroidSchedulers.mainThread()).subscribe(new CommonSubscriber<Result>(context) {
								@Override
								public void onSuccess(Result result) {
									if (result.isHasReturnValidCode()) {
										state.setImageResource(R.drawable.like_icon_press);
										ToastUtil.showToast(context.getResources().getString(R.string.like_suc));
									} else {
										ToastUtil.showToast(result.getMsg());
									}
								}
							}.onError(new CommonSubscriber.ErrorHandler() {
								@Override
								public void onError(Throwable e) {
									ToastUtil.showToast(R.string.net_error);
								}
							}));
				}
			});

		}
	}
}
