package com.luohe.android.luohe.mine;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Author: GanQuan Date: 16/6/11 Email:ganquan3640@gmail.com
 */

public class Base64Util {
	public static String bitmap2Base64(Bitmap bitmap) {
		String imageString = "";
		if (bitmap != null) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, out);
			byte[] bytes = out.toByteArray();
			imageString = Base64.encodeToString(bytes, Base64.DEFAULT);
		}
		return imageString;
	}
}
