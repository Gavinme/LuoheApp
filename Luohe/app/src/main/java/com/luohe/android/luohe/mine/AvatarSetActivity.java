package com.luohe.android.luohe.mine;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.androidplus.ui.ToastManager;
import com.androidplus.util.LogUtils;
import com.androidplus.util.Md5Util;
import com.androidplus.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.common.MyApplication;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.net.Downloader;
import com.luohe.android.luohe.net.data.ApiParams;
import com.luohe.android.luohe.net.data.GsonRequest;
import com.luohe.android.luohe.net.data.RequestManager;
import com.luohe.android.luohe.net.data.Result;
import com.luohe.android.luohe.net.http.ApiLoader;
import com.luohe.android.luohe.net.http.CommonSubscriber;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.ImageUtils;
import com.luohe.android.luohe.utils.LocalDisplay;
import com.luohe.android.luohe.utils.ToastUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.Bind;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by GanQuan on 16/3/20.
 */
public class AvatarSetActivity extends AppCompatActivity {
	private static final int INTENT_CROP = 100;

	AvatarDialog avatarDialog;
	private static String mUploadedPath;
	@Bind(R.id.avatar)
	PhotoView avatar;

	@Override
	protected void init(Bundle savedInstanceState) {
		getTitlebar().setTitle("更换头像");
		getTitlebar().setDefauleBackBtn();
		getTitlebar().addAction(new TitleBar.ImageAction(R.drawable.ipay_ui_more) {
			@Override
			public void performAction(View view) {
				if (avatarDialog == null)
					avatarDialog = new AvatarDialog();
				avatarDialog.show(getSupportFragmentManager(), "avatar");
			}
		});
		if (avatarDialog == null)
			avatarDialog = new AvatarDialog();
		avatarDialog.show(getSupportFragmentManager(), "avatar");
		ImageUtils.displayImage(UserInfoUtil.getInstance().getUserInfo().getComUserInfo().headUrl, avatar);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			switch (requestCode) {
			case ConstantsUtil.RequestCode.GO_TO_CAMERA:
				gotoCrop(Uri.fromFile(new File(mUploadedPath)), Uri.fromFile(new File(getCropImgPath(mUploadedPath))));
				break;
			case INTENT_CROP:
				upLoadImg(getCropImgPath(mUploadedPath));
				break;
			case ConstantsUtil.RequestCode.GO_TO_ALBUM:
				Uri selectedImage = data.getData();
				String[] filePathColumns = { MediaStore.Images.Media.DATA };
				Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
				c.moveToFirst();
				int columnIndex = c.getColumnIndex(filePathColumns[0]);
				mUploadedPath = c.getString(columnIndex);
				gotoCrop(Uri.fromFile(new File(mUploadedPath)), Uri.fromFile(new File(getCropImgPath(mUploadedPath))));
				c.close();
			}
		}
	}

	private String getCropImgPath(String src) {
		String suffix = src.substring(src.lastIndexOf(".") + 1);

		if (!TextUtils.isEmpty(suffix)) {
			src = src.substring(0, src.lastIndexOf("."));
			return src + "_crop." + suffix;
		} else {
			return src + "_crop";
		}
	}

	private void upLoadImg(final String path) {
		final ProgressDialog progressDialog = ProgressDialog.show(this, "上传中...", null);
		Observable
				.just(path)
				.flatMap(new Func1<String, Observable<String>>() {
					@Override
					public Observable<String> call(String path) {
						String imgString = getImageString(path);
						ApiParams apiParams = new ApiParams();
						apiParams.with("fileBase64", imgString).with("fileFileName", new File(path).getName());
						return Observable.just(new Downloader(getContext()).getJsonContent(
								ConstantsUtil.UrlConstant.HOST_URL + "accountSettingServices!changePhoto.action",
								apiParams));
					}

				}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<String>(this) {
					@Override
					public void onSuccess(String result) {
						progressDialog.dismiss();
						Result result1 = new Gson().fromJson(result, new TypeToken<Result>() {
						}.getType());
						if (result1.isHasReturnValidCode()) {
							ToastUtil.showToast("上传头像成功");
						} else {
							ToastUtil.showToast("上传头像失败");
						}
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						Log.e("gq", e.toString());
						ToastUtil.showToast("上传头像失败");
					}
				});
	}

	private void gotoUpLoadImg(String mUploadedPath) {
		final ProgressDialog progressDialog = ProgressDialog.show(this, "上传中...", null);
		Observable.just(mUploadedPath).flatMap(new Func1<String, Observable<Result>>() {
			@Override
			public Observable<Result> call(String s) {
				String base64 = getImageString(s);
				String fileName = new File(s).getName();
				RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), base64);
				return ApiLoader.getApiService().changePhoto(requestBody, fileName);
			}
		}).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
				.subscribe(new CommonSubscriber<Result>(this) {
					@Override
					public void onSuccess(Result result) {
						progressDialog.dismiss();
						if (result.isHasReturnValidCode()) {
							ToastUtil.showToast("上传头像成功");
						} else {
							ToastUtil.showToast("上传头像失败");
						}
					}

					@Override
					public void onError(Throwable e) {
						super.onError(e);
						progressDialog.dismiss();
						ToastUtil.showToast("上传头像失败");
					}
				});
	}

	public static String getImageString(String imgFilePath) {
		String imageString = null;
		Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath);
		if (bitmap != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
			byte[] bytes = out.toByteArray();
			imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
		}
		return imageString;
	}

	private void gotoCrop(Uri srcUri, Uri dstUri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(srcUri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, dstUri);// 图像输出
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("return-data", false);
		startActivityForResult(intent, INTENT_CROP);
	}

	@Override
	protected int onBindLayoutId() {
		return R.layout.activity_avatar_set;
	}

	public static class AvatarDialog extends DialogFragment implements View.OnClickListener {

		@NonNull
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Dialog dialog = new Dialog(getActivity(), R.style.Dialog);
			dialog.setContentView(R.layout.activity_my_user_menu);
			dialog.findViewById(R.id.camera).setOnClickListener(this);
			dialog.findViewById(R.id.ablum).setOnClickListener(this);
			dialog.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					dialog.dismiss();
				}
			});
			dialog.setCanceledOnTouchOutside(true);
			Window window = dialog.getWindow();
			WindowManager.LayoutParams params = window.getAttributes();
			params.width = LocalDisplay.SCREEN_WIDTH_PIXELS;
			params.y = (LocalDisplay.SCREEN_HEIGHT_PIXELS - params.height) / 2;
			window.setAttributes(params);
			window.setWindowAnimations(R.style.DialogWindowAnim);
			return dialog;
		}

		private void goToCamera() {
			mUploadedPath = createImagePath();
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用系统自带照相机
			Uri photoUri = Uri.fromFile(new File(mUploadedPath));
			intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);// 设置图像的Uri存储地址
			getActivity().startActivityForResult(intent, ConstantsUtil.RequestCode.GO_TO_CAMERA);
		}

		private String createImagePath() {
			String imageName = System.currentTimeMillis() + ".jpg";
			return com.nostra13.universalimageloader.core.ImageLoader.getInstance().getDiskCache().getDirectory()
					+ File.separator + imageName;
		}

		private void goToAlbum() {
			try {
				if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
					ToastManager.getInstance(MyApplication.getContext()).makeToast("SD卡加载不正确，不能选择相册", true);
				} else {
					Intent picture = new Intent(Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					getActivity().startActivityForResult(picture, ConstantsUtil.RequestCode.GO_TO_ALBUM);
				}
			} catch (ActivityNotFoundException e) {
				ToastManager.getInstance(getActivity()).makeToast("你没有选择图片", true);
			}
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.camera:
				this.dismiss();
				goToCamera();
				break;
			case R.id.ablum:
				this.dismiss();
				goToAlbum();
				break;
			}
		}
	}
}
