package com.softwarenation.superpatient;

import android.app.Activity;
import android.app.Dialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.softwarenation.superpatient.utility.CustomAnimatedNumberLinearLayout;
import com.softwarenation.superpatient.utility.CustomEditText;
import com.softwarenation.superpatient.utility.GlobalData;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class SuperDigitsActivity extends Activity {

    private ImageView menuButton;

    private LinearLayout superDigitsNumbers;
    private LinearLayout number0Layout;

    public  ImageView number0;
    private ImageView number1;
    private ImageView number2;
    private ImageView number3;
    private ImageView number4;
    private ImageView number5;
    private ImageView number6;
    private ImageView number7;
    private ImageView number8;
    private ImageView number9;

    private TextView invisibleTextView;
    private CustomEditText invisibleEditText;

    private RelativeLayout rootView;

    private TextView levelNumber;

    private TextView superDigitsPoints;

    private Typeface thinTypeface;


    LinearLayout textLayout;
    TextView textViewNumber;
    TextView textViewQuestion;
    private int heightTextView;


    private LinearLayout linearLayoutRunnable;

    private List<LinearLayout> listLayouts = new ArrayList<>();

    private LinearLayout layoutToAnimate;

    public static int textViewNumberId=0;

    private int childPosition = 0;

    private Random random;

    public static int countNumbers;
    public int countNumbersAnimateUp;

    public static int countNumbersLayout;
    public static int textViewHeight = 0;
    public static boolean firstLayout = false;

    public static String animationString = "animationNumber";

    private boolean shouldSetNewLayout;
    private int correctCount = 0;
    private int failCount = 0;

    private int points = 0;

    private ArrayList<String> correctSymbols = new ArrayList<>();
    private ArrayList<String> enteredSymbols = new ArrayList<>();

    final Handler handler_interact=new Handler();

    private String game;

    final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    final int N = alphabet.length();

    public TranslateAnimation translateAnimation1;

    final private int GAME_MENU_REQUEST = 1;

    private Boolean canSetNew = false;

    private Boolean resetBoolean = false;

    public static Boolean canUseKeyboard = false;

    public static Activity activity;

    private Vibrator vibrator;

//    public static boolean canUseKeyboard = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_digits);

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

        activity = this;

        thinTypeface = Typeface.createFromAsset(getAssets(),"fonts/helveticaNeueThin.ttf");

        menuButton = (ImageView) findViewById(R.id.super_digits_menu_button);
//        menuButton.setEnabled(true);

        superDigitsNumbers = (LinearLayout) findViewById(R.id.super_digits_numbers_layout);

//        number0Layout = (LinearLayout) findViewById(R.id.super_digits_number_0_layout);

        number0 = (ImageView) findViewById(R.id.super_digits_button_0);
        number1 = (ImageView) findViewById(R.id.super_digits_button_1);
        number2 = (ImageView) findViewById(R.id.super_digits_button_2);
        number3 = (ImageView) findViewById(R.id.super_digits_button_3);
        number4 = (ImageView) findViewById(R.id.super_digits_button_4);
        number5 = (ImageView) findViewById(R.id.super_digits_button_5);
        number6 = (ImageView) findViewById(R.id.super_digits_button_6);
        number7 = (ImageView) findViewById(R.id.super_digits_button_7);
        number8 = (ImageView) findViewById(R.id.super_digits_button_8);
        number9 = (ImageView) findViewById(R.id.super_digits_button_9);

        invisibleTextView = (TextView) findViewById(R.id.super_digits_invisible_textview);
        invisibleTextView.setTypeface(thinTypeface);

        rootView = (RelativeLayout) findViewById(R.id.super_digits_root_view);

//        invisibleEditText = (CustomEditText) findViewById(R.id.super_digits_invisible_edittext);

        random = new Random();

        levelNumber = (TextView) findViewById(R.id.super_digits_level_number);

        superDigitsPoints = (TextView) findViewById(R.id.super_digits_points);

        shouldSetNewLayout = true;


        Intent intent = getIntent();
        if(intent != null){
            game = intent.getStringExtra(getString(R.string.main_game_key));
        }

        if(game!=null){
            if(game.equals(getString(R.string.main_game_super_mash_up))){

                invisibleEditText = new CustomEditText(SuperDigitsActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                params.topMargin = -300;
                invisibleEditText.setLayoutParams(params);
                rootView.addView(invisibleEditText);
                RelativeLayout.LayoutParams paramsNew = (RelativeLayout.LayoutParams) invisibleEditText.getLayoutParams();
//                paramsNew.setMargins(0,-300,0,0);
                paramsNew.addRule(RelativeLayout.BELOW,R.id.super_digits_main_layout);
                invisibleEditText.requestLayout();

                invisibleEditText.setFocusable(true);
                invisibleEditText.setFocusableInTouchMode(true);
                invisibleEditText.requestFocus();
                openKeypad(SuperDigitsActivity.this, invisibleEditText);

                TextView activityTitle = (TextView) findViewById(R.id.super_digits_title);
                activityTitle.setText("Super Mash Up");
                LinearLayout buttonsLayout = (LinearLayout) findViewById(R.id.super_digits_button_layout);
                buttonsLayout.setVisibility(View.GONE);



                menuButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SuperDigitsActivity.this,GameMenu.class);
                        intent.putExtra(getString(R.string.game_menu_game_key),getString(R.string.main_game_super_mash_up));
                        startActivityForResult(intent, GAME_MENU_REQUEST);
                    }
                });
//                LinearLayout mainLayout = (LinearLayout) findViewById(R.id.super_digits_main_layout);
//                mainLayout.requestFocus();
//                invisibleTextView.requestFocus();
//                openKeypad(SuperDigitsActivity.this, invisibleTextView);

            }
        } else {
            menuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
            Intent intent1 = new Intent(SuperDigitsActivity.this,GameMenu.class);
            intent1.putExtra(getString(R.string.game_menu_game_key),getString(R.string.main_game_super_digits));
            startActivityForResult(intent1,GAME_MENU_REQUEST);
                }
            });
        }


//
//        translateAnimation1 = new TranslateAnimation(0, 0,
//                TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - superDigitsNumbers.getChildAt(0).getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - SuperDigitsActivity.textViewHeight / 2),
//                TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - superDigitsNumbers.getChildAt(0).getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - ((superDigitsNumbers.getMeasuredHeight() + 2 * SuperDigitsActivity.textViewHeight) - (SuperDigitsActivity.textViewHeight / 2))));
//        translateAnimation1.setDuration(300);
//        translateAnimation1.setRepeatCount(0);
//        translateAnimation1.setInterpolator(new LinearInterpolator());
//        translateAnimation1.setFillAfter(true);


        ImageView[] buttons = {number0,number1,number2,number3,number4,number5,number6,number7,number8,number9};
        int numberButton = 0;
        for(ImageView button : buttons){
            setButtonClickListener(button,numberButton++);
        }



        countNumbers =3;
        countNumbersAnimateUp=countNumbers;
        countNumbersLayout=1;

//        setNewLayout();


        if(invisibleEditText!=null) {



            invisibleEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                int flag = 0;
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

if(resetBoolean==false) {

    if ((childPosition < countNumbersAnimateUp)&&canUseKeyboard) {

        final char character = s.charAt(childPosition);
        enteredSymbols.add(String.valueOf(character).toUpperCase());

        GlobalData.getInstance().animateNumberUp((CustomAnimatedNumberLinearLayout) superDigitsNumbers.getChildAt(childPosition), superDigitsNumbers, String.valueOf(character).toUpperCase());

        if (childPosition++ == countNumbersAnimateUp - 1) {
            checkSymbols();
        }
    }else if(childPosition == 0 && flag == 0 && canUseKeyboard == false){
        flag =1;
        invisibleEditText.setText("");
        flag = 0;
    }
}
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(intent != null){
            game = intent.getStringExtra(getString(R.string.main_game_key));
        }

        openKeypad(SuperDigitsActivity.this,invisibleEditText);
        if(canSetNew) {
            canSetNew =false;
            if (game != null) {
                if (game.equals(getString(R.string.main_game_super_mash_up))) {

//                    canSetNew = false;
                    TextView activityTitle = (TextView) findViewById(R.id.super_digits_title);
                    activityTitle.setText("Super Mash Up");
                    LinearLayout buttonsLayout = (LinearLayout) findViewById(R.id.super_digits_button_layout);
                    buttonsLayout.setVisibility(View.GONE);
                }
            }

            int childCount = superDigitsNumbers.getChildCount();
            for (int i = 0; i < childCount; i++) {
                LinearLayout layout = (LinearLayout) superDigitsNumbers.getChildAt(i);
                layout.clearAnimation();
            }
            superDigitsNumbers.removeAllViewsInLayout();
            waitALittle();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        canSetNew = true;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);


//        Log.e("Height in Window",textViewHeight+"");

        if(shouldSetNewLayout) {

            textViewHeight = invisibleTextView.getMeasuredHeight();

//            CustomAnimatedNumberLinearLayout.animationString=SuperDigitsActivity.this.getString(R.string.animation_string_number);
            animationString=SuperDigitsActivity.this.getString(R.string.animation_string_number);
            setNewLayout();

            shouldSetNewLayout=false;

        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SuperDigitsActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void checkSymbols(){
        Boolean same = true;
        for(int i = 0;i<correctSymbols.size();i++){

            if(!correctSymbols.get(i).equals(enteredSymbols.get(i))){
                same=false;
            }
        }
        Log.e("Arrays same", "They are equal: " + same);
        if(same){
            superDigitsNumbers.setBackgroundResource(R.drawable.super_digits_shape_green);
            correctCount++;
            points += 10;

        }else {
            superDigitsNumbers.setBackgroundResource(R.drawable.super_digits_shape_red);
            failCount++;
        }

        superDigitsPoints.setText(String.valueOf(points));

//        resetSymbols();
        resetBoolean = true;

        waitALittle();


    }

    private void waitALittle(){

        //creating timer
        Timer timer_interact=new Timer();
        timer_interact.schedule(new TimerTask() {
            @Override
            public void run() {
                UpdateSymbols();
            }
        }, 600);
    }
    private void UpdateSymbols() {
        handler_interact.post(runnable_interact);
    }
    //creating runnable
    final Runnable runnable_interact = new Runnable() {
        public void run() {
            resetSymbols();
        }
    };


    private void enableButtons(){
        // calculate the proximate duration of the animations
        int duration = (countNumbers+1)*1256;

        //creating timer
        Timer timer_interact=new Timer();
        timer_interact.schedule(new TimerTask() {
            @Override
            public void run() {enableButtonsPost();}
        }, duration);
    }
    private void enableButtonsPost() {
        handler_interact.post(runnable_enable_buttons);
//        enableButtonsVoid(true);
    }
    //creating runnable
    final Runnable runnable_enable_buttons = new Runnable() {
        public void run() {
            enableButtonsVoid(true);
        }
    };


    private void resetSymbols(){
        if(superDigitsNumbers.getChildCount() > 0)
            superDigitsNumbers.removeAllViews();

        animationString=SuperDigitsActivity.this.getString(R.string.animation_string_number);
        countNumbersLayout=1;
        textViewNumberId=0;
        correctSymbols.clear();
        enteredSymbols.clear();
        childPosition = 0;

        if(game!=null && invisibleEditText !=null) {
            resetBoolean =true;
            invisibleEditText.setText("");
            resetBoolean=false;
        }

        Log.e("Entered in resetSymbols", "true");

        switch (correctCount){
            case 0:
            case 1:
                levelNumber.setText(String.valueOf(1));
                countNumbers=3;
                countNumbersAnimateUp=countNumbers;

                setNewLayout();
                break;
            case 2:case 3:
                levelNumber.setText(String.valueOf(2));
                countNumbers=4;
                countNumbersAnimateUp=countNumbers;
                setNewLayout();
                break;
            case 4:case 5:
                levelNumber.setText(String.valueOf(3));
                countNumbers=5;
                countNumbersAnimateUp=countNumbers;
                setNewLayout();
                break;
            case 6:case 7:
                levelNumber.setText(String.valueOf(4));
                countNumbers=6;
                countNumbersAnimateUp=countNumbers;
                setNewLayout();
                break;
            case 8:case 9:
                levelNumber.setText(String.valueOf(5));
                countNumbers=7;
                countNumbersAnimateUp=countNumbers;
                setNewLayout();
                break;
            case 10:case 11:
                levelNumber.setText(String.valueOf(6));
                countNumbers=8;
                countNumbersAnimateUp=countNumbers;
                setNewLayout();
                break;
            case 12:case 13:
                levelNumber.setText(String.valueOf(7));
                countNumbers=9;
                countNumbersAnimateUp=countNumbers;
                setNewLayout();
                break;

            default:
                setNewLayout();
                break;


        }

        if(failCount==2)
            showGameOverDialog();

    };

    public void setNewLayout(){

        if(game!=null) {
            if(game.equals(getString(R.string.main_game_super_mash_up))) {
            canUseKeyboard = false;
            }
        }else{
            enableButtonsVoid(false);
        }

        superDigitsNumbers.setBackgroundResource(R.drawable.super_digits_shape);


        for(int i = 0;i<countNumbers;i++) {


            final CustomAnimatedNumberLinearLayout textLayout = new CustomAnimatedNumberLinearLayout(SuperDigitsActivity.this, superDigitsNumbers);
            final TextView textViewNumber = new TextView(SuperDigitsActivity.this);
            final TextView textViewQuestion = new TextView(SuperDigitsActivity.this);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams layoutParamsLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams layoutParamsNumber = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            layoutParamsNumber.setMargins(0, superDigitsNumbers.getMeasuredHeight(), 0, 0);

            if(i==countNumbers-1){
                layoutParamsLayout.setMargins(0, -(superDigitsNumbers.getMeasuredHeight() + textViewHeight), 0, 0);
            }else{
                layoutParamsLayout.setMargins(0, -(superDigitsNumbers.getMeasuredHeight() + textViewHeight), 32, 0);
            }



//        layoutParamsLayout.setMargins(0,-(superDigitsNumbers.getMeasuredHeight() + textViewNumber.getMeasuredHeight()),0,0);

            textLayout.setOrientation(LinearLayout.VERTICAL);
            textLayout.setGravity(Gravity.CENTER_HORIZONTAL);
//            layoutParamsLayout.gravity = Gravity.CENTER_HORIZONTAL;

            textLayout.setLayoutParams(layoutParamsLayout);

            textViewQuestion.setLayoutParams(layoutParams);


            textViewNumber.setLayoutParams(layoutParamsNumber);

            textViewNumber.setTextAppearance(SuperDigitsActivity.this, R.style.superDigitsTextView);
            textViewQuestion.setTextAppearance(SuperDigitsActivity.this, R.style.superDigitsTextView);

            textViewNumber.setTypeface(thinTypeface);
            textViewQuestion.setTypeface(thinTypeface);

            textViewNumber.setId(SuperDigitsActivity.textViewNumberId++);
            textViewQuestion.setId(SuperDigitsActivity.textViewNumberId + 10);
            textLayout.setId(SuperDigitsActivity.textViewNumberId + 20);

            textLayout.addView(textViewQuestion);
            textLayout.addView(textViewNumber);


            textViewQuestion.setText("?");
            if(game!=null){
                if(game.equals(getString(R.string.main_game_super_mash_up))){

                    invisibleTextView.setText("W");
                    handler_interact.post(new Runnable() {
                        @Override
                        public void run() {
                            int width = invisibleTextView.getMeasuredWidth();
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) textLayout.getLayoutParams();
                            if (width != 0) {
                                params.width = width;
                                textLayout.requestLayout();
                            }
                        }
                    });



                    textViewNumber.setText(alphabet.charAt(random.nextInt(N))+"");
                }
            }else {
                textViewNumber.setText(String.valueOf(random.nextInt(10)));
            }

            correctSymbols.add(textViewNumber.getText().toString());


            superDigitsNumbers.addView(textLayout);



        }

        childPosition = 0;
        textViewNumberId = 0;
        countNumbers--;
        firstLayout = true;

        Log.d("checking everything", "countNumbers: " + countNumbers + " textViewId: " + textViewNumberId + " correctSymbols: " + correctSymbols.size() + " countNumbersAnimateUp: " + countNumbersAnimateUp);

        Log.e("Numbers areEnabledAct", number0.isEnabled() + "");


        GlobalData.getInstance().animateNumberDown((CustomAnimatedNumberLinearLayout) superDigitsNumbers.getChildAt(0), superDigitsNumbers);
        if(game!=null) {
            if(game.equals(getString(R.string.main_game_super_mash_up))) {

            }
        }else{
//            enableButtons();
        }
    }

    public void showGameOverDialog(){
        final Dialog dialog = new Dialog(SuperDigitsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_call);
        dialog.setCancelable(false);

        TextView titleTextView = (TextView) dialog.findViewById(R.id.custom_dialog_title);
        TextView textTextView = (TextView) dialog.findViewById(R.id.custom_dialog_text);

        Button dialogTryAgainButton = (Button) dialog.findViewById(R.id.custom_dialog_call_ButtonCall);
        Button dialogMainMenuButton = (Button) dialog.findViewById(R.id.custom_dialog_call_ButtonCancel);


        titleTextView.setText("Great Game!");
        textTextView.setText("Points: " + points + " pts" + "\nTry again to practice and continue improving, or try another game.");
        dialogTryAgainButton.setText("Try Again");
        dialogMainMenuButton.setText("Return to main menu");

        // if button is clicked, close the custom dialog
        dialogTryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuperDigitsActivity.this, GamesIntroActivity.class);
                if (game != null) {
                    intent.putExtra(SuperDigitsActivity.this.getString(R.string.main_game_key), getString(R.string.main_game_super_mash_up));
                } else {
                    intent.putExtra(SuperDigitsActivity.this.getString(R.string.main_game_key), getString(R.string.main_game_super_digits));
                }
                startActivity(intent);
                dialog.dismiss();
                finish();
            }
        });

        dialogMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuperDigitsActivity.this, MainActivity.class);
                startActivity(intent);

                dialog.dismiss();
                finish();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_shape);
        dialog.show();
    }

    public static void openKeypad(final Context context, final View v) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                Log.e("openKeypad", "Inside Handler");
            }
        }, 150);}

    private void openKeypadTimer(final Context context, final View v){
        int duration = 5000;

        //creating timer
        Timer timer_interact=new Timer();
        timer_interact.schedule(new TimerTask() {
            @Override
            public void run() {

                v.requestFocus();

                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(v, InputMethodManager.SHOW_IMPLICIT);
                Log.e("openKeypadTimer", "Inside Handler");


            }
        }, duration);
    }


    public void enableButtonsVoid(Boolean enable){

        Log.e("Entered in enButtons", "Enable: "+enable);

        number0.setEnabled(enable);
        number1.setEnabled(enable);
        number2.setEnabled(enable);
        number3.setEnabled(enable);
        number4.setEnabled(enable);
        number5.setEnabled(enable);
        number6.setEnabled(enable);
        number7.setEnabled(enable);
        number8.setEnabled(enable);
        number9.setEnabled(enable);
    }

    public static void enableButtonsStatic(){
        final ImageView b0 = (ImageView) activity.findViewById(R.id.super_digits_button_0);
        final ImageView b1 = (ImageView) activity.findViewById(R.id.super_digits_button_1);
        final ImageView b2 = (ImageView) activity.findViewById(R.id.super_digits_button_2);
        final ImageView b3 = (ImageView) activity.findViewById(R.id.super_digits_button_3);
        final ImageView b4 = (ImageView) activity.findViewById(R.id.super_digits_button_4);
        final ImageView b5 = (ImageView) activity.findViewById(R.id.super_digits_button_5);
        final ImageView b6 = (ImageView) activity.findViewById(R.id.super_digits_button_6);
        final ImageView b7 = (ImageView) activity.findViewById(R.id.super_digits_button_7);
        final ImageView b8 = (ImageView) activity.findViewById(R.id.super_digits_button_8);
        final ImageView b9 = (ImageView) activity.findViewById(R.id.super_digits_button_9);

        if(b0.isEnabled()==false) {
            b0.setEnabled(true);
            b1.setEnabled(true);
            b2.setEnabled(true);
            b3.setEnabled(true);
            b4.setEnabled(true);
            b5.setEnabled(true);
            b6.setEnabled(true);
            b7.setEnabled(true);
            b8.setEnabled(true);
            b9.setEnabled(true);
        }
    }

    public void setButtonClickListener(ImageView button, final int numberValue){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrator.vibrate(30);
                if (childPosition < countNumbersAnimateUp) {
                    enteredSymbols.add(String.valueOf(numberValue));

//                    CustomAnimatedNumberLinearLayout.animationString=SuperDigitsActivity.this.getString(R.string.animation_up);
                    GlobalData.getInstance().animateNumberUp((CustomAnimatedNumberLinearLayout) superDigitsNumbers.getChildAt(childPosition), superDigitsNumbers, String.valueOf(numberValue));
                    if (childPosition++ == countNumbersAnimateUp - 1) {
                        checkSymbols();
                    }
                }
            }
        });

    }


    public void animateNumberUp(final LinearLayout layout, final LinearLayout superDigitsNumbers, final String newSymbol){

        TextView numberTextView = (TextView) layout.getChildAt(1);
        numberTextView.setText(newSymbol);

                TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,
                        TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - layout.getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - SuperDigitsActivity.textViewHeight / 2),
                        TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - layout.getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - ((superDigitsNumbers.getMeasuredHeight() + 2 * SuperDigitsActivity.textViewHeight) - (SuperDigitsActivity.textViewHeight / 2))));
                translateAnimation.setDuration(300);
                translateAnimation.setRepeatCount(0);
                translateAnimation.setInterpolator(new LinearInterpolator());
                translateAnimation.setFillAfter(true);
//        translateAnimation.setStartOffset(100);
                layout.startAnimation(translateAnimation);


    }

    private void animateUpNew(){
        // calculate the proximate duration of the animations
        int duration = 100;

        //creating timer
        Timer timer_interact=new Timer();
        timer_interact.schedule(new TimerTask() {
            @Override
            public void run() {enableButtonsPost();}
        }, duration);
    }
    private void animateUpPost() {
        handler_interact.post(runnable_animate_up);
//        enableButtonsVoid(true);
    }
    //creating runnable
    final Runnable runnable_animate_up = new Runnable() {
        public void run() {
            animateNumberUp((CustomAnimatedNumberLinearLayout) superDigitsNumbers.getChildAt(0), superDigitsNumbers, String.valueOf("A").toUpperCase());
        }
    };


    public void createAnimation(){

        translateAnimation1 = new TranslateAnimation(0, 0,
                TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - superDigitsNumbers.getChildAt(childPosition).getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - SuperDigitsActivity.textViewHeight / 2),
                TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - superDigitsNumbers.getChildAt(childPosition).getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - ((superDigitsNumbers.getMeasuredHeight() + 2 * SuperDigitsActivity.textViewHeight) - (SuperDigitsActivity.textViewHeight / 2))));
        translateAnimation1.setDuration(300);
        translateAnimation1.setRepeatCount(0);
        translateAnimation1.setInterpolator(new LinearInterpolator());
        translateAnimation1.setFillAfter(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            String result = data.getStringExtra(getString(R.string.game_menu_tag));
            if (result != null) {

                Log.e("result",result);

                if(requestCode == GAME_MENU_REQUEST){

                    if(result.equals(getString(R.string.game_menu_tag_resume))){

                        if(game!=null){
                            if(game.equals(getString(R.string.main_game_super_mash_up))){
                                canSetNew = false;
                                TextView activityTitle = (TextView) findViewById(R.id.super_digits_title);
                                activityTitle.setText("Super Mash Up");
                                LinearLayout buttonsLayout = (LinearLayout) findViewById(R.id.super_digits_button_layout);
                                buttonsLayout.setVisibility(View.GONE);
                            }
                        }

                        int childCount = superDigitsNumbers.getChildCount();
                        for(int i = 0;i<childCount;i++){
                            LinearLayout layout = (LinearLayout) superDigitsNumbers.getChildAt(i);
                            layout.clearAnimation();
                        }
                        canSetNew=false;
                        superDigitsNumbers.removeAllViewsInLayout();
                        waitALittle();

                    }
                }
            }
        } else if (resultCode == RESULT_CANCELED && data != null){
            String result = data.getStringExtra(getString(R.string.game_menu_tag));
            if (result != null) {

                Log.e("result in Canceled",result);

                if(requestCode == GAME_MENU_REQUEST){
                    if((result.equals(getString(R.string.game_menu_tag_main)))||(result.equals(getString(R.string.game_menu_tag_restart)))){
                        finish();
                    }
                }
            }
        }
    }


}
