package com.softwarenation.superpatient;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.softwarenation.superpatient.utility.GlobalData;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class FollowSuperPatientActivity extends Activity {

    private TextView timerTextView;
    private TextView correctTextView;
    private TextView pointsTextView;

    private ImageView markImageView;

    private CountDownTimer countDownTimer;
    private final String FORMAT = "%02d:%02d:%02d";
    private long timeCountdown = 60000;

    private int points = 0;
    private int correct = 0;

    private LinearLayout tableSpaceLayout;
    private LinearLayout rowsLayout;

    private LinearLayout row1;
    private LinearLayout row2;
    private LinearLayout row3;
    private LinearLayout row4;
    private LinearLayout row5;

    private LinearLayout[] rows;


    private Boolean shouldSetLayout = false;

    private Random random;

    private Handler handler;

    private int imageCount;
    private int totalImageCount;

    private int layoutTag = 0;
    private int currentLayoutTag = 0;

    private ImageView menuButton;

    final private int GAME_MENU_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_super_patient);

        random = new Random();

        handler = new Handler();

        markImageView = (ImageView) findViewById(R.id.follow_super_patient_mark);

        timerTextView = (TextView) findViewById(R.id.follow_super_patient_time);
        correctTextView = (TextView) findViewById(R.id.follow_super_patient_correct);
        pointsTextView = (TextView) findViewById(R.id.follow_super_patient_points);

        tableSpaceLayout = (LinearLayout) findViewById(R.id.follow_super_patient_table_space);
        rowsLayout = (LinearLayout) findViewById(R.id.follow_super_patient_rows);

        row1 = (LinearLayout) findViewById(R.id.follow_super_patient_layoutrow_1);
        row2 = (LinearLayout) findViewById(R.id.follow_super_patient_layoutrow_2);
        row3 = (LinearLayout) findViewById(R.id.follow_super_patient_layoutrow_3);
        row4 = (LinearLayout) findViewById(R.id.follow_super_patient_layoutrow_4);
        row5 = (LinearLayout) findViewById(R.id.follow_super_patient_layoutrow_5);

        rows = new LinearLayout[]{row1,row2,row3,row4,row5};

        menuButton = (ImageView) findViewById(R.id.follow_super_patient_menu_button);

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FollowSuperPatientActivity.this,GameMenu.class);
                intent.putExtra(getString(R.string.game_menu_game_key),getString(R.string.main_game_follow_super_patient));
                startActivityForResult(intent, GAME_MENU_REQUEST);
            }
        });

        shouldSetLayout=true;

        for(LinearLayout row : rows){
            for(int i=0; i<4;i++){
                row.getChildAt(i).setOnClickListener(null);
            }
        }


        imageCount = 3;
        totalImageCount = imageCount;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus&&shouldSetLayout) {
            shouldSetLayout = false;

            int layoutHeight = tableSpaceLayout.getMeasuredHeight();
            int layoutWidth = tableSpaceLayout.getMeasuredWidth();

            int width = layoutWidth / 4;

            if (width * 5 <= layoutHeight) {
                int newSize = layoutWidth / 4;

                for (LinearLayout row : rows) {
                    for (int i = 0; i < 4; i++) {
                        LinearLayout.LayoutParams paramsRow = (LinearLayout.LayoutParams) row.getChildAt(i).getLayoutParams();
                        paramsRow.height = newSize;
                        row.getChildAt(i).requestLayout();
                    }
                }
            } else {
                int newSize = layoutHeight / 5;

                LinearLayout.LayoutParams paramsTable = (LinearLayout.LayoutParams) rowsLayout.getLayoutParams();
                paramsTable.width = newSize * 4;
                rowsLayout.requestLayout();
                for (LinearLayout row : rows) {
                    for (int i = 0; i < 4; i++) {
                        LinearLayout.LayoutParams paramsRow = (LinearLayout.LayoutParams) row.getChildAt(i).getLayoutParams();
                        paramsRow.height = newSize;
                        paramsRow.width = newSize;
                        row.getChildAt(i).requestLayout();
                    }

                }
            }

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setImage();
                }
            },200);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        int randomRowInt = random.nextInt(5);
//        int randomCellInt = random.nextInt(4);
//        LinearLayout row = (LinearLayout)rowsLayout.getChildAt(randomRowInt);
//        LinearLayout cell = (LinearLayout) row.getChildAt(randomCellInt);
//        setImage(cell);

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
        Intent intent = new Intent(FollowSuperPatientActivity.this, MainActivity.class);
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
                GlobalData.getInstance().showGameOverDialog(FollowSuperPatientActivity.this, getString(R.string.main_game_follow_super_patient), points);
            }
        };
        countDownTimer.start();
    }

    private void setImage(){

        int randomRowInt = random.nextInt(5);
        int randomCellInt = random.nextInt(4);
        LinearLayout row = (LinearLayout)rowsLayout.getChildAt(randomRowInt);
        LinearLayout cell = (LinearLayout) row.getChildAt(randomCellInt);

        if(cell.getChildAt(0)==null){
        ImageView imageView = new ImageView(FollowSuperPatientActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.journal_feeling_accomplished);
        cell.addView(imageView);

            cell.setTag(layoutTag++);

        Animation scaleIn = AnimationUtils.loadAnimation(FollowSuperPatientActivity.this,R.anim.scale_in);
        scaleIn.setFillAfter(true);
        imageView.startAnimation(scaleIn);
            imageCount--;
            if(imageCount!=0){
                Log.e("Entered in nextImage", "ImageCount: "+imageCount);
                setNextImage();

            }else{
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        for(LinearLayout rowFor : rows){
                            for(int i=0; i<4;i++){
                                rowFor.getChildAt(i).setOnClickListener(layoutClickListener);
                            }
                        }
                    }
                },450);
            }
        }
        else{
            Log.e("Recursive","true");
            setImage();
        }

    }

    private void removeImage(LinearLayout layout){
        ImageView imageView = (ImageView) layout.getChildAt(0);
        Animation scaleOut = AnimationUtils.loadAnimation(FollowSuperPatientActivity.this,R.anim.scale_out);
        scaleOut.setFillAfter(true);
        imageView.startAnimation(scaleOut);
    }

    private View.OnClickListener layoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            LinearLayout layout = (LinearLayout) v;
//            if(layout.getChildAt(0)==null){
//
//            setImage(layout);
//            }else{
//                removeImage(layout);
//            }

            if((int) layout.getTag()== currentLayoutTag){

                currentLayoutTag++;
                removeImage(layout);

                if(currentLayoutTag == totalImageCount){
                 correct++;
                points+=10;
                correctTextView.setText(String.valueOf(correct));
                pointsTextView.setText(String.valueOf(points));
                    setMark(true);
                    startNewGame();
                }


            } else{
                setMark(false);
                startNewGame();
            }

        }
    };

    private void startNewGame(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for(LinearLayout row : rows){
                    for(int i = 0;i<4;i++){
                        LinearLayout layout = (LinearLayout) row.getChildAt(i);
                        layout.removeAllViews();
                        layout.setOnClickListener(null);
                    }
                }

                layoutTag = 0;
                currentLayoutTag = 0;

                switch (correct){
                    case 0:case 1:
                        imageCount = 3;
                        break;
                    case 2:case 3:case 4:
                        imageCount = 4;
                        break;
                    case 5:case 6:case 7:case 8:
                        imageCount = 5;
                        break;
                    case 9:case 10:case 11:case 12:case 13:
                        imageCount = 6;
                        break;
                    case 14:case 15:case 16:case 17:case 18:case 19:
                        imageCount = 7;
                        break;
                    case 20:case 21:case 22:case 23:case 24:case 25:case 26:
                        imageCount = 8;
                        break;
                }

                totalImageCount = imageCount;
                setImage();
            }
        },450);

    }

    private void setNextImage(){
        // calculate the proximate duration of the animations
        int duration = 450;

        //creating timer
        Timer timer_interact=new Timer();
        timer_interact.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        setImage();
                    }
                });

            }
        }, duration);
    }

    private void setMark(Boolean correct){

        for(LinearLayout rowFor : rows){
            for(int i=0; i<4;i++){
                rowFor.getChildAt(i).setOnClickListener(null);
            }
        }

        Animation scaleInOut = AnimationUtils.loadAnimation(FollowSuperPatientActivity.this,R.anim.scale_in_out_mark);
        markImageView.setVisibility(View.VISIBLE);

        if(correct)
            markImageView.setImageResource(R.drawable.correct_big);
        else
        markImageView.setImageResource(R.drawable.incorrect_big);

        markImageView.startAnimation(scaleInOut);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                markImageView.setVisibility(View.GONE);
            }
        }, 450);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra(getString(R.string.game_menu_tag));
            if (result != null) {

                if(requestCode == GAME_MENU_REQUEST){

                    if(result.equals(getString(R.string.game_menu_tag_resume))){

                        startNewGame();

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
