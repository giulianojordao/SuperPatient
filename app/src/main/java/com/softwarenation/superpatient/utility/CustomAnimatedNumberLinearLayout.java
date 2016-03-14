package com.softwarenation.superpatient.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.softwarenation.superpatient.R;
import com.softwarenation.superpatient.SuperDigitsActivity;

import java.util.zip.Inflater;

/**
 * Created by software-academyios on 6/3/15.
 */
public class CustomAnimatedNumberLinearLayout extends LinearLayout {

    public LinearLayout superDigitsNumbers;
    public static String animationString = "animationNumber";
    private Context context;

    public CustomAnimatedNumberLinearLayout(Context context, LinearLayout superDigitsNumbers) {
        super(context);

        this.superDigitsNumbers = superDigitsNumbers;
        this.context = context;


    }

    public CustomAnimatedNumberLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void onAnimationEnd() {
        super.onAnimationEnd();
//        Log.e("Entered in AnimationEnd", "animation string: " + animationString);
//        Log.e("Entered in AnimationEnd","animation id: "+this.getId());
//        Log.e("SuperDigitsCountLayout","SuperDigitsCountLayout is: "+SuperDigitsActivity.countNumbersLayout);
        if(SuperDigitsActivity.animationString.equals(context.getString(R.string.animation_string_number))) {
            Log.d("textViewId AnQues","textView Id: "+SuperDigitsActivity.textViewNumberId);
//            GlobalData.getInstance().animateQuestionMarkDown(this, superDigitsNumbers);
            if(SuperDigitsActivity.firstLayout){
                GlobalData.getInstance().animateQuestionMarkDown((CustomAnimatedNumberLinearLayout) superDigitsNumbers.getChildAt(0), superDigitsNumbers);
                SuperDigitsActivity.firstLayout=false;
                SuperDigitsActivity.animationString =context.getString(R.string.animation_string_question);
            }else{

            GlobalData.getInstance().animateQuestionMarkDown((CustomAnimatedNumberLinearLayout)superDigitsNumbers.getChildAt(SuperDigitsActivity.countNumbersLayout++), superDigitsNumbers);
            SuperDigitsActivity.animationString =context.getString(R.string.animation_string_question);

            }

        } else if(SuperDigitsActivity.animationString.equals(context.getString(R.string.animation_string_question))){
//            if(SuperDigitsActivity.countNumbers!=0){
//                animationString = context.getString(R.string.animation_string_number);
//                SuperDigitsActivity.countNumbers--;
//            GlobalData.getInstance().setNewLayout(context,superDigitsNumbers);
//
//            }
            if(SuperDigitsActivity.countNumbers ==0){
                SuperDigitsActivity.canUseKeyboard=true;
                SuperDigitsActivity.enableButtonsStatic();
            }
            Log.d("textViewId AnNum","textView Id: "+SuperDigitsActivity.textViewNumberId);
        if(SuperDigitsActivity.countNumbers>0){

                SuperDigitsActivity.animationString = context.getString(R.string.animation_string_number);
                SuperDigitsActivity.countNumbers--;
                GlobalData.getInstance().animateNumberDown((CustomAnimatedNumberLinearLayout) superDigitsNumbers.getChildAt(SuperDigitsActivity.countNumbersLayout), superDigitsNumbers);

        }


        }
//        else if(SuperDigitsActivity.animationString.equals(getContext().getString(R.string.animation_up))){
//
//        }
    }


}
