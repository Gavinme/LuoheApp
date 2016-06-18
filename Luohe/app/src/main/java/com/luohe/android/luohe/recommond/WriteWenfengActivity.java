package com.luohe.android.luohe.recommond;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.mine.Base64Util;
import com.luohe.android.luohe.net.Downloader;
import com.luohe.android.luohe.net.data.ApiParams;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.richeditor.RichTextEditor;
import com.luohe.android.luohe.user.LoginHelper;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.JsonHelper;
import com.luohe.android.luohe.utils.ToastUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by GanQuan on 16/3/30.写文章 增加图文混排功能
 */
public class WriteWenfengActivity extends AppCompatActivity implements View.OnClickListener {
	private static final int REQUEST_CODE_PICK_IMAGE = 1023;
	private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1022;
	@Bind(R.id.edit_title)
	EditText editTitle;
	@Bind(R.id.edit_des)
	RichTextEditor editor;
	LoginHelper mLoginHelper = new LoginHelper(this);
	@Bind(R.id.button1)
	View btn1;
	@Bind(R.id.button2)
	View btn2;
	@Bind(R.id.button3)
	View btn3;
	@Bind(R.id.rd_public)
	RadioButton rdPublic;
	@Bind(R.id.rd_friend_see)
	RadioButton rdFriendSee;
	@Bind(R.id.rd_own_see)
	RadioButton rdOwnSee;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		mSetTint = false;
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void init(Bundle savedInstanceState) {
		getTitlebar().setImmersive(false);
		getTitlebar().setTitle("文章");
		getTitlebar().setDefauleBackBtn();
		getTitlebar().addAction(new TitleBar.TextAction("存草稿") {
			@Override
			public void performAction(View view) {
				sendMsgByDraft();
			}
		});
		getTitlebar().addAction(new TitleBar.TextAction("发布") {
			@Override
			public void performAction(View view) {
				sendMsg();
			}

		});
		initViewActions();
	}

	private void sendMsgByDraft() {

	}

	private void initViewActions() {
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
		btn3.setOnClickListener(this);
	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.activity_write_wenfeng;
	}

	public static Intent getStartIntent(Context context, int fallOrderId) {
		Intent intent = new Intent(context, WriteWenfengActivity.class);
		intent.putExtra(ConstantsUtil.id, fallOrderId);
		return intent;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button1:
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");// 相片类型
			startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);

			break;
		case R.id.button2:
			openCamera();
			break;
		case R.id.button3:
			break;
		}
	}

	private int getCheckRadio() {
		if (rdPublic.isChecked()) {
			return 0;
		} else if (rdFriendSee.isChecked()) {
			return 1;

		} else if (rdOwnSee.isChecked()) {
			return 2;
		}
		return 0;

	}

	public void sendMsg() {
		final ProgressDialog progressDialog = ProgressDialog.show(this, "发布中...", "");
		progressDialog.show();
		final List<RichTextEditor.EditData> editList = editor.buildEditData();
		// 下面的代码可以上传、或者保存，请自行实现

		Observable
				.just(editList)
				.flatMap(new Func1<List<RichTextEditor.EditData>, Observable<String>>() {
					@Override
					public Observable<String> call(List<RichTextEditor.EditData> editDatas) {
						ApiParams apiParams = new ApiParams();
						int i = 0;
						StringBuilder sbfileBase64 = new StringBuilder();

						StringBuilder sbContent = new StringBuilder();
						StringBuilder sbfile = new StringBuilder();

						for (RichTextEditor.EditData itemData : editList) {
							if (itemData.inputStr != null) {
								sbContent.append(itemData.inputStr);
							} else if (itemData.imagePath != null) {
								sbContent.append(String.format("<<image%s>>.jpg", i));
								sbfile.append(String.format("<<image%s>>.jpg;", i));
								sbfileBase64.append(Base64Util.bitmap2Base64(itemData.bitmap)).append(";");
								i++;
							}
						}
						apiParams.with("e.styleName", editTitle.getText().toString())
								.with("e.userId", UserInfoUtil.getInstance().getUserId() + "")
								.with("e.styleContent", sbContent.toString())
								.with("e.styleSubContent", sbContent.toString()).with("e.styleDraft", 1 + "")
								.with("e.styleRand", getCheckRadio() + "").with("e.file", sbfile.toString())
								.with("e.fileBase64", sbfileBase64.toString());
						return Observable.just(new Downloader(getContext()).getJsonContent(
								ConstantsUtil.UrlConstant.HOST_URL + "myStyleServices!publishStyle.action", apiParams));
					}
				}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<String>(this) {
					@Override
					public void onSuccess(String result) {
						progressDialog.dismiss();
						Result data = JsonHelper.getResult(result, new TypeToken<Result>() {
						});
						if (data != null && data.isHasReturnValidCode()) {
							ToastUtil.showToast("发布成功！");
						} else {
							ToastUtil.showToast("发布失败，请重试！");
						}
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						progressDialog.dismiss();
						ToastUtil.showToast("发布失败，请重试！");
					}
				});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		}

		if (requestCode == REQUEST_CODE_PICK_IMAGE) {
			Uri uri = data.getData();
			insertBitmap(getRealFilePath(uri));
		} else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
			insertBitmap(mCurrentPhotoFile.getAbsolutePath());
		}
	}

	public String getRealFilePath(final Uri uri) {
		if (null == uri) {
			return null;
		}

		final String scheme = uri.getScheme();
		String data = null;
		if (scheme == null) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
			data = uri.getPath();
		} else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
			Cursor cursor = getContentResolver().query(uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null,
					null, null);
			if (null != cursor) {
				if (cursor.moveToFirst()) {
					int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
					if (index > -1) {
						data = cursor.getString(index);
					}
				}
				cursor.close();
			}
		}
		return data;
	}

	/**
	 * 添加图片到富文本剪辑器
	 *
	 * @param imagePath
	 */
	private void insertBitmap(String imagePath) {
		editor.insertImage(imagePath);
	}

	private static final File PHOTO_DIR = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
	private File mCurrentPhotoFile;// 照相机拍照得到的图片

	protected void openCamera() {
		try {
			// Launch camera to take photo for selected contact
			PHOTO_DIR.mkdirs();// 创建照片的存储目录
			mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			startActivityForResult(intent, REQUEST_CODE_CAPTURE_CAMEIA);
		} catch (ActivityNotFoundException e) {
		}
	}

	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date) + ".jpg";
	}

	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

}
