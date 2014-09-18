package com.example.exp.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.exp.app.views.CropImageView;


public class CropImageActivity extends Activity {

    CropImageView iv;
    ImageView ivCaptured;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_image);
        iv = new CropImageView(this);
        ivCaptured = ((ImageView) findViewById(R.id.ivCaptured));
        iv.setImageResource(R.drawable.photo);
        ((ViewGroup) this.findViewById(R.id.root)).addView(iv, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.crop_image, menu);
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


    boolean captured = false;

    public void onCaptureClick(View view) {
        if(!captured) {
            ivCaptured.setVisibility(View.VISIBLE);
            ivCaptured.setImageBitmap(iv.captureBitmap());
        } else {
            if (ivCaptured.getDrawable() != null) {
                ivCaptured.getDrawable().setCallback(null);
            }
            ivCaptured.setImageDrawable(null);
            ivCaptured.setVisibility(View.GONE);
        }
        captured = !captured;
    }
}
