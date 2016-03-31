package com.luohe.android.luohe.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.luohe.android.luohe.R;

/**
 * Created by GanQuan on 16/3/10.
 */
public class FragmentUtils {
    public static void ReplaceFragment(Context context, FragmentManager fragmentManager, int layoutId, String clazzName, Bundle bundle) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag(clazzName);
        if (fragment == null) {
            fragment = Fragment.instantiate(context, clazzName);
            fragment.setArguments(bundle);
        }

        if (!fragment.isAdded()) {
            transaction.replace(layoutId, fragment, clazzName);
        }

        transaction.commitAllowingStateLoss();
    }


}
