package com.luohe.android.luohe.view.stickyheadersrecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


public class StickyRecyclerFootersTouchListener implements RecyclerView.OnItemTouchListener {
    private final GestureDetector mTapDetector;
    private final RecyclerView mRecyclerView;
    private final StickyRecyclerFootersDecoration mDecor;
    private OnHeaderClickListener mOnHeaderClickListener;

    public StickyRecyclerFootersTouchListener(final RecyclerView recyclerView,
                                              final StickyRecyclerFootersDecoration decor) {
        mTapDetector = new GestureDetector(recyclerView.getContext(), new SingleTapDetector());
        mRecyclerView = recyclerView;
        mDecor = decor;
    }

    public StickyRecyclerHeadersAdapter getAdapter() {
        if (mRecyclerView.getAdapter() instanceof StickyRecyclerHeadersAdapter) {
            return (StickyRecyclerHeadersAdapter) mRecyclerView.getAdapter();
        } else {
            throw new IllegalStateException("A RecyclerView with " +
                    StickyRecyclerFootersTouchListener.class.getSimpleName() +
                    " requires a " + StickyRecyclerHeadersAdapter.class.getSimpleName());
        }
    }

    public void setOnHeaderClickListener(OnHeaderClickListener listener) {
        mOnHeaderClickListener = listener;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
        if (position != -1) {
            View headerView = mDecor.getFooterView();
            long headerId = getAdapter().getHeaderId(position);
//            mOnHeaderClickListener.onHeaderClick(headerView, position, headerId);
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent e) {
        View headerView = mDecor.getFooterView();
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }

    public interface OnHeaderClickListener {
        public void onHeaderClick(View header, int position, long headerId);
    }

    private class SingleTapDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            int position = mDecor.findHeaderPositionUnder((int) e.getX(), (int) e.getY());
            if (position != -1) {
                View headerView = mDecor.getFooterView(mRecyclerView, position);
                long headerId = getAdapter().getHeaderId(position);
                mOnHeaderClickListener.onHeaderClick(headerView, position, headerId);
                return true;
            }
            return false;
        }
    }
}
