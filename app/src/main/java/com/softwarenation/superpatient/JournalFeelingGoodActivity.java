package com.softwarenation.superpatient;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


public class JournalFeelingGoodActivity extends Activity {

    private ImageView backButton;

    private Button submit;

    private LinearLayout happyLayout;
    private LinearLayout calmLayout;
    private LinearLayout accomplishedLayout;
    private LinearLayout hopefulLayout;

    private ImageView happyCorrect;
    private ImageView calmCorrect;
    private ImageView accomplishedCorrect;
    private ImageView hopefulCorrect;

    private SharedPreferences sharedPreferences;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_feeling_good);

        sharedPreferences = this.getSharedPreferences(getString(R.string.preferences_key),MODE_PRIVATE);
        userId = sharedPreferences.getInt(getString(R.string.preferences_user_id), -1);
        Log.e("UserId in journal", String.valueOf(userId));

        submit = (Button) findViewById(R.id.submit_journal_positive_button);

        backButton = (ImageView) findViewById(R.id.back_button_positive_mood);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToJournalActivity.enteredGoToJournal = true;
                Intent intent = new Intent(JournalFeelingGoodActivity.this, SelectMoodActivity.class);
                startActivity(intent);
                finish();
            }
        });

        happyLayout = (LinearLayout) findViewById(R.id.journal_positive_happy);
        calmLayout = (LinearLayout) findViewById(R.id.journal_positive_calm);
        accomplishedLayout = (LinearLayout) findViewById(R.id.journal_positive_accomplished);
        hopefulLayout = (LinearLayout) findViewById(R.id.journal_positive_hopeful);

        happyCorrect = (ImageView) findViewById(R.id.journal_positive_happy_correct);
        calmCorrect = (ImageView) findViewById(R.id.journal_positive_calm_correct);
        accomplishedCorrect = (ImageView) findViewById(R.id.journal_positive_accomplished_correct);
        hopefulCorrect = (ImageView) findViewById(R.id.journal_positive_hopeful_correct);

        happyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeVisibility(happyCorrect);
            }
        });

        calmLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeVisibility(calmCorrect);
            }
        });

        accomplishedLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeVisibility(accomplishedCorrect);
            }
        });

        hopefulLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeVisibility(hopefulCorrect);
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    final SubmitJournalFeelingGood submitJournalFeelingGood = new SubmitJournalFeelingGood(JournalFeelingGoodActivity.this);
                   submitJournalFeelingGood.execute();
                    showPositiveDialog();

                } else {
                    Toast.makeText(JournalFeelingGoodActivity.this, "No network connection", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }

    private void changeVisibility(ImageView imageCorrect){

        if(imageCorrect.getVisibility()!=View.VISIBLE){
            imageCorrect.setVisibility(View.VISIBLE);
        }else{
            imageCorrect.setVisibility(View.INVISIBLE);
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isAvailable() && netInfo.isConnected();
    }



    public void showPositiveDialog(){
        final Dialog dialog = new Dialog(JournalFeelingGoodActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);

        Button dialogButton = (Button) dialog.findViewById(R.id.custom_dialog_ButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JournalFeelingGoodActivity.this,MainActivity.class);
                startActivity(intent);

                dialog.dismiss();
                finish();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_shape);
        dialog.show();
    }


    public class SubmitJournalFeelingGood extends AsyncTask<Void, Void, Void>{

        private Context context;

        public SubmitJournalFeelingGood(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create a new HttpClient and Post Header

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(getString(R.string.apiPatientMoodKey));

            try {
                // Add your data

//                httppost.setEntity(new StringEntity("{\"mood\":[{\"mood_id\":1,\"value\":1}],\"user_id\":17}"));

                JSONObject happyMoodJson = new JSONObject();
                happyMoodJson.put("mood_id","1");
                happyMoodJson.put("value", "1");

                JSONObject calmMoodJson = new JSONObject();
                calmMoodJson.put("mood_id","2");
                calmMoodJson.put("value", "1");

                JSONObject accomplishedMoodJson = new JSONObject();
                accomplishedMoodJson.put("mood_id","3");
                accomplishedMoodJson.put("value", "1");

                JSONObject hopefulMoodJson = new JSONObject();
                hopefulMoodJson.put("mood_id","4");
                hopefulMoodJson.put("value", "1");

                JSONArray moodJsonArray = new JSONArray();

                if(happyCorrect.getVisibility()==View.VISIBLE)
                    moodJsonArray.put(happyMoodJson);

                if(calmCorrect.getVisibility()==View.VISIBLE)
                    moodJsonArray.put(calmMoodJson);

                if(accomplishedCorrect.getVisibility()==View.VISIBLE)
                    moodJsonArray.put(accomplishedMoodJson);

                if(hopefulCorrect.getVisibility()==View.VISIBLE)
                    moodJsonArray.put(hopefulMoodJson);


//                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
//                nameValuePairs.add(new BasicNameValuePair("mood", moodJsonArray.toString()));
//                nameValuePairs.add(new BasicNameValuePair("user_id", String.valueOf(userId)));
//                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//                Log.e("httpPost: ", String.valueOf(new UrlEncodedFormEntity(nameValuePairs).getContent()));
                Log.e("moodJsonArray: ", moodJsonArray.toString());

                JSONObject post = new JSONObject();
                post.put("mood", moodJsonArray);
                post.put("user_id", userId);

                Log.e("post: ", post.toString());

                StringEntity se = new StringEntity(post.toString());
                se.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

                httppost.setEntity(se);


                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);
                String responseBody = EntityUtils.toString(response.getEntity());

                    Log.e("Response pos journal",responseBody);
                    if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_OK){
                        Log.i("Journal positive","successful");
                        Log.e("response journal", responseBody);
                    }else{
                        Log.e("Error journal", "error");
                    }




            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
            } catch (IOException e) {
                // TODO Auto-generated catch block
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(JournalFeelingGoodActivity.this, SelectMoodActivity.class);
        startActivity(intent);
        finish();
    }
}


