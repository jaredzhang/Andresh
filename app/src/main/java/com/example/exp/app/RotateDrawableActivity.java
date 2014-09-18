package com.example.exp.app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Property;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RotateDrawableActivity extends Activity {

    static final int MAX_LEVEL = 5000;
    Drawable drawable;
    View idle;
    TextView header;
    Property<View, Integer> property;
    boolean isExpanded = false;
    int contentHeight;
    ObjectAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate_drawable);
        drawable = this.getResources().getDrawable(R.drawable.rotate_drawable);
        header = (TextView)this.findViewById(R.id.header);
        contentHeight = 400;
        idle = new ExpandView(this);
        ((ViewGroup)this.findViewById(R.id.root)).addView(idle, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, contentHeight));

        header.setCompoundDrawablesWithIntrinsicBounds(null, null, drawable, null);
        header.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(animator != null && animator.isRunning())return;
                header.setEnabled(false);
//
//				if(contentHeight==0 && isExpanded) {
//					contentHeight = idle.getHeight();
//				}
                animator = ObjectAnimator.ofInt(idle, "clipHeight",isExpanded?0:contentHeight);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        drawable.setLevel((int)(MAX_LEVEL*animation.getAnimatedFraction()+(isExpanded?MAX_LEVEL:0)));
                        drawable.invalidateSelf();
                    }
                });
                animator.addListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        isExpanded = !isExpanded;
                        header.setEnabled(true);
                    }

                });
                animator.start();
            }
        });
        property = new Property<View, Integer>(Integer.class, "viewLayoutHeight") {

            @Override
            public Integer get(View object) {
                return object.getLayoutParams().height;
            }

            @Override
            public void set(View object, Integer value) {
                object.getLayoutParams().height = value;
                object.requestLayout();
            }

        };
    }


    class ExpandView extends TextView {
        private int clipHeight = 0;

        public ExpandView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            // TODO Auto-generated constructor stub
        }

        public ExpandView(Context context, AttributeSet attrs) {
            super(context, attrs);
            // TODO Auto-generated constructor stub
        }

        public ExpandView(Context context) {
            super(context);
            setText("WTF WTF WTF ");
            setTextSize(40);
            //setGravity(gravity)
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.clipRect(0, 0, getWidth(), clipHeight);
            canvas.drawColor(0XFF00FF00);
            super.onDraw(canvas);
            //Log.d("", " testing onDraw ");
        }

        public int getClipHeight() {
            return clipHeight;
        }

        public void setClipHeight(int clipHeight) {
            this.clipHeight = clipHeight;
            invalidate(0, 0, getWidth(), clipHeight);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
