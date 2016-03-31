package com.luohe.android.luohe.view.stickyheadersrecyclerview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

public class StickyRecyclerFootersDecoration extends RecyclerView.ItemDecoration {
    private boolean mNeedSticky = true;
    private View mFooterView;
    private Rect mFooterRects;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
//        int orientation = getOrientation(parent);
//        int itemPosition = parent.getChildPosition(view);
//        if (hasNewFooter(itemPosition)) {
//            View footer = getFooterView(parent, itemPosition);
//            if (orientation == LinearLayoutManager.VERTICAL) {
//                outRect.top = footer.getHeight();
//            } else {
//                outRect.left = footer.getWidth();
//            }
//        }
    }

    private boolean needSticky() {
        return mNeedSticky;
    }

    public void setNeedSticky(boolean needSticky) {
        this.mNeedSticky = needSticky;
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);

        if (needSticky()) {
            View footerView = getFooterView(parent, 0);
            int translationX = Math.max(parent.getLeft() - footerView.getWidth(), 0);
            int translationY = Math.max(parent.getTop() - footerView.getHeight(), 0);
            footerView.setFocusableInTouchMode(true);
            footerView.setFocusable(true);
            footerView.setFilterTouchesWhenObscured(true);
            canvas.save();
            canvas.translate(translationX, translationY);
            footerView.draw(canvas);
            canvas.restore();
            mFooterRects = new Rect(translationX, translationY,
                    translationX + footerView.getWidth(), translationY + footerView.getHeight());
        }
    }

    private int getOrientation(RecyclerView parent) {
        if (parent.getLayoutManager() instanceof LinearLayoutManager) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            return layoutManager.getOrientation();
        } else {
            throw new IllegalStateException("StickyListHeadersDecoration can only be used with a " +
                    "LinearLayoutManager.");
        }
    }

    /**
     * Gets the header view for the associated position.  If it doesn't exist yet, it will be
     * created, measured, and laid out.
     *
     * @param parent
     * @param position
     * @return Header view
     */
    public View getFooterView(RecyclerView parent, int position) {
        if (mFooterView == null) return null;
        if (mFooterView.getLayoutParams() == null) {
            mFooterView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        int widthSpec;
        int heightSpec;

        if (getOrientation(parent) == LinearLayoutManager.VERTICAL) {
            widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
            heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.UNSPECIFIED);
        } else {
            widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.UNSPECIFIED);
            heightSpec = View.MeasureSpec.makeMeasureSpec(parent.getHeight(), View.MeasureSpec.EXACTLY);
        }

        int childWidth = ViewGroup.getChildMeasureSpec(widthSpec,
                parent.getPaddingLeft() + parent.getPaddingRight(), mFooterView.getLayoutParams().width);
        int childHeight = ViewGroup.getChildMeasureSpec(heightSpec,
                parent.getPaddingTop() + parent.getPaddingBottom(), mFooterView.getLayoutParams().height);
        mFooterView.measure(childWidth, childHeight);
        mFooterView.layout(0, 0, mFooterView.getMeasuredWidth(), mFooterView.getMeasuredHeight());
        return mFooterView;
    }

    public View getFooterView() {
        return mFooterView;
    }

    public void setFooterView(View footerView) {
        mFooterView = footerView;
    }

    /**
     * Gets the position of the header under the specified (x, y) coordinates.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @return position of header, or -1 if not found
     */
    public int findHeaderPositionUnder(int x, int y) {
        Rect rect = mFooterRects;
        if (rect.contains(x, y)) {
            return 1;
        }
        return -1;
    }

    private boolean hasNewFooter(int position) {
        return mFooterView != null;
    }
}
