package com.example.exp.app;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity {

//    String[] sampleTitles = new String[] {"Animate TextView Color", "Rotate Drawable", "Load Key Value Pair from XML", "Access Resource Via Uri", "Adding ActionBar Logo Via Styling ", "Seeker Animation","Level List Drawable"};
//    Class[] activityClasses = new Class[] {AnimateTextColorActivity.class, RotateDrawableActivity.class, ParseXMLToBundle.class, UriActivity.class, ActionBarLogoActivity.class,SeekerAnimationActivity.class,LevelListAnimationActivity.class};
//    List<String> sampleTitles;
    List<String> sampleTitles = new ArrayList<String>();
    List<String> sampleActivityNames = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            ActivityInfo[] activities = getPackageManager().getPackageInfo(getPackageName(),
                    PackageManager.GET_ACTIVITIES).activities;

            for(ActivityInfo activityInfo:activities) {
                Log.d("",activityInfo.name);
                if(!activityInfo.name.equals(MainActivity.class.getName())) {
                    sampleTitles.add(activityInfo.loadLabel(getPackageManager()).toString());
                    sampleActivityNames.add(activityInfo.name);
                }
            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        getListView().setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,sampleTitles));
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClassName(MainActivity.this,sampleActivityNames.get(position));
                startActivity(intent);
            }
        });

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

}
