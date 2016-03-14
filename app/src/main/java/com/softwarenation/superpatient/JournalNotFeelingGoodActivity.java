package com.softwarenation.superpatient;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;


public class JournalNotFeelingGoodActivity extends Activity {

    private ImageView backButton;

    private Button submit;

    private RelativeLayout nervousLayout;
    private RelativeLayout troubleSleepingLayout;
    private RelativeLayout disconnectedLayout;
    private RelativeLayout sadLayout;
    private RelativeLayout troubleThinkingLayout;
    private RelativeLayout irritableLayout;
    private RelativeLayout hopelessLayout;
    private RelativeLayout harmingLayout;
    private RelativeLayout failureLayout;
    private RelativeLayout painLayout;

    private TextView nervousScaleTextview;
    private TextView troubleSleepingScaleTextview;
    private TextView disconnectedScaleTextview;
    private TextView sadScaleTextview;
    private TextView troubleThinkingScaleTextview;
    private TextView irritableScaleTextview;
    private TextView hopelessScaleTextview;
    private TextView harmingScaleTextview;
    private TextView failureScaleTextview;
    private TextView painScaleTextview;

    static final int GET_SCALE_NERVOUS_REQUEST = 1;
    static final int GET_SCALE_TROUBLE_SLEEPING_REQUEST = 2;
    static final int GET_SCALE_DISCONNECTED_REQUEST = 3;
    static final int GET_SCALE_SAD_REQUEST = 4;
    static final int GET_SCALE_TROUBLE_THINKING_REQUEST = 5;
    static final int GET_SCALE_IRRITABLE_REQUEST = 6;
    static final int GET_SCALE_HOPELESS_REQUEST = 7;
//    static final int GET_SCALE_HARMING_REQUEST = 8;
    static final int GET_SCALE_FAILURE_REQUEST = 9;
    static final int GET_SCALE_PAIN_REQUEST = 10;

    static private final String TAG = "Scale";

    private SharedPreferences sharedPreferences;
    private int userId;

    private JSONArray moodJsonArray;

    private Boolean showNegativeDialogBoolean = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_not_feeling_good);

        setUpLayouts();

        submit = (Button) findViewById(R.id.submit_journal_negative_button);

        sharedPreferences = this.getSharedPreferences(getString(R.string.preferences_key),MODE_PRIVATE);
        userId = sharedPreferences.getInt(getString(R.string.preferences_user_id), -1);

        backButton = (ImageView) findViewById(R.id.back_button_negative_mood);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoToJournalActivity.enteredGoToJournal = true;
                Intent intent = new Intent(JournalNotFeelingGoodActivity.this,SelectMoodActivity.class);
                startActivity(intent);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()){

                    final SubmitJournalNotFeelingGood submitJournalNotFeelingGood = new SubmitJournalNotFeelingGood(JournalNotFeelingGoodActivity.this);
                    submitJournalNotFeelingGood.execute();

                    // if value greater than 5 show red
                    if(showNegativeDialogBoolean)
                    showNegativeDialog();
                    else{
                        showPositiveDialog();
                    }

                } else {
                    Toast.makeText(JournalNotFeelingGoodActivity.this, "No network connection", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isAvailable() && netInfo.isConnected();
    }

    public void showPositiveDialog(){
        final Dialog dialog = new Dialog(JournalNotFeelingGoodActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);

        Button dialogButton = (Button) dialog.findViewById(R.id.custom_dialog_ButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JournalNotFeelingGoodActivity.this, MainActivity.class);
                startActivity(intent);

                dialog.dismiss();
                finish();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_shape);
        dialog.show();
    }

    public void showNegativeDialog(){
        final Dialog dialog = new Dialog(JournalNotFeelingGoodActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);

        Button dialogButton = (Button) dialog.findViewById(R.id.custom_dialog_ButtonOK);

        TextView dialogTitle = (TextView) dialog.findViewById(R.id.custom_dialog_title);
        TextView dialogText = (TextView) dialog.findViewById(R.id.custom_dialog_text);

        dialogTitle.setText("We're sorry you're not feeling your best.");
        dialogText.setText("Some brain exercises might just change your mood. Would you like to play some games?");

        dialogButton.setBackgroundResource(R.drawable.button_ok_red_shape);

        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JournalNotFeelingGoodActivity.this, MainActivity.class);
                startActivity(intent);

                dialog.dismiss();
                finish();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_shape);
        dialog.show();
    }

    public void showNegativeCallDialog(){
        final Dialog dialog = new Dialog(JournalNotFeelingGoodActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_call);
        dialog.setCancelable(false);

        Button dialogCancelButton = (Button) dialog.findViewById(R.id.custom_dialog_call_ButtonCancel);
        Button dialogCallButton = (Button) dialog.findViewById(R.id.custom_dialog_call_ButtonCall);
        // if button is clicked, close the custom dialog
        dialogCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + "1 800 273 8255"));
                startActivity(callIntent);

                dialog.dismiss();
            }
        });

        dialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_shape);
        dialog.show();
    }

    private void setUpLayouts(){
        nervousLayout = (RelativeLayout) findViewById(R.id.journal_negative_nervous);
        troubleSleepingLayout = (RelativeLayout) findViewById(R.id.journal_negative_trouble_sleeping);
        disconnectedLayout = (RelativeLayout) findViewById(R.id.journal_negative_disconnected);
        sadLayout = (RelativeLayout) findViewById(R.id.journal_negative_sad_depressed);
        troubleThinkingLayout = (RelativeLayout) findViewById(R.id.journal_negative_trouble_memory);
        irritableLayout = (RelativeLayout) findViewById(R.id.journal_negative_irritable);
        hopelessLayout = (RelativeLayout) findViewById(R.id.journal_negative_hopeless);
        harmingLayout = (RelativeLayout) findViewById(R.id.journal_negative_harming_yourself);
        failureLayout = (RelativeLayout) findViewById(R.id.journal_negative_failure);
        painLayout = (RelativeLayout) findViewById(R.id.journal_negative_in_pain);

        nervousLayout.setOnClickListener(getScale);
        troubleSleepingLayout.setOnClickListener(getScale);
        disconnectedLayout.setOnClickListener(getScale);
        sadLayout.setOnClickListener(getScale);
        troubleThinkingLayout.setOnClickListener(getScale);
        irritableLayout.setOnClickListener(getScale);
        hopelessLayout.setOnClickListener(getScale);

        failureLayout.setOnClickListener(getScale);
        painLayout.setOnClickListener(getScale);

        harmingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    showNegativeCallDialog();
            }
        });

        nervousScaleTextview = (TextView) findViewById(R.id.journal_negative_nervous_scale);
        troubleSleepingScaleTextview = (TextView) findViewById(R.id.journal_negative_trouble_sleeping_scale);
        disconnectedScaleTextview = (TextView) findViewById(R.id.journal_negative_disconnected_scale);
        sadScaleTextview = (TextView) findViewById(R.id.journal_negative_sad_scale);
        troubleThinkingScaleTextview = (TextView) findViewById(R.id.journal_negative_trouble_memory_scale);
        irritableScaleTextview= (TextView) findViewById(R.id.journal_negative_irritable_scale);
        hopelessScaleTextview = (TextView) findViewById(R.id.journal_negative_hopeless_scale);
//        harmingScaleTextview = (TextView) findViewById(R.id.journal_negative_harming_yourself_scale);
        failureScaleTextview = (TextView) findViewById(R.id.journal_negative_failure_scale);
        painScaleTextview = (TextView) findViewById(R.id.journal_negative_in_pain_scale);
    }

    private View.OnClickListener getScale= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int requestCode = 0;
            switch (v.getId()){
                case R.id.journal_negative_nervous : requestCode=GET_SCALE_NERVOUS_REQUEST;
                    break;
                case R.id.journal_negative_trouble_sleeping : requestCode=GET_SCALE_TROUBLE_SLEEPING_REQUEST;
                    break;
                case R.id.journal_negative_disconnected : requestCode=GET_SCALE_DISCONNECTED_REQUEST;
                    break;
                case R.id.journal_negative_sad_depressed : requestCode = GET_SCALE_SAD_REQUEST;
                    break;
                case R.id.journal_negative_trouble_memory : requestCode = GET_SCALE_TROUBLE_THINKING_REQUEST;
                    break;
                case R.id.journal_negative_irritable : requestCode = GET_SCALE_IRRITABLE_REQUEST;
                    break;
                case R.id.journal_negative_hopeless : requestCode = GET_SCALE_HOPELESS_REQUEST;
                    break;
//                case R.id.journal_negative_harming_yourself : requestCode = GET_SCALE_HARMING_REQUEST;
//                    break;
                case R.id.journal_negative_failure : requestCode = GET_SCALE_FAILURE_REQUEST;
                    break;
                case R.id.journal_negative_in_pain : requestCode = GET_SCALE_PAIN_REQUEST;
                    break;
            }
            Intent intent = new Intent(JournalNotFeelingGoodActivity.this, ScaleActivity.class);
            if(requestCode!=0)
            startActivityForResult(intent,requestCode);
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

//        Log.e("onActivityResult","requestCode: "+requestCode+" resultCode: "+String.valueOf(resultCode==RESULT_OK)+" dataNotNull: "+String.valueOf(data!=null));
        if (resultCode == RESULT_OK && data != null) {
            int result = data.getIntExtra(TAG,0);
            if (result != 0) {

                Log.e("result",result+"");

                switch (requestCode) {
                    case GET_SCALE_NERVOUS_REQUEST:
                        nervousScaleTextview.setText(String.valueOf(result));
                        checkForNegative(result);
                        break;
                    case GET_SCALE_TROUBLE_SLEEPING_REQUEST:
                        troubleSleepingScaleTextview.setText(String.valueOf(result));
                        checkForNegative(result);
                        break;
                    case GET_SCALE_DISCONNECTED_REQUEST:
                        disconnectedScaleTextview.setText(String.valueOf(result));
                        checkForNegative(result);
                        break;
                    case GET_SCALE_SAD_REQUEST:
                        sadScaleTextview.setText(String.valueOf(result));
                        checkForNegative(result);
                        break;
                    case GET_SCALE_TROUBLE_THINKING_REQUEST:
                        troubleThinkingScaleTextview.setText(String.valueOf(result));
                        checkForNegative(result);
                        break;
                    case GET_SCALE_IRRITABLE_REQUEST:
                        irritableScaleTextview.setText(String.valueOf(result));
                        checkForNegative(result);
                        break;
                    case GET_SCALE_HOPELESS_REQUEST:
                        hopelessScaleTextview.setText(String.valueOf(result));
                        checkForNegative(result);
                        break;
//                    case GET_SCALE_HARMING_REQUEST: harmingScaleTextview.setText(String.valueOf(result));
//                        break;
                    case GET_SCALE_FAILURE_REQUEST:
                        failureScaleTextview.setText(String.valueOf(result));
                        checkForNegative(result);
                        break;
                    case GET_SCALE_PAIN_REQUEST:
                        painScaleTextview.setText(String.valueOf(result));
                        checkForNegative(result);
                        break;
                }
            }
    }


    }

    public class SubmitJournalNotFeelingGood extends AsyncTask<Void, Void, Void> {

        private Context context;

        public SubmitJournalNotFeelingGood(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create a new HttpClient and Post Header

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(getString(R.string.apiPatientMoodKey));

            try {
                // Add your data

                moodJsonArray = new JSONArray();

                if(!nervousScaleTextview.getText().equals(""))
                    addMood(7,nervousScaleTextview.getText().toString());

                if(!troubleSleepingScaleTextview.getText().equals(""))
                    addMood(8,troubleSleepingScaleTextview.getText().toString());

                if(!disconnectedScaleTextview.getText().equals(""))
                    addMood(9, disconnectedScaleTextview.getText().toString());

                if(!sadScaleTextview.getText().equals(""))
                    addMood(10, sadScaleTextview.getText().toString());

                if(!troubleThinkingScaleTextview.getText().equals(""))
                    addMood(11, troubleThinkingScaleTextview.getText().toString());

                if(!irritableScaleTextview.getText().equals(""))
                    addMood(12, irritableScaleTextview.getText().toString());

                if(!hopelessScaleTextview.getText().equals(""))
                    addMood(13, hopelessScaleTextview.getText().toString());

                if(!failureScaleTextview.getText().equals(""))
                    addMood(15, failureScaleTextview.getText().toString());

                if(!painScaleTextview.getText().equals(""))
                    addMood(16,painScaleTextview.getText().toString());

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
                    Log.i("Journal negative","successful");
                }else{
                    Log.e("Error journal negative", "error");
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

    private void addMood(int moodId, String value){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("mood_id",moodId);
            jsonObject.put("value", value);

            moodJsonArray.put(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkForNegative(int value){
        if(value>5)
            showNegativeDialogBoolean = true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(JournalNotFeelingGoodActivity.this, SelectMoodActivity.class);
        startActivity(intent);
        finish();
    }


}
