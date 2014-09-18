package com.example.exp.app.utils;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.util.Property;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

/**
 * Created by zhuodong on 3/27/14.
 */
public class AnimatorUtils {
    static Property<TextView, Integer> property = new Property<TextView, Integer>(int.class,"textColor") {
        @Override
        public Integer get(TextView object) {
            return object.getCurrentTextColor();
        }

        @Override
        public void set(TextView object, Integer value) {
            object.setTextColor(value);
        }
    };

    static ObjectAnimator animator;

    public static void animate(TextView tv,int... color) {
        animator = ObjectAnimator.ofInt(tv,property,color);
        animator.setEvaluator(new ArgbEvaluator());
        animator.setDuration(300);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    public static boolean isAnimating() {
        if(animator == null)return false;
        return animator.isRunning();
    }
}
