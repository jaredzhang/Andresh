package com.example.exp.app;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.TouchDelegate;
import android.view.View;

/*
demo how to enlarge the touch space
 */
public class TouchDelegateActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_delegate);
        final View root = this.findViewById(R.id.root);
        final View button = this.findViewById(R.id.btn);

        root.post(new Runnable() {

            @Override
            public void run() {
//                Rect rect = new Rect();
//                button.getHitRect(rect);
//                rect.inset(-touchAreaAddition, -touchAreaAddition);
//                TouchDelegate delegate = new TouchDelegate(rect, button);
//                root.setTouchDelegate(delegate);

                enlargeTouchArea(root,button);
            }
        });
    }

    static int touchAreaAddition = 100;

    public static void enlargeTouchArea(final View parent, final View child) {
        parent.post(new Runnable() {
            @Override
            public void run() {
                int locationChild[] = new int[2];
                child.getLocationOnScreen(locationChild);

                int locationParent[] = new int[2];
                parent.getLocationOnScreen(locationParent);

                int left = locationChild[0] - locationParent[0];
                int top = locationChild[1] - locationParent[1];

                Rect rect = new Rect(left,top,left+child.getWidth(),top+child.getHeight());
                rect.inset(-touchAreaAddition, -touchAreaAddition);

                TouchDelegate delegate = new TouchDelegate(rect, child);
                parent.setTouchDelegate(delegate);
            }
        });
    }
}
