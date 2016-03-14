package com.softwarenation.superpatient;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class GoToJournalActivity extends Activity {

    private Button goToJournalButton;

    public static boolean enteredGoToJournal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to_journal);
        enteredGoToJournal=true;

        goToJournalButton =(Button) findViewById(R.id.go_to_journal_button);
        goToJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GoToJournalActivity.this,SelectMoodActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



}
