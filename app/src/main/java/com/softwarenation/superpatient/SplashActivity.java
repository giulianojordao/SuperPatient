package com.softwarenation.superpatient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Field;


public class SplashActivity extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 1500;

    private SharedPreferences sharedPreferences;
    private Boolean logInEnteredPrefs;

    private TextView androidVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        androidVersion = (TextView) findViewById(R.id.splash_android_version);

        androidVersion.setText(Build.VERSION.RELEASE);

        setDefaultFont();
        sharedPreferences = this.getSharedPreferences(getString(R.string.preferences_key),MODE_PRIVATE);
        logInEnteredPrefs = sharedPreferences.getBoolean(getString(R.string.preferences_log_in_entered),false);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                Intent loginIntent = new Intent(SplashActivity.this, LoginActivity.class);
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);

                if(logInEnteredPrefs)
                startActivity(mainIntent);
                else startActivity(loginIntent);

                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    private void setDefaultFont() {

        try {
            final Typeface bold = Typeface.createFromAsset(getAssets(), "fonts/helveticaNeueRomanBold.ttf");
            final Typeface italic = Typeface.createFromAsset(getAssets(), "fonts/helveticaNeueRomanItalic.ttf");
            final Typeface thin = Typeface.createFromAsset(getAssets(), "fonts/helveticaNeueThin.ttf");
            final Typeface thinItalic = Typeface.createFromAsset(getAssets(), "fonts/helveticaNeueThinItalic.ttf");
            final Typeface boldItalic = Typeface.createFromAsset(getAssets(), "fonts/helveticaNeueRomanBoldItalic.ttf");
            final Typeface regular = Typeface.createFromAsset(getAssets(),"fonts/helveticaNeueRomanRegular.ttf");

            Field DEFAULT = Typeface.class.getDeclaredField("DEFAULT");
            DEFAULT.setAccessible(true);
            DEFAULT.set(null, regular);

            Field DEFAULT_BOLD = Typeface.class.getDeclaredField("DEFAULT_BOLD");
            DEFAULT_BOLD.setAccessible(true);
            DEFAULT_BOLD.set(null, bold);

            //Thin...SERIF because that exists
            Field SERIF = Typeface.class.getDeclaredField("SERIF");
            SERIF.setAccessible(true);
            SERIF.set(null, thin);

            Field sDefaults = Typeface.class.getDeclaredField("sDefaults");
            sDefaults.setAccessible(true);
            sDefaults.set(null, new Typeface[]{
                    regular, bold, italic, boldItalic, thin, thinItalic
            });

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable e) {
            //cannot crash app if there is a failure with overriding the default font!
            e.printStackTrace();
        }
    }



}
