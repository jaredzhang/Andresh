package com.example.exp.app;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

/*
  Android resources Uris must respect the following format:
  android.resource://[package_name]/[res_type]/[res_id]
  see: https://plus.google.com/118417777153109946393/posts/3MEe8ABsvjY

    while you can use identifier directly in your app, you can share your resources in this way to other app

   */
public class UriActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uri);
        ((ImageView) this.findViewById(R.id.imageView)).setImageURI(Uri.parse("android.resource://"+BuildConfig.PACKAGE_NAME+"/drawable/"+R.drawable.rotate_drawable));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.uri, menu);
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

}
