package com.softwarenation.superpatient;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.softwarenation.superpatient.utility.GlobalData;

import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class SuperSpeedActivity extends Activity {

    private TableRow cell1;
    private TableRow cell2;
    private TableRow cell3;
    private TableRow cell4;
    private TableRow cell5;
    private TableRow cell6;
    private TableRow cell7;
    private TableRow cell8;

    private TableRow[] cells;

    private LinearLayout tableSpaceLayout;
    private TableLayout tableLayout;

    private CountDownTimer countDownTimer;
    private final String FORMAT = "%02d:%02d:%02d";
    private long timeCountdown = 60000;

    private TextView timeText;
    private TextView correctTextView;
    private TextView pointsTextView;

    private int[] imagesArray;
    private ArrayList<String> availableImages;

    private ImageView targetImageView;
    private int targetImage;

    private Random random;

    private Boolean shouldSetLayout = false;

    private int points = 0;
    private int correct = 0;

    private Handler handler;

    private ImageView menuButton;

    final private int GAME_MENU_REQUEST = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_speed);

        menuButton = (ImageView) findViewById(R.id.super_speed_menu_button);

        tableSpaceLayout = (LinearLayout) findViewById(R.id.super_speed_table_space);
       tableLayout = (TableLayout) findViewById(R.id.super_speed_table);

        imagesArray = new int[]{
                R.drawable.super_speed_basketball,
                R.drawable.super_speed_car,
                R.drawable.super_speed_car2,
                R.drawable.super_speed_connected,
                R.drawable.super_speed_cubes,
                R.drawable.super_speed_gear,
                R.drawable.super_speed_globe,
                R.drawable.super_speed_heart,
                R.drawable.super_speed_leaf,
                R.drawable.super_speed_love,
                R.drawable.super_speed_plane,
                R.drawable.super_speed_puzzle,
                R.drawable.super_speed_round,
                R.drawable.super_speed_trophy,
                R.drawable.super_speed_trophy2,
                R.drawable.super_speed_umbrella,
                R.drawable.super_speed_wrench};

        random = new Random();

        availableImages = new ArrayList<>();

        cell1 = (TableRow) findViewById(R.id.super_speed_tablerow_1);
        cell2 = (TableRow) findViewById(R.id.super_speed_tablerow_2);
        cell3 = (TableRow) findViewById(R.id.super_speed_tablerow_3);
        cell4 = (TableRow) findViewById(R.id.super_speed_tablerow_4);
        cell5 = (TableRow) findViewById(R.id.super_speed_tablerow_5);
        cell6 = (TableRow) findViewById(R.id.super_speed_tablerow_6);
        cell7 = (TableRow) findViewById(R.id.super_speed_tablerow_7);
        cell8 = (TableRow) findViewById(R.id.super_speed_tablerow_8);

        cells = new TableRow[] {cell1,cell2,cell3,cell4,cell5,cell6,cell7,cell8};

        timeText = (TextView) findViewById(R.id.super_speed_time);
        correctTextView = (TextView) findViewById(R.id.super_speed_correct);
        pointsTextView = (TextView) findViewById(R.id.super_speed_points);

        targetImageView = (ImageView) findViewById(R.id.super_speed_target_image);

        handler = new Handler();

        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuperSpeedActivity.this,GameMenu.class);
                intent.putExtra(getString(R.string.game_menu_game_key),getString(R.string.main_game_super_speed));
                startActivityForResult(intent, GAME_MENU_REQUEST);
            }
        });


       newGame();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus&&shouldSetLayout) {
            shouldSetLayout = false;

            int layoutHeight = tableSpaceLayout.getMeasuredHeight();
            int layoutWidth = tableSpaceLayout.getMeasuredWidth();

//            Log.e("layoutHeight", String.valueOf(layoutHeight));
//            Log.e("layoutWidth", String.valueOf(layoutWidth));


            int width = layoutWidth / 6;

            if (width * 8 <= layoutHeight) {
                int newSize = layoutWidth / 6;

                for (TableRow cell : cells) {
                    for (int i = 0; i < 6; i++) {


                        LinearLayout.LayoutParams paramsCell = (LinearLayout.LayoutParams) cell.getChildAt(i).getLayoutParams();


                        paramsCell.height = newSize;

                        cell.getChildAt(i).requestLayout();
                    }


                }

            } else {
                int newSize = layoutHeight / 8;

                LinearLayout.LayoutParams paramsTable = (LinearLayout.LayoutParams) tableLayout.getLayoutParams();
                paramsTable.width = newSize * 6;
                tableLayout.requestLayout();
                for (TableRow cell : cells) {
                    for (int i = 0; i < 6; i++) {
                        LinearLayout.LayoutParams paramsCell = (LinearLayout.LayoutParams) cell.getChildAt(i).getLayoutParams();


                        paramsCell.height = newSize;
                        paramsCell.width = newSize;

                        cell.getChildAt(i).requestLayout();

                    }


                }
            }

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
        Intent intent = new Intent(SuperSpeedActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void startTimer() {
//        timerTextView.setTextColor(getResources().getColor(R.color.black));
        countDownTimer = new CountDownTimer(timeCountdown, 10) {

            public void onTick(long millisUntilFinished) {
                timeText.setText(String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)), millisUntilFinished % 100
                ));
                timeCountdown = millisUntilFinished;
                if(millisUntilFinished<=10000)
                    timeText.setTextColor(getResources().getColor(R.color.NotFeelingGoodRed));
            }

            public void onFinish() {
                timeText.setText("00:00:00");
                GlobalData.getInstance().showGameOverDialog(SuperSpeedActivity.this, getString(R.string.main_game_super_speed), points);
            }
        };
        countDownTimer.start();
    }

    private View.OnClickListener imageViewClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkIfSame(v, false);

        }
    };

    private View.OnClickListener imageLayoutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            checkIfSame(v, true);

        }
    };

    private void checkIfSame(View view, boolean isRelativeLayout){
        int imageResource = (Integer) view.getTag();
        if(targetImage == imageResource){
            points += 10;
            correct++;
            pointsTextView.setText(String.valueOf(points));
            correctTextView.setText(String.valueOf(correct));

            setMark(true, view,isRelativeLayout);
            changeDrawable(view, isRelativeLayout);
        }else{
            setMark(false, view, isRelativeLayout);
            clearMark(view, isRelativeLayout);
        }
    }

    private void setMark(Boolean correct, View view, boolean isRelativeLayout){
        ImageView imageView = new ImageView(SuperSpeedActivity.this);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
        imageView.setLayoutParams(params);
        if(correct) {
            imageView.setImageResource(R.drawable.correct_small);
        }else{
            imageView.setImageResource(R.drawable.incorrect_small);
        }
        if(isRelativeLayout){
            RelativeLayout layout = (RelativeLayout) view;
            layout.addView(imageView);

        }else{
            ImageView clickedImage = (ImageView) view;
            RelativeLayout layout = (RelativeLayout) clickedImage.getParent();
            layout.addView(imageView);
        }
        Animation scale_in = AnimationUtils.loadAnimation(SuperSpeedActivity.this,R.anim.scale_in_out);
        imageView.startAnimation(scale_in);

    }

    private void changeDrawable(final View view, final boolean isRelativeLayout){
        // calculate the proximate duration of the animations
        int duration = 300;

        //creating timer
        Timer timer_interact=new Timer();
        timer_interact.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        availableImages.remove(String.valueOf(targetImage));
                        if (isRelativeLayout) {
                            RelativeLayout layout = (RelativeLayout) view;
                            if (layout != null) {
                                layout.removeAllViewsInLayout();
                                layout.setOnClickListener(null);
                            }

                        } else {
                            ImageView imageView = (ImageView) view;
                            imageView.setOnClickListener(null);
                            RelativeLayout layout = (RelativeLayout) imageView.getParent();
                            if (layout != null) {
                                layout.removeAllViewsInLayout();
                                layout.setOnClickListener(null);
                            }
                        }

//                        Log.e("Available images", "size: " + availableImages.size());
                        int randomNumber = random.nextInt(availableImages.size());
                        targetImageView.setImageResource(Integer.valueOf(availableImages.get(randomNumber)));
                        targetImage = Integer.valueOf(availableImages.get(randomNumber));
                    }
                });

            }
        }, duration);
    }

    private void clearMark(final View view, final boolean isRelativeLayout){
        // calculate the proximate duration of the animations
        int duration = 300;

        //creating timer
        Timer timer_interact=new Timer();
        timer_interact.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        availableImages.remove(String.valueOf(targetImage));
                        if(isRelativeLayout){
                            RelativeLayout layout = (RelativeLayout) view;
                            if(layout!=null)
                            layout.removeViewAt(1);
                        }else{
                            ImageView imageView = (ImageView) view;
                            RelativeLayout layout = (RelativeLayout) imageView.getParent();
                            if(layout!=null)
                            layout.removeViewAt(1);
                        }
                    }
                });

            }
        }, duration);
    }

    private void newGame(){
        int color = getResources().getColor(R.color.SuperPatientGray);

        for(TableRow cell : cells){
            for(int i = 0; i<6;i++){
                int randomNumberImage = random.nextInt(17);
                RelativeLayout layout = (RelativeLayout) cell.getChildAt(i);
                ImageView imageView = (ImageView) layout.getChildAt(0);
                imageView.setImageResource(imagesArray[randomNumberImage]);

                availableImages.add(String.valueOf(imagesArray[randomNumberImage]));

                imageView.setColorFilter(color);
                imageView.setTag(imagesArray[randomNumberImage]);
                imageView.setOnClickListener(imageViewClickListener);
                layout.setTag(imagesArray[randomNumberImage]);
                layout.setOnClickListener(imageLayoutClickListener);
                layout.requestLayout();
            }
            cell.requestLayout();

        }
        shouldSetLayout = true;

//        Log.e("Available images", "size: " + availableImages.size());
        int randomNumber = random.nextInt(availableImages.size());
        targetImageView.setImageResource(Integer.valueOf(availableImages.get(randomNumber)));
        targetImage = Integer.valueOf(availableImages.get(randomNumber));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra(getString(R.string.game_menu_tag));
            if (result != null) {

                if(requestCode == GAME_MENU_REQUEST){

                    if(result.equals(getString(R.string.game_menu_tag_resume))){

                        int randomNumber = random.nextInt(availableImages.size());
                        targetImageView.setImageResource(Integer.valueOf(availableImages.get(randomNumber)));
                        targetImage = Integer.valueOf(availableImages.get(randomNumber));

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
