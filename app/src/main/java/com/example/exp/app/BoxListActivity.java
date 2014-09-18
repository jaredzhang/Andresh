package com.example.exp.app;

import android.app.ListActivity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

import greendao.Box;
import greendao.BoxDao;
import greendao.DaoMaster;
import greendao.DaoSession;

/*
DEMO THE USAGE OF GREENDAO and SQL ASSET HELPER
 */

public class BoxListActivity extends ListActivity {

    BoxDao mBoxDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_box_list);
        setupDatabase();

        setListAdapter(new ArrayAdapter<Box>(this,android.R.layout.simple_list_item_1,mBoxDao.loadAll()));
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"example.sqlite",null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession mDaoSession = daoMaster.newSession();
        mBoxDao = mDaoSession.getBoxDao();

        Box box = new Box();
        box.setId(1l);
        box.setName("box 1");
        box.setDescription("This is my box. I can put in it anything I wish.");
        mBoxDao.insertOrReplace(box);

        Box box2 = new Box();
        box2.setId(2l);
        box2.setName("box 2");
        box2.setDescription("This is my box. I can put in it anything I wish.");
        mBoxDao.insertOrReplace(box2);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.box_list, menu);
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
