package com.luohe.android.luohe.find;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidplus.ui.ToastManager;
import com.androidplus.util.LogUtils;
import com.luohe.android.luohe.R;
import com.luohe.android.luohe.base.AppCompatActivity;
import com.luohe.android.luohe.common.ConstantsUtil;
import com.luohe.android.luohe.common.MyApplication;
import com.luohe.android.luohe.luohe.FragmentAdapter;
import com.luohe.android.luohe.user.UserInfoUtil;
import com.luohe.android.luohe.utils.LocalDisplay;
import com.mob.commons.logcollector.e;
import com.mob.tools.utils.Data;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * 互动页面
 */
public class FindReactActivity extends AppCompatActivity implements View.OnClickListener {

    @Bind(R.id.tab_layout)
    TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager viewPager;
    @Bind(R.id.head_bg)
    ImageView headBg;
    @Bind(R.id.head_name)
    TextView headName;
    @Bind(R.id.head_avatar)
    ImageView headAvatar;

    HeadBgDialog headBgDialog;
    private static String mLocalHeadBgPath;

    protected void init(Bundle savedInstanceState) {
        getTitlebar().setTitle(getString(R.string.find_react));
        getTitlebar().setDefauleBackBtn();
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("仅好友");
        headName.setText(UserInfoUtil.getInstance().getUserInfo().getComUserInfo().nickName);
        ImageLoader.getInstance().displayImage(UserInfoUtil.getInstance().getUserInfo().getComUserInfo().headUrl, headAvatar);

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String name = list.get(i);
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            fragments.add(Fragment.instantiate(this, FindReactFragment.class.getName(), bundle));
        }
        FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments, list);
        viewPager.setAdapter(fragmentAdapter);
        viewPager.setOffscreenPageLimit(list.size() - 1);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(fragmentAdapter);
        //todo on 2016/06/03 设置头背景
        headBg.setOnClickListener(this);
    }
    protected int onBindLayoutId() {
        return R.layout.activity_find_react;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ConstantsUtil.RequestCode.GO_TO_CAMERA:
                    headBg.setImageURI(data.getData());
                    break;

                case ConstantsUtil.RequestCode.GO_TO_ALBUM:
                    headBg.setImageURI(data.getData());
                    break;
            }
        }
    }
    /**
     * 设置头背景
     */

    protected class HeadBgDialog extends DialogFragment implements View.OnClickListener {
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
            mLocalHeadBgPath = createImagePath();
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 调用系统自带照相机
            Uri photoUri = Uri.fromFile(new File(mLocalHeadBgPath));
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
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_bg:
                if (headBgDialog == null)
                    headBgDialog = new HeadBgDialog();
                headBgDialog.show(getSupportFragmentManager(), "headBg");
                break;
        }
    }
}

