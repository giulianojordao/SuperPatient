package com.softwarenation.superpatient;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


public class SelectMoodActivity extends ActionBarActivity {

    private android.support.v7.app.ActionBar actionBar;

    private LinearLayout feelingGoodLayout;
    private LinearLayout notFeelingGoodLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_mood);

        feelingGoodLayout = (LinearLayout) findViewById(R.id.select_mood_feeling_good_layout);
        notFeelingGoodLayout = (LinearLayout) findViewById(R.id.select_mood_not_feeling_good_layout);

        actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#584B8D")));

        if(!GoToJournalActivity.enteredGoToJournal){
        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);
        actionBar.setDisplayHomeAsUpEnabled(true);
        }else{
            actionBar.setDisplayHomeAsUpEnabled(false);
            GoToJournalActivity.enteredGoToJournal=false;
        }

        feelingGoodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectMoodActivity.this, JournalFeelingGoodActivity.class);
                startActivity(intent);
                finish();

            }
        });

        notFeelingGoodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectMoodActivity.this, JournalNotFeelingGoodActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_select_mood, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.select_mood_calendar_button:
                Toast.makeText(this,"Calendar button clicked",Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
