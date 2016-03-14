package com.softwarenation.superpatient;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.softwarenation.superpatient.fragments.FollowSuperPatientFragment;
import com.softwarenation.superpatient.fragments.SuperColorBlockFragment;
import com.softwarenation.superpatient.fragments.SuperDigitsIntroFragment;
import com.softwarenation.superpatient.fragments.SuperMashUpIntroFragment;
import com.softwarenation.superpatient.fragments.SuperSpeedFragment;


public class GamesIntroActivity extends Activity {

    private ActionBar actionBar;

    private SuperDigitsIntroFragment superDigitsIntroFragment;

    private Fragment fragmentIntro;

    private ImageView backButton;
    private TextView title;

    private Button startGameButton;

    private String gameForIntro;

    private int fromGameMenu = 0;

    private Class gameToStart;

    final private int GAME_MENU_HOW_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_intro);

        backButton = (ImageView) findViewById(R.id.back_button_games_intro);
        title = (TextView) findViewById(R.id.games_intro_title);

        startGameButton = (Button) findViewById(R.id.games_intro_button_start);

        Intent intent = getIntent();
        if(intent != null){
            gameForIntro = intent.getStringExtra(getString(R.string.main_game_key));
            fromGameMenu = intent.getIntExtra(getString(R.string.main_game_from_menu_key),0);
        }


        if(gameForIntro != null&&fromGameMenu==0){
            if(gameForIntro.equals(getString(R.string.main_game_super_digits))){
                fragmentIntro = new SuperDigitsIntroFragment();
                title.setText(getString(R.string.super_digits_title));
                gameToStart = SuperDigitsActivity.class;
            } else if(gameForIntro.equals(getString(R.string.main_game_super_mash_up))){
                fragmentIntro = new SuperMashUpIntroFragment();
                title.setText("Super Mash Up");
                gameToStart = SuperDigitsActivity.class;
            }else if(gameForIntro.equals(getString(R.string.main_game_super_color_block))){
                fragmentIntro = new SuperColorBlockFragment();
                title.setText("Super Color Block");
                gameToStart = SuperColorBlockActivity.class;
            }else if(gameForIntro.equals(getString(R.string.main_game_super_speed))){
                fragmentIntro = new SuperSpeedFragment();
                title.setText("Super Speed");
                gameToStart = SuperSpeedActivity.class;
            }else if(gameForIntro.equals(getString(R.string.main_game_follow_super_patient))){
                fragmentIntro = new FollowSuperPatientFragment();
                title.setText("Follow Super Patient");
                gameToStart = FollowSuperPatientActivity.class;
            }
            setFragment(fragmentIntro);
        } else if(gameForIntro != null&&fromGameMenu==1){
            if(gameForIntro.equals(getString(R.string.main_game_super_digits))){
                fragmentIntro = new SuperDigitsIntroFragment();

            } else if(gameForIntro.equals(getString(R.string.main_game_super_mash_up))){
                fragmentIntro = new SuperMashUpIntroFragment();
            }else if(gameForIntro.equals(getString(R.string.main_game_super_color_block))){
                fragmentIntro = new SuperColorBlockFragment();
            }else if(gameForIntro.equals(getString(R.string.main_game_super_speed))){
                fragmentIntro = new SuperSpeedFragment();
            }else if(gameForIntro.equals(getString(R.string.main_game_follow_super_patient))){
                fragmentIntro = new FollowSuperPatientFragment();
            }
            title.setText("Menu");
            startGameButton.setVisibility(View.GONE);
            setFragment(fragmentIntro);
        }

        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameToStart != null) {
                    Intent intent = new Intent(GamesIntroActivity.this, gameToStart);

                    if(gameForIntro.equals(getString(R.string.main_game_super_mash_up)))
                        intent.putExtra(getString(R.string.main_game_key),getString(R.string.main_game_super_mash_up));
                    startActivity(intent);
                    finish();
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fromGameMenu == 0) {
                    Intent intent = new Intent(GamesIntroActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if(gameForIntro != null&&fromGameMenu == 1){
                    Intent intent1 = new Intent(GamesIntroActivity.this, GameMenu.class);
                    //maybe dont need it
                    intent1.putExtra(getString(R.string.game_menu_game_key), gameForIntro);
                    setResult(RESULT_OK,intent1);
                    finish();
                }
            }
        });


//        actionBar = getSupportActionBar();
//        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFFFF")));
//        actionBar.setHomeAsUpIndicator(R.drawable.back_arrow);
//        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void setFragment(Fragment fragment){
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.games_intro_frame,fragment);
        transaction.commit();
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        if(fromGameMenu == 0) {
            Intent intent = new Intent(GamesIntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if(gameForIntro != null&&fromGameMenu == 1){
            Intent intent1 = new Intent(GamesIntroActivity.this, GameMenu.class);
            intent1.putExtra(getString(R.string.game_menu_game_key), gameForIntro);
            setResult(RESULT_OK,intent1);
            finish();
        }
    }


}
