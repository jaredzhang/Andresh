package com.example.exp.app;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;

import com.example.exp.app.views.NotifyingScrollView;


public class AnimateActionBarActivity extends Activity implements Drawable.Callback{

    Drawable background;

    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_action_bar);
        background = new ColorDrawable(0xFFBF2100);
        background.setAlpha(0);
        getActionBar().setBackgroundDrawable(background);

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1)
            background.setCallback(this);

        final View headerView = findViewById(R.id.image_header);

        ((NotifyingScrollView)this.findViewById(R.id.sv)).setOverScrollEnabled(false);
        ((NotifyingScrollView)this.findViewById(R.id.sv)).setmOnScrollChangedListener(new NotifyingScrollView.OnScrollChangedListener() {

            @Override
            public void onScrollChanged(ScrollView who, int l, int t, int oldl, int oldt) {
                int height = headerView.getHeight();
                float alpha = Math.min(Math.max(0, t), height)/(float)height;
                background.setAlpha((int)(alpha*255));
                background.invalidateSelf();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }

    @Override
    public void invalidateDrawable(Drawable arg0) {
        Log.d("", " set background ");
        getActionBar().setBackgroundDrawable(arg0);
    }

    @Override
    public void scheduleDrawable(Drawable arg0, Runnable arg1, long arg2) {
    }

    @Override
    public void unscheduleDrawable(Drawable arg0, Runnable arg1) {
    }

}
