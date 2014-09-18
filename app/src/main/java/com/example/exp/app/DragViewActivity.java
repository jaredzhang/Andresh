package com.example.exp.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/*
Drag View Helper Demo
 */

public class DragViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DragLayout mDragLayout = new DragLayout(this);
        ImageView iv = new ImageView(this);
        iv.setImageResource(R.drawable.ic_launcher);
        mDragLayout.addView(iv,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        setContentView(mDragLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drag_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    static class DragLayout extends FrameLayout {

        private static final int INVALID_POINTER_ID = -1;

        // The ‘active pointer’ is the one currently moving our object.
        private int mActivePointerId = INVALID_POINTER_ID;

        public DragLayout(Context context) {
            this(context, null, 0);
        }

        public DragLayout(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public DragLayout(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            init();
        }

        ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                return true;
            }

            @Override
            public void onViewDragStateChanged(int state) {
                super.onViewDragStateChanged(state);
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);
            }

            @Override
            public void onViewCaptured(View capturedChild, int activePointerId) {
                super.onViewCaptured(capturedChild, activePointerId);
                Log.d("","onViewCaptured");
            }

            @Override
            public void onViewReleased(View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
            }

            @Override
            public void onEdgeTouched(int edgeFlags, int pointerId) {
                super.onEdgeTouched(edgeFlags, pointerId);
            }

            @Override
            public boolean onEdgeLock(int edgeFlags) {
                return super.onEdgeLock(edgeFlags);
            }

            @Override
            public void onEdgeDragStarted(int edgeFlags, int pointerId) {
                super.onEdgeDragStarted(edgeFlags, pointerId);
            }

            @Override
            public int getOrderedChildIndex(int index) {
                return super.getOrderedChildIndex(index);
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return super.getViewHorizontalDragRange(child);
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return super.getViewVerticalDragRange(child);
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - getPaddingRight();
                final int newLeft = Math.min(Math.max(left, leftBound),rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight()-getPaddingBottom();
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
            }
        };

        ViewDragHelper mViewDragHelper;

        private void init() {
            mViewDragHelper = ViewDragHelper.create(this, 1.0f, mDragCallback);
            //mViewDragHelper.setEdgeTrackingEnabled();
            mViewDragHelper.setMinVelocity(ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity());
        }


        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            //handleMultiTouch(ev);
            boolean shouldIntercept = mViewDragHelper.shouldInterceptTouchEvent(ev);
//            Log.d(""," shouldIntercept "+shouldIntercept);
//            Log.d(""," dragState "+ mViewDragHelper.getViewDragState());
//            Log.d(""," capturedView "+ mViewDragHelper.getCapturedView());

            return shouldIntercept;
            //return super.onInterceptTouchEvent(ev);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            //handleMultiTouch(event);
            mViewDragHelper.processTouchEvent(event);
            return true;
        }

//        @Override
//        protected void onLayout(boolean changed, int l, int t, int r, int b) {
//
//        }


        private void handleMultiTouch(MotionEvent event) {
            int actionMask = MotionEventCompat.getActionMasked(event);
            switch (actionMask) {
                case MotionEvent.ACTION_DOWN:
                    mActivePointerId = event.getPointerId(0);
                case MotionEventCompat.ACTION_POINTER_UP:
                    final int pointerIndex = MotionEventCompat.getActionIndex(event);
                    if (event.getPointerId(pointerIndex) == mActivePointerId) {
                        final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                        mActivePointerId = event.getPointerId(newPointerIndex);
                    }
                case MotionEvent.ACTION_UP: {
                    mActivePointerId = INVALID_POINTER_ID;
                    break;
                }
            }
        }
    }

   }
