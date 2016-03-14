package com.softwarenation.superpatient;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softwarenation.superpatient.utility.GlobalData;


public class MainActivity extends Activity {

    private Button superJournalButton;

    private com.softwarenation.superpatient.utility.AnimationStanding animation;

    private Boolean doubleBackToExitPressedOnce = false;

    private TextView superDigitsText;
    private TextView superMashUpText;
    private TextView superColorBlockText;
    private TextView superSpeedText;
    private TextView superFollowText;

    private LinearLayout superDigitsLayout;
    private LinearLayout superMashupLayout;
    private LinearLayout superColorBlockLayout;
    private LinearLayout superSpeedLayout;
    private LinearLayout superFollowLayout;

    private String selectedGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpGameLayouts();

        superJournalButton = (Button) findViewById(R.id.main_super_journal_button);
        superJournalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SelectMoodActivity.class);
                startActivity(intent);
            }
        });

        animation = (com.softwarenation.superpatient.utility.AnimationStanding) findViewById(R.id.main_animation_standing);
        Log.e("Animation","Height: "+animation.getHeight()+" Width: "+animation.getWidth());

        superDigitsText = (TextView) findViewById(R.id.main_super_digits_text);
        superMashUpText = (TextView) findViewById(R.id.main_super_mash_up_text);
        superColorBlockText = (TextView) findViewById(R.id.main_super_color_block_text);
        superSpeedText = (TextView) findViewById(R.id.main_super_speed_text);
        superFollowText = (TextView) findViewById(R.id.main_follow_super_patient_text);

        if(GlobalData.getInstance().getSize(this)<4){
            superDigitsText.setTextSize(15);
            superDigitsText.setPadding(0, 1, 0, 0);
            superMashUpText.setTextSize(15);
            superMashUpText.setPadding(0, 1, 0, 0);
            superColorBlockText.setTextSize(15);
            superColorBlockText.setPadding(0, 1, 0, 0);
            superSpeedText.setTextSize(15);
            superSpeedText.setPadding(0, 1, 0, 0);
            superFollowText.setTextSize(15);
            superFollowText.setPadding(0,1,0,0);


        }


    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            finish();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 1500);
    }

    private void setUpGameLayouts(){
        superDigitsLayout = (LinearLayout) findViewById(R.id.main_super_digits);
        superMashupLayout = (LinearLayout) findViewById(R.id.main_super_mash_up);
        superColorBlockLayout = (LinearLayout) findViewById(R.id.main_super_color_block);
        superSpeedLayout = (LinearLayout) findViewById(R.id.main_super_speed);
        superFollowLayout = (LinearLayout) findViewById(R.id.main_follow_super_patient);

        superDigitsLayout.setOnClickListener(startGameIntro);
        superMashupLayout.setOnClickListener(startGameIntro);
        superColorBlockLayout.setOnClickListener(startGameIntro);
        superSpeedLayout.setOnClickListener(startGameIntro);
        superFollowLayout.setOnClickListener(startGameIntro);

    }

    private View.OnClickListener startGameIntro= new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.main_super_digits:
                    selectedGame = getString(R.string.main_game_super_digits);
                    break;
                case R.id.main_super_mash_up:
                    selectedGame = getString(R.string.main_game_super_mash_up);
                    break;
                case R.id.main_super_color_block:
                    selectedGame = getString(R.string.main_game_super_color_block);
                    break;
                case R.id.main_super_speed:
                    selectedGame = getString(R.string.main_game_super_speed);
                    break;
                case R.id.main_follow_super_patient:
                    selectedGame = getString(R.string.main_game_follow_super_patient);
                    break;
            }

            Intent intent = new Intent(MainActivity.this, GamesIntroActivity.class);
            intent.putExtra(getString(R.string.main_game_key),selectedGame);
            startActivity(intent);
            finish();
        }
    };


}
