package com.uc3m.hackmenot;

import uc3m.hackmenot.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;

public class TaskActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_activity);

        // Make sure we're running on Honeycomb or higher to use ActionBar APIs
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        
        Intent intent = getIntent();
        String message = intent.getStringExtra("package");
        Log.d("test",message);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
