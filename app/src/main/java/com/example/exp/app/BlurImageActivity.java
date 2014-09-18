package com.example.exp.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.TimingLogger;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;


public class BlurImageActivity extends Activity {

    ImageView mImageView;
    TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_image);
        mImageView = (ImageView)this.findViewById(R.id.image);
        mTextView = (TextView)this.findViewById(R.id.text);
        mTextView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mTextView.getViewTreeObserver().removeOnPreDrawListener(this);
                Drawable drawable = mImageView.getDrawable();
                if(drawable != null && drawable instanceof BitmapDrawable) {
                    blur(((BitmapDrawable)drawable).getBitmap(),mTextView,25);
                }
                return true;
            }
        });
    }

    public static final String TAG = "Blurring";
    void blur(Bitmap bkg, View view, float radius) {
        TimingLogger tl = new TimingLogger(TAG, "blur");
        Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        tl.addSplit("Bitmap.createBitmap");

        Canvas canvas = new Canvas(overlay);
        tl.addSplit("new Canvas()");

        canvas.drawBitmap(bkg,-view.getLeft(),-view.getTop(),null);
        tl.addSplit("canvans.drawBitmap()");

        RenderScript rs = RenderScript.create(this);
        tl.addSplit("RenderScript.create()");

        Allocation overlayAlloc = Allocation.createFromBitmap(rs,overlay);
        tl.addSplit("Allocation.createFromBitmap");

        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs,overlayAlloc.getElement());
        tl.addSplit("ScriptIntrinsicBlur.create()");

        blur.setInput(overlayAlloc);
        tl.addSplit("blur.setInput");

        blur.setRadius(radius);
        tl.addSplit("blur.setRadius()");

        blur.forEach(overlayAlloc);
        tl.addSplit("blur.forEach()");

        overlayAlloc.copyTo(overlay);
        tl.addSplit("overlayAlloc.copyTo()");

        view.setBackground(new BitmapDrawable(getResources(),overlay));
        tl.addSplit("view.setBackground()");

        rs.destroy();
        tl.addSplit("rs.destroy()");
        tl.dumpToLog();
    }
}
