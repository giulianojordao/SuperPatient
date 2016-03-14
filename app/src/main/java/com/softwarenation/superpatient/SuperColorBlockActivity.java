package com.softwarenation.superpatient;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.softwarenation.superpatient.utility.GlobalData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class SuperColorBlockActivity extends Activity {

    private TextView timerTextView;

    private ImageView imageGame;
    private TextView textGame;

    private CountDownTimer countDownTimer;
    private final String FORMAT = "%02d:%02d:%02d";
    private long timeCountdown = 60000;

    private int[] colorsArray;
    private String[] colorNamesArray = {"RED","ORANGE","YELLOW","GREEN","BLUE","PURPLE"};
    private int[] imagesArray;
//    private Map<Integer, Integer> imageMap = new HashMap<Integer, Integer>();

    private Random randomInt;

    private ImageView buttonRed;
    private ImageView buttonOrange;
    private ImageView buttonYellow;
    private ImageView buttonGreen;
    private ImageView buttonBlue;
    private ImageView buttonPurple;

    private TextView pointsTextView;
    private TextView correctTextView;
    private TextView correctTextTextView;

    private ImageView correctMark;

    private int colorInt = 0;

    private int points = 0;
    private int correct = 0;

    private ImageView menuButton;

    final private int GAME_MENU_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_color_block);

        menuButton = (ImageView) findViewById(R.id.super_color_block_menu_button);

        timerTextView = (TextView) findViewById(R.id.super_color_block_timer);

        pointsTextView = (TextView) findViewById(R.id.super_color_block_points);
        correctTextView = (TextView) findViewById(R.id.super_color_block_correct);
        correctTextTextView = (TextView) findViewById(R.id.super_color_block_correct_text);

        correctMark = (ImageView) findViewById(R.id.super_color_block_correct_mark);

        imageGame = (ImageView) findViewById(R.id.super_color_block_image);
        textGame = (TextView) findViewById(R.id.super_color_block_text);

        buttonBlue = (ImageView) findViewById(R.id.super_color_block_button_blue);
        buttonOrange = (ImageView) findViewById(R.id.super_color_block_button_orange);
        buttonYellow = (ImageView) findViewById(R.id.super_color_block_button_yellow);
        buttonGreen = (ImageView) findViewById(R.id.super_color_block_button_green);
        buttonPurple = (ImageView) findViewById(R.id.super_color_block_button_purple);
        buttonRed = (ImageView) findViewById(R.id.super_color_block_button_red);

        colorsArray = getResources().getIntArray(R.array.superColorBlockColors);
//        imagesArray=getResources().getIntArray(R.array.superColorBlockImages);

        imagesArray = new int[]{R.drawable.super_color_block_basketball,
        R.drawable.super_color_block_car,
        R.drawable.super_color_block_car2,
        R.drawable.super_color_block_connected,
        R.drawable.super_color_block_cubes,
        R.drawable.super_color_block_gear,
        R.drawable.super_color_block_globe,
        R.drawable.super_color_block_heart,
        R.drawable.super_color_block_leaf,
        R.drawable.super_color_block_love,
        R.drawable.super_color_block_plane,
        R.drawable.super_color_block_puzzle,
        R.drawable.super_color_block_round,
        R.drawable.super_color_block_trophy,
        R.drawable.super_color_block_trophy2,
        R.drawable.super_color_block_umbrella,
        R.drawable.super_color_block_wrench};

        randomInt = new Random();

        chooseColor();

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuperColorBlockActivity.this, GameMenu.class);
                intent.putExtra(getString(R.string.game_menu_game_key), getString(R.string.main_game_super_color_block));
                startActivityForResult(intent, GAME_MENU_REQUEST);
            }
        });

        ImageView[] buttons = {buttonRed,buttonOrange,buttonYellow,buttonGreen,buttonBlue,buttonPurple};
        int buttonNumber = 0;
        for(final ImageView button : buttons){
                    setButtonCLickListener(button, buttonNumber++);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        countDownTimer.cancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SuperColorBlockActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startTimer() {
//        timerTextView.setTextColor(getResources().getColor(R.color.black));
        countDownTimer = new CountDownTimer(timeCountdown, 10) {

            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)), millisUntilFinished % 100
                ));
                timeCountdown = millisUntilFinished;
                if(millisUntilFinished<=10000)
                    timerTextView.setTextColor(getResources().getColor(R.color.NotFeelingGoodRed));
            }

            public void onFinish() {
                timerTextView.setText("00:00:00");
                GlobalData.getInstance().showGameOverDialog(SuperColorBlockActivity.this,getString(R.string.main_game_super_color_block),points);
            }
        };
        countDownTimer.start();
    }

    private void setButtonCLickListener(ImageView view, final int buttonNumber){
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Is true", String.valueOf(colorInt == buttonNumber));
                if (colorInt == buttonNumber) {
                    points += 10;
                    pointsTextView.setText(String.valueOf(points));
                    correctTextView.setText(String.valueOf(++correct));
                    correctTextTextView.setTextColor(getResources().getColor(R.color.SuperPatientGreen));
                    correctMark.setImageResource(R.drawable.correct_small);
                } else {
                    correctTextTextView.setTextColor(getResources().getColor(R.color.NotFeelingGoodRed));
                    correctMark.setImageResource(R.drawable.incorrect_small);
                }
                if (correctMark.getVisibility() == View.INVISIBLE)
                    correctMark.setVisibility(View.VISIBLE);

                chooseColor();
            }
        });
    }

    private void chooseColor(){

        correctTextTextView.postDelayed(new Runnable() {
            @Override
            public void run() {
                correctTextTextView.setTextColor(getResources().getColor(R.color.black));
                if(correctMark.getVisibility()==View.VISIBLE)
                    correctMark.setVisibility(View.INVISIBLE);
            }
        }, 250);

        colorInt = randomInt.nextInt(6);

        imageGame.setImageResource(imagesArray[randomInt.nextInt(17)]);
        textGame.setText(colorNamesArray[randomInt.nextInt(6)]);


        imageGame.setColorFilter(colorsArray[colorInt]);
        textGame.setTextColor(colorsArray[colorInt]);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra(getString(R.string.game_menu_tag));
            if (result != null) {

                Log.e("result",result);

                if(requestCode == GAME_MENU_REQUEST){

                    if(result.equals(getString(R.string.game_menu_tag_resume))){

                        chooseColor();

                    }
                }
            }
        } else if (resultCode == RESULT_CANCELED && data != null){
            String result = data.getStringExtra(getString(R.string.game_menu_tag));
            if (result != null) {
                if(requestCode == GAME_MENU_REQUEST){
                    if((result.equals(getString(R.string.game_menu_tag_main)))||(result.equals(getString(R.string.game_menu_tag_restart)))){
                        finish();
                    }
                }
            }
        }
    }


}
