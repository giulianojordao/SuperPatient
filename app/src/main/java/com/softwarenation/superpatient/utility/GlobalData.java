package com.softwarenation.superpatient.utility;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softwarenation.superpatient.GamesIntroActivity;
import com.softwarenation.superpatient.MainActivity;
import com.softwarenation.superpatient.R;
import com.softwarenation.superpatient.SuperDigitsActivity;

import java.util.Random;

/**
 * Created by software-academyios on 5/29/15.
 */
public class GlobalData{

    private Random random = new Random();
    private int textViewHeight;

    private static GlobalData instance = null;
    protected GlobalData() {
        // Exists only to defeat instantiation.
    }
    public static GlobalData getInstance() {
        if(instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public boolean isHdpi(Activity activity){
        float density = activity.getResources().getDisplayMetrics().density;

        return density==1.5;
    }

    public Double getSize(Activity activity){
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        int dens=dm.densityDpi;
        double wi=(double)width/(double)dens;
        double hi=(double)height/(double)dens;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);
        return screenInches;
    }


    public void animateNumberDown(final LinearLayout layout, LinearLayout superDigitsNumbers){
//        Log.e("animationNumber height", SuperDigitsActivity.textViewHeight / 2 + "");
//        Log.e("animationNumber LHeight", layout.getMeasuredHeight() + "");
//        Log.e("animNumber new LHeight",(superDigitsNumbers.getMeasuredHeight()+2*SuperDigitsActivity.textViewHeight)+"");
//        Log.e("animationNumber Top",layout.getTop()+"");

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,
                TranslateAnimation.ABSOLUTE, 0, Animation.ABSOLUTE, -(superDigitsNumbers.getMeasuredHeight() + SuperDigitsActivity.textViewHeight),
                TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - (-(superDigitsNumbers.getMeasuredHeight()+2*SuperDigitsActivity.textViewHeight)) + (((superDigitsNumbers.getMeasuredHeight() / 2)) - ((superDigitsNumbers.getMeasuredHeight()+2*SuperDigitsActivity.textViewHeight) - (SuperDigitsActivity.textViewHeight / 2))));
//        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,
//                TranslateAnimation.ABSOLUTE, 0, 0, 0,
//                TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - layout.getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - (layout.getMeasuredHeight() - (50))));
        translateAnimation.setDuration(500);
        translateAnimation.setRepeatCount(0);
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setFillAfter(true);

        layout.startAnimation(translateAnimation);

    }


    public void animateQuestionMarkDown(LinearLayout layout, LinearLayout superDigitsNumbers){
//        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,
//                TranslateAnimation.ABSOLUTE, 0, 0, 0,
//                TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - layout.getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - layout.getChildAt(0).getMeasuredHeight() / 2));
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,
                TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - layout.getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - ((superDigitsNumbers.getMeasuredHeight()+2*SuperDigitsActivity.textViewHeight) - (SuperDigitsActivity.textViewHeight/ 2))),
                TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - layout.getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - SuperDigitsActivity.textViewHeight / 2));
        translateAnimation.setDuration(400);
        translateAnimation.setRepeatCount(0);
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setFillAfter(true);
        translateAnimation.setStartOffset(100);
        layout.startAnimation(translateAnimation);

//        Log.e("animationQuest height", layout.getChildAt(0).getMeasuredHeight() / 2 + "");
    }

    public void animateNumberUp(LinearLayout layout, LinearLayout superDigitsNumbers, String newSymbol){

        TextView numberTextView = (TextView) layout.getChildAt(1);
        numberTextView.setText(newSymbol);

        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0,
                TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - layout.getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - SuperDigitsActivity.textViewHeight / 2),
                TranslateAnimation.ABSOLUTE, TranslateAnimation.ABSOLUTE - layout.getTop() + (((superDigitsNumbers.getMeasuredHeight() / 2)) - ((superDigitsNumbers.getMeasuredHeight()+2*SuperDigitsActivity.textViewHeight) - (SuperDigitsActivity.textViewHeight/ 2))));
        translateAnimation.setDuration(300);
        translateAnimation.setRepeatCount(0);
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setFillAfter(true);
        translateAnimation.setStartOffset(100);
        layout.startAnimation(translateAnimation);
    }

    public void showGameOverDialog(final Activity activity, final String gameForIntro, int points){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_call);
        dialog.setCancelable(false);

        TextView titleTextView = (TextView) dialog.findViewById(R.id.custom_dialog_title);
        TextView textTextView = (TextView) dialog.findViewById(R.id.custom_dialog_text);

        Button dialogTryAgainButton = (Button) dialog.findViewById(R.id.custom_dialog_call_ButtonCall);
        Button dialogMainMenuButton = (Button) dialog.findViewById(R.id.custom_dialog_call_ButtonCancel);

        dialogTryAgainButton.setBackgroundResource(R.drawable.button_ok_shape);
        dialogMainMenuButton.setBackgroundResource(R.drawable.button_ok_shape);


        titleTextView.setText("Great Game!");
        textTextView.setText("Points: " + points + " pts" + "\nTry again to practice and continue improving, or try another game.");
        dialogTryAgainButton.setText("Try Again");
        dialogMainMenuButton.setText("Return to main menu");

        // if button is clicked, close the custom dialog
        dialogTryAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, GamesIntroActivity.class);
                intent.putExtra(activity.getString(R.string.main_game_key),gameForIntro);
                activity.startActivity(intent);
                dialog.dismiss();
                activity.finish();
            }
        });

        dialogMainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);

                dialog.dismiss();
                activity.finish();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_shape);
        dialog.show();
    }


}

