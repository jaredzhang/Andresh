package com.example.exp.app;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class LevelListAnimationActivity extends Activity {

    Drawable mDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_list_animation);
        mDrawable = this.getResources().getDrawable(R.drawable.level_drawable);
        ((ImageView) findViewById(R.id.iv)).setImageDrawable(mDrawable);

        if(mDrawable instanceof LevelListDrawable) {
            ((LevelListDrawable) mDrawable).setEnterFadeDuration(200);
            ((LevelListDrawable) mDrawable).setExitFadeDuration(200);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.level_list_animation, menu);
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

    ValueAnimator animator;

    public void startAnimation(View view) {
        if(animator != null && animator.isRunning())
            animator.cancel();
        animator = ValueAnimator.ofInt(40).setDuration(20000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mDrawable.setLevel(((Integer) animation.getAnimatedValue()) % 4);
            }
        });
        animator.start();
    }

}
