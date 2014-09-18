package com.example.exp.app.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;

import com.example.exp.app.R;

/**
 * Created by zhuodong on 4/8/14.
 */
public class CropImageView extends TouchImageView {

    Drawable mFrame;
    Rect mFrameBound;
    int margin, frameSize;
    int colorOverlay = 0x5C000000;

    public CropImageView(Context context) {
        this(context, null, 0);
    }

    public CropImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CropImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mFrame = getResources().getDrawable(R.drawable.img_user_profile_photo_corner);
        margin = getResources().getDimensionPixelOffset(R.dimen.frame_margin);
        mFrameBound = new Rect();
        this.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        setMinZoom(0.7f);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(w > h) {
//            frameSize = h/2;
//            margin = h/4;
            frameSize = h-2*margin;
            mFrameBound.set((w - frameSize) / 2, margin, (w + frameSize) / 2,
                    frameSize + margin);
        } else {
//            frameSize = w/2;
//            margin = w/4;
            frameSize = w-2*margin;
            mFrameBound.set(margin, (h - frameSize) / 2, frameSize + margin,
                    (h + frameSize) / 2);
        }
        mFrame.setBounds(mFrameBound);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mFrame.draw(canvas);

        int clipRect = canvas.save();
        canvas.clipRect(mFrameBound, Region.Op.DIFFERENCE);
        canvas.drawColor(colorOverlay);
        canvas.restoreToCount(clipRect);
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    public Bitmap captureBitmap() {
        Bitmap bitmap = Bitmap.createBitmap(mFrameBound.width(),mFrameBound.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.translate(-mFrameBound.left,-mFrameBound.top);
        canvas.concat(getImageMatrix());
        getDrawable().draw(canvas);
        return bitmap;
    }
}

