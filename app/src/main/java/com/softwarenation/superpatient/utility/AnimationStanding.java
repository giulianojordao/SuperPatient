package com.softwarenation.superpatient.utility;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.softwarenation.superpatient.R;

/**
 * Created by software-academyios on 5/18/15.
 */
public class AnimationStanding extends ImageView{

    public AnimationStanding(Context context) {
        super(context);
        init();
    }

    public AnimationStanding(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public AnimationStanding(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundResource(R.drawable.animation_standing);
        final AnimationDrawable frameAnimation = (AnimationDrawable) getBackground();
        post(new Runnable(){
            public void run(){
                frameAnimation.start();
            }
        });
    }

}
