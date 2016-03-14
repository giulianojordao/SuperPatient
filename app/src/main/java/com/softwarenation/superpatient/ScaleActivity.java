package com.softwarenation.superpatient;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;


public class ScaleActivity extends Activity {

    private TextView cancelButton;

    private TableRow row1;
    private TableRow row2;
    private TableRow row3;
    private TableRow row4;
    private TableRow row5;
    private TableRow row6;
    private TableRow row7;
    private TableRow row8;
    private TableRow row9;
    private TableRow row10;


    static private final String TAG = "Scale";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scale);

        setUpRows();

        cancelButton = (TextView) findViewById(R.id.scale_cancel_textview);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScaleActivity.this,JournalNotFeelingGoodActivity.class);
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });
    }

    private void setUpRows(){
        row1 = (TableRow) findViewById(R.id.scale_value_1);
        row2 = (TableRow) findViewById(R.id.scale_value_2);
        row3 = (TableRow) findViewById(R.id.scale_value_3);
        row4 = (TableRow) findViewById(R.id.scale_value_4);
        row5 = (TableRow) findViewById(R.id.scale_value_5);
        row6 = (TableRow) findViewById(R.id.scale_value_6);
        row7 = (TableRow) findViewById(R.id.scale_value_7);
        row8 = (TableRow) findViewById(R.id.scale_value_8);
        row9 = (TableRow) findViewById(R.id.scale_value_9);
        row10 = (TableRow) findViewById(R.id.scale_value_10);

        row1.setOnClickListener(setScale);
        row2.setOnClickListener(setScale);
        row3.setOnClickListener(setScale);
        row4.setOnClickListener(setScale);
        row5.setOnClickListener(setScale);
        row6.setOnClickListener(setScale);
        row7.setOnClickListener(setScale);
        row8.setOnClickListener(setScale);
        row9.setOnClickListener(setScale);
        row10.setOnClickListener(setScale);
    }

    private View.OnClickListener setScale= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int value = 0;
            switch (v.getId()){
                case R.id.scale_value_1 : value=1;
                    break;
                case R.id.scale_value_2 : value=2;
                    break;
                case R.id.scale_value_3 : value=3;
                    break;
                case R.id.scale_value_4 : value=4;
                    break;
                case R.id.scale_value_5 : value=5;
                    break;
                case R.id.scale_value_6 : value=6;
                    break;
                case R.id.scale_value_7 : value=7;
                    break;
                case R.id.scale_value_8 : value=8;
                    break;
                case R.id.scale_value_9 : value=9;
                    break;
                case R.id.scale_value_10 : value=10;
                    break;
            }
            Intent intent = new Intent(ScaleActivity.this, JournalNotFeelingGoodActivity.class);
            Log.e("value",value+"");
            if(value!=0) {
                intent.putExtra(TAG, value);
                setResult(RESULT_OK, intent);
                finish();
            }

        }
    };
}
