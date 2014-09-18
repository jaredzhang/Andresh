package com.example.exp.app.widget;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.animation.AnimationUtils;

import java.util.Random;

public class ColorAnimationDrawable extends Drawable implements Animatable {
	private final Paint paint = new Paint();
	
	private static final long ANIMATION_DURATION = 1500;
	private static final int FRAME_DURATION = 1000/60;
	
	private static final int ACCENT_COLOR = 0x33FFFFFF;
	private static final int DIM_COLOR = 0x33000000;
	
	private static final Random random = new Random();
	
	private int mStartColor;
	private int mEndColor;
	private int mCurrentColor;
	private boolean mIsRunning;
	
	private long mStartTime;

	
	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return mIsRunning;
	}

	@Override
	public void start() {
		if(!isRunning()) {
			mIsRunning = true;
			
			mStartTime = AnimationUtils.currentAnimationTimeMillis();
			mStartColor = randomColor();
			mEndColor = randomColor();
			mCurrentColor = mStartColor;
			Log.d(""," start color "+String.format("%06X", mStartColor));
			Log.d(""," end color "+String.format("%06X", mEndColor));
			scheduleSelf(mUpdater, SystemClock.uptimeMillis()+FRAME_DURATION);
			invalidateSelf();
		}
	}

	private int randomColor() {
		return random.nextInt() & 0x00FFFFFF | 0xFF000000;
	}

	@Override
	public void stop() {
		if(isRunning()) {
			mIsRunning = false;
			unscheduleSelf(mUpdater);
		}
	}

	@Override
	public void draw(Canvas canvas) {
		final Rect bound = getBounds();
		
		paint.setColor(mCurrentColor);
		canvas.drawRect(bound, paint);
		
		paint.setColor(ACCENT_COLOR);
		canvas.drawRect(bound.left, bound.top, bound.right, bound.top+1, paint);
		
		paint.setColor(DIM_COLOR);
		canvas.drawRect(bound.left, bound.bottom-2, bound.right, bound.bottom, paint);

	}

	@Override
	public int getOpacity() {
		return PixelFormat.TRANSPARENT;
	}

	@Override
	public void setAlpha(int arg0) {
		oops("setAlpha(int)");
	}

	@Override
	public void setColorFilter(ColorFilter arg0) {
		oops("setColorFilter(ColorFilter)");
	}

	private void oops(String string) {
		throw new UnsupportedOperationException("ColorAnimationDrawable doesn't support "+string);
	}

	private Runnable mUpdater = new Runnable() {
		
		@Override
		public void run() {
			long now = AnimationUtils.currentAnimationTimeMillis();
			long duration = now - mStartTime;
			if(duration >= ANIMATION_DURATION) {
				mStartTime = now;
				mStartColor = mEndColor;
				mEndColor = randomColor();
				mCurrentColor = mStartColor;
				Log.d(""," start color "+String.format("%06X", mStartColor));
				Log.d(""," end color "+String.format("%06X", mEndColor));
			} else {
				float fraction = (float)duration / ANIMATION_DURATION;
				mCurrentColor = Color.rgb(evaluation(fraction,Color.red(mStartColor),Color.red(mEndColor)), evaluation(fraction,Color.green(mStartColor),Color.green(mEndColor)), evaluation(fraction,Color.blue(mStartColor),Color.blue(mEndColor)));
			}
			scheduleSelf(this, SystemClock.uptimeMillis()+FRAME_DURATION);
			invalidateSelf();
		}
	};


	protected int evaluation(float fraction, int red, int red2) {
		return (int)(red+(red2 - red) * fraction);
	}

	public int getmCurrentColor() {
		return mCurrentColor;
	}

	public void setmCurrentColor(int mCurrentColor) {
		this.mCurrentColor = mCurrentColor;
	}
	
	
}
