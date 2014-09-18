package com.example.exp.app.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class ReflectionLayout extends FrameLayout {

	static final float HEIGHT_RATIO = 0.5f;
	static final int GAP = 2;

	public ReflectionLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public ReflectionLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public ReflectionLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	Bitmap mOriginalBitmap, mReflectionBitmap;
	Canvas mCanvas;
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
//		canvas.save();
//		canvas.clipRect(0, 0, getWidth(), getHeight()/2);
//		super.dispatchDraw(canvas);
//		canvas.restore();
		
		final boolean initBimapAndCanvas =  !(mOriginalBitmap != null && !mOriginalBitmap.isRecycled());
		if(initBimapAndCanvas) {
			mOriginalBitmap = Bitmap.createBitmap(getWidth(), getHeight()/2, Config.ARGB_8888);
			mCanvas = new Canvas(mOriginalBitmap);
		}
		super.dispatchDraw(mCanvas);
		
		final boolean recreateReflectionBitmap = !(mReflectionBitmap != null && !mReflectionBitmap.isRecycled());
		
		if(recreateReflectionBitmap) {
			mReflectionBitmap = createReflection(mOriginalBitmap);
		}
		canvas.drawBitmap(mReflectionBitmap, 0, 0, null);
	}
	
	private Bitmap createReflection(Bitmap originalBitmap) {
		
		int width = originalBitmap.getWidth();
		int height =(int) (originalBitmap.getHeight() * HEIGHT_RATIO);
		
		Bitmap reflectionBitmap = Bitmap.createBitmap(width, originalBitmap.getHeight() + GAP + height, Config.ARGB_8888);
		
		Bitmap blurredBitmap = Bitmap.createBitmap(originalBitmap, 0, originalBitmap.getHeight()-height, width, height);

		// first scale the bitmap
		blurredBitmap = Bitmap.createScaledBitmap(Bitmap.createScaledBitmap(blurredBitmap, width/2, height/2, true), width, height, true);
		
		BitmapShader shader = new BitmapShader(blurredBitmap, TileMode.CLAMP, TileMode.CLAMP);
		// flip the shader
		Matrix invertMatrix = new Matrix();
		invertMatrix.setScale(1, -1);
		invertMatrix.preTranslate(0,-(originalBitmap.getHeight() + GAP + height) );
		shader.setLocalMatrix(invertMatrix);
		
		Shader alphaShader = new LinearGradient(0,originalBitmap.getHeight() + GAP,0,originalBitmap.getHeight() + GAP + height,0x80FFFFFF,0x00000000,TileMode.CLAMP);
		ComposeShader compositeShader = new ComposeShader(shader, alphaShader, Mode.DST_IN);
		
		Paint paint = new Paint();
		paint.setShader(compositeShader);
		
		Canvas canvas = new Canvas(reflectionBitmap);
		canvas.drawBitmap(originalBitmap, 0, 0, null);
		canvas.drawRect(0, originalBitmap.getHeight() + GAP , width,  originalBitmap.getHeight() + GAP +height, paint);
		
		return reflectionBitmap;
	}
	
	
	
}
