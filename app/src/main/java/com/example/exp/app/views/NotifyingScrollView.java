package com.example.exp.app.views;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;


public class NotifyingScrollView extends ScrollView {

	public NotifyingScrollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public NotifyingScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public NotifyingScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	public OnScrollChangedListener getmOnScrollChangedListener() {
		return mOnScrollChangedListener;
	}

	public void setmOnScrollChangedListener(OnScrollChangedListener mOnScrollChangedListener) {
		this.mOnScrollChangedListener = mOnScrollChangedListener;
	}


	public static interface OnScrollChangedListener {
		void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt);
	}
	
	private OnScrollChangedListener mOnScrollChangedListener;

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
		if(mOnScrollChangedListener != null)
			mOnScrollChangedListener.onScrollChanged(this, l, t, oldl, oldt);
	}
	
	private boolean mIsOverScrollEnabled = true;

	public void setOverScrollEnabled(boolean enabled) {
	    mIsOverScrollEnabled = enabled;
	}

	public boolean isOverScrollEnabled() {
	    return mIsOverScrollEnabled;
	}

	@Override
	protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
	                               int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
	    return super.overScrollBy(
	            deltaX,
	            deltaY,
	            scrollX,
	            scrollY,
	            scrollRangeX,
	            scrollRangeY,
	            mIsOverScrollEnabled ? maxOverScrollX : 0,
	            mIsOverScrollEnabled ? maxOverScrollY : 0,
	            isTouchEvent);
	}
	
}
