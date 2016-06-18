package com.luohe.android.luohe.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;

/**
 * Author: GanQuan Date: 16/5/27 Email:ganquan3640@gmail.com
 */

public class NoScrollGridView extends GridView {
	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollGridView(Context context) {
		super(context);
	}

	public NoScrollGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		int expandSpec = View.MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, View.MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}