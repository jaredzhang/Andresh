package com.example.exp.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ComposeShader;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.os.Bundle;

import java.util.HashMap;
import java.util.Map;


public class ReflectionActivity extends Activity {

    Map<Bitmap, Bitmap> reflectionMap = new HashMap<Bitmap, Bitmap>();
    Bitmap originalBitmap, reflectionBitmap;
    static final float HEIGHT_RATIO = 0.5f;
    static final int GAP = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relection);
//		originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
//		reflectionBitmap = createReflection(originalBitmap);
//		((ImageView)findViewById(R.id.iv_reflection)).setImageBitmap(reflectionBitmap);

    }

    private Bitmap createReflection(Bitmap originalBitmap) {
        if (reflectionMap.containsKey(originalBitmap) && reflectionMap.get(originalBitmap) != null)
            return reflectionMap.get(originalBitmap);

        int width = originalBitmap.getWidth();
        int height = (int) (originalBitmap.getHeight() * HEIGHT_RATIO);

        Bitmap reflectionBitmap = Bitmap.createBitmap(width, originalBitmap.getHeight() + GAP + height, Bitmap.Config.ARGB_8888);


        Bitmap blurredBitmap = Bitmap.createBitmap(originalBitmap, 0, originalBitmap.getHeight() - height, width, height);

        // first scale the bitmap
        blurredBitmap = Bitmap.createScaledBitmap(Bitmap.createScaledBitmap(blurredBitmap, width / 2, height / 2, true), width, height, true);

        BitmapShader shader = new BitmapShader(blurredBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        // flip the shader
        Matrix invertMatrix = new Matrix();
        invertMatrix.setScale(1, -1);
        invertMatrix.preTranslate(0, -(originalBitmap.getHeight() + GAP + height));
        shader.setLocalMatrix(invertMatrix);

        Shader alphaShader = new LinearGradient(0, originalBitmap.getHeight() + GAP, 0, originalBitmap.getHeight() + GAP + height, 0x80FFFFFF, 0x00000000, Shader.TileMode.CLAMP);
        ComposeShader compositeShader = new ComposeShader(shader, alphaShader, PorterDuff.Mode.DST_IN);

        Paint paint = new Paint();
        paint.setShader(compositeShader);

        Canvas canvas = new Canvas(reflectionBitmap);
        canvas.drawBitmap(originalBitmap, 0, 0, null);
        canvas.drawRect(0, originalBitmap.getHeight() + GAP, width, originalBitmap.getHeight() + GAP + height, paint);

        return reflectionBitmap;
    }
}
