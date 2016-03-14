package com.softwarenation.superpatient;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class GameMenu extends Activity {

    private String pausedGame;

    private ImageView soundImage;

    private TextView gameTitle;
    private TextView pointsTextView;
    private TextView pointsBestTextView;
    private TextView pointsTodaysTextView;

    private Button buttonResume;
    private Button buttonRestart;
    private Button buttonHow;
    private Button buttonHome;

    private Class gameToRestart;

    final private int GAME_MENU_REQUEST = 1;

    final private int GAME_MENU_HOW_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        gameTitle = (TextView) findViewById(R.id.game_menu_game_title);
        pointsTextView = (TextView) findViewById(R.id.game_menu_points);
        pointsBestTextView = (TextView) findViewById(R.id.game_menu_points_best_score);
        pointsTodaysTextView = (TextView) findViewById(R.id.game_menu_points_today_score);

        buttonResume = (Button) findViewById(R.id.game_menu_button_resume);
        buttonRestart = (Button) findViewById(R.id.game_menu_button_restart);
        buttonHow = (Button) findViewById(R.id.game_menu_button_how);
        buttonHome = (Button) findViewById(R.id.game_menu_button_home);

        soundImage = (ImageView) findViewById(R.id.game_menu_sound);

        Intent intent = getIntent();
        if(intent != null){
            pausedGame = intent.getStringExtra(getString(R.string.game_menu_game_key));
        }

        if(pausedGame!=null){
            if(pausedGame.equals(getString(R.string.main_game_super_digits))){
                gameTitle.setText(getString(R.string.super_digits_title));
                gameToRestart = SuperDigitsActivity.class;
            } else if(pausedGame.equals(getString(R.string.main_game_super_mash_up))){
                gameTitle.setText("Super Mash Up");
                gameToRestart = SuperDigitsActivity.class;
            }else if(pausedGame.equals(getString(R.string.main_game_super_color_block))){
                gameTitle.setText(getString(R.string.super_color_block_title));
                gameToRestart = SuperColorBlockActivity.class;
            }else if(pausedGame.equals(getString(R.string.main_game_super_speed))){
                gameTitle.setText(getString(R.string.super_speed_title));
                gameToRestart = SuperSpeedActivity.class;
            }else if(pausedGame.equals(getString(R.string.main_game_follow_super_patient))){
                gameTitle.setText(getString(R.string.follow_super_patient_title));
                gameToRestart = FollowSuperPatientActivity.class;
            }
        }


        buttonResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameMenu.this, gameToRestart);
                intent.putExtra(getString(R.string.game_menu_tag), getString(R.string.game_menu_tag_resume));
                if(pausedGame.equals(getString(R.string.main_game_super_mash_up)))
                intent.putExtra(getString(R.string.main_game_key),getString(R.string.main_game_super_mash_up));
                setResult(RESULT_OK, intent);
                 finish();

            }
        });

        buttonRestart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameMenu.this, gameToRestart);
                intent.putExtra(getString(R.string.game_menu_tag), getString(R.string.game_menu_tag_restart));
                setResult(RESULT_CANCELED, intent);

                Intent intent1 = new Intent(GameMenu.this, GamesIntroActivity.class);
                intent1.putExtra(getString(R.string.main_game_key),pausedGame);
                startActivity(intent1);
                finish();

            }
        });

        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(GameMenu.this, gameToRestart);
                intent1.putExtra(getString(R.string.game_menu_tag), getString(R.string.game_menu_tag_main));
                setResult(RESULT_CANCELED, intent1);

                Intent intent = new Intent(GameMenu.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonHow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameMenu.this, GamesIntroActivity.class);
                intent.putExtra(getString(R.string.main_game_from_menu_key),1);
                intent.putExtra(getString(R.string.main_game_key), pausedGame);
                startActivityForResult(intent,GAME_MENU_HOW_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra(getString(R.string.game_menu_game_key));
            if (result != null) {

                if(requestCode == GAME_MENU_HOW_REQUEST){

//                    pausedGame = result;
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(GameMenu.this, gameToRestart);
        intent.putExtra(getString(R.string.game_menu_tag), getString(R.string.game_menu_tag_resume));
        if(pausedGame.equals(getString(R.string.main_game_super_mash_up)))
            intent.putExtra(getString(R.string.main_game_key),getString(R.string.main_game_super_mash_up));
        setResult(RESULT_OK, intent);
        finish();

    }
}
