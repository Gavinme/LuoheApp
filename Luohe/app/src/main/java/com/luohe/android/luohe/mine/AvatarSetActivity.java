package com.luohe.android.luohe.mine;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.androidplus.ui.ToastManager;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.common.MyApplication;
import com.luohe.android.luohe.common.TitleBar;
import com.luohe.android.luohe.utils.LocalDisplay;

import java.io.File;

import butterknife.Bind;
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
        getTitlebar().addAction(new TitleBar.ImageAction(R.drawable.icon_tab_redian) {
            @Override
            public void performAction(View view) {
                if (avatarDialog == null)
                    avatarDialog = new AvatarDialog();
                avatarDialog.show(getSupportFragmentManager(), "avatar");
            }
        });
        avatar.setImageResource(R.drawable.icon_tab_redian);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ConstantsUtil.RequestCode.GO_TO_CAMERA:
                    gotoCrop(Uri.fromFile(new File(mUploadedPath)),
                            Uri.fromFile(new File(mUploadedPath.replace(".jpg", "_crop.jpg"))));
                    break;
                case INTENT_CROP:
                    break;
                case ConstantsUtil.RequestCode.GO_TO_ALBUM:
                    Uri selectedImage = data.getData();
                    String[] filePathColumns={MediaStore.Images.Media.DATA};
                    Cursor c = this.getContentResolver().query(selectedImage, filePathColumns, null,null, null);
                    c.moveToFirst();
                    int columnIndex = c.getColumnIndex(filePathColumns[0]);
                    mUploadedPath= c.getString(columnIndex);
                    gotoCrop(Uri.fromFile(new File(mUploadedPath)),
                            Uri.fromFile(new File(mUploadedPath.replace(".jpg", "_crop.jpg"))));
                    c.close();
            }
        }
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
        // 回调方法data.getExtras().getParcelable("data")返回数据为空
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
                    Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   getActivity(). startActivityForResult(picture, ConstantsUtil.RequestCode.GO_TO_ALBUM);
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
