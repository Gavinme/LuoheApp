package com.luohe.android.luohe.utils;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

public class FileUtil {
	private static final String TAG = "FileUtil";
	private static String encoding;

	public static boolean deleteFile(String path) {
		File f = new File(path);
		return f.exists() && f.delete();
	}

	public static int deleteAllFiles(File file) {
		if (file == null)
			return 0;

		if (file.isDirectory()) {
			int result = 0;
			File[] subs = file.listFiles();
			for (File sub : subs) {
				result += deleteAllFiles(sub);
			}

			result += file.delete() ? 1 : 0;
			return result;
		} else {
			Log.e(TAG, file.getName() + " deleted");
			return file.delete() ? 1 : 0;
		}
	}

	public static long calculateFileSize(File file) {
		if (file == null)
			return 0;
		long result = 0;
		if (file.isDirectory()) {
			for (File sub : file.listFiles())
				result += calculateFileSize(sub);
		} else {
			result = file.length();
		}
		return result;
	}

	/**
	 * 转换文件大小 *
	 */
	public static String formatFileSize(long fileSize) {
		DecimalFormat decimalFormat = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileSize < 1024) {
			fileSizeString = fileSize + " B";
		} else if (fileSize < 1048576) {
			fileSizeString = decimalFormat.format((double) fileSize / 1024) + " KB";
		} else if (fileSize < 1073741824) {
			fileSizeString = decimalFormat.format((double) fileSize / 1048576) + " MB";
		} else {
			fileSizeString = decimalFormat.format((double) fileSize / 1073741824) + " GB";
		}
		return fileSizeString;
	}

	public static boolean copyFile(String src, String target) {
		File s = new File(src);
		File t = new File(target);
		if (!s.exists())
			return false;

		if (t.exists())
			t.delete();

		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(s);
			output = new FileOutputStream(t);
			byte[] b = new byte[1024];
			while (input.read(b) != -1) {
				output.write(b);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (input != null)
					input.close();
				if (output != null)
					output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static boolean isSdCardOK() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	public static String getFilePathByUri(Activity activity, Uri uri) {

		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor actualimagecursor = activity.managedQuery(uri, proj, null, null, null);
		int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		actualimagecursor.moveToFirst();
		return actualimagecursor.getString(actual_image_column_index);
	}

	public static InputStream getAssetInputStream(Context context, String assetPath) {
		try {
			return context.getAssets().open(assetPath);
		} catch (IOException e) {
		}
		return null;
	}

	public static void deleteDir(File f) {
		if (f.exists() && f.isDirectory()) {
			for (File file : f.listFiles()) {
				if (file.isDirectory())
					deleteDir(file);
				file.delete();
			}
			f.delete();
		}
	}


}
