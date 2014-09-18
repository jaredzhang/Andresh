package com.example.exp.app;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.exp.app.utils.AnimatorUtils;


public class AnimateTextColorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animate_text_color);
        mTextView = (TextView)this.findViewById(R.id.textView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    boolean ToRedOrToBlack;
    TextView mTextView;

    public void animate(View view) {
        if(ToRedOrToBlack) {
            AnimatorUtils.animate(mTextView, Color.BLACK);
        } else {
            AnimatorUtils.animate(mTextView,Color.RED);
        }
        ToRedOrToBlack = !ToRedOrToBlack;
    }

}
