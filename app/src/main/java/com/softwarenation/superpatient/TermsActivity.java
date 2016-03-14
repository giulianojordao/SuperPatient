package com.softwarenation.superpatient;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class TermsActivity extends Activity {

    TextView termsTextviewParagraph_1;
    TextView termsTextviewParagraph_2;
    TextView termsTextviewParagraph_3;
    TextView termsTextviewParagraph_4;
    TextView termsTextviewParagraph_5;
    TextView termsTextviewParagraph_6;
    TextView termsTextviewParagraph_7;
    TextView termsTextviewParagraph_8;

    TextView termsTextviewParagraph2_1;
    TextView termsTextviewParagraph2_2;
    TextView termsTextviewParagraph2_3;
    TextView termsTextviewParagraph2_4;
    TextView termsTextviewParagraph2_5;

    private Button agreeTermsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        termsTextviewParagraph_1 = (TextView) findViewById(R.id.terms_conditions_paragraph_1);
        termsTextviewParagraph_2 = (TextView) findViewById(R.id.terms_conditions_paragraph_2);
        termsTextviewParagraph_3 = (TextView) findViewById(R.id.terms_conditions_paragraph_3);
        termsTextviewParagraph_4 = (TextView) findViewById(R.id.terms_conditions_paragraph_4);
        termsTextviewParagraph_5 = (TextView) findViewById(R.id.terms_conditions_paragraph_5);
        termsTextviewParagraph_6 = (TextView) findViewById(R.id.terms_conditions_paragraph_6);
        termsTextviewParagraph_7 = (TextView) findViewById(R.id.terms_conditions_paragraph_7);
        termsTextviewParagraph_8 = (TextView) findViewById(R.id.terms_conditions_paragraph_8);

        termsTextviewParagraph2_1 = (TextView) findViewById(R.id.terms_conditions_paragraph2_1);
        termsTextviewParagraph2_2 = (TextView) findViewById(R.id.terms_conditions_paragraph2_2);
        termsTextviewParagraph2_3 = (TextView) findViewById(R.id.terms_conditions_paragraph2_3);
        termsTextviewParagraph2_4 = (TextView) findViewById(R.id.terms_conditions_paragraph2_4);
        termsTextviewParagraph2_5 = (TextView) findViewById(R.id.terms_conditions_paragraph2_5);

        agreeTermsButton = (Button) findViewById(R.id.terms_conditions_agree_button);

        termsTextviewParagraph_1.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph_1)));
        termsTextviewParagraph_2.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph_2)));
        termsTextviewParagraph_3.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph_3)));
        termsTextviewParagraph_4.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph_4)));
        termsTextviewParagraph_5.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph_5)));
        termsTextviewParagraph_6.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph_6)));
        termsTextviewParagraph_7.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph_7)));
        termsTextviewParagraph_8.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph_8)));

        termsTextviewParagraph2_1.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph2_1)));
        termsTextviewParagraph2_2.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph2_2)));
        termsTextviewParagraph2_3.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph2_3)));
        termsTextviewParagraph2_4.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph2_4)));
        termsTextviewParagraph2_5.setText(Html.fromHtml(getString(R.string.terms_conditions_paragraph2_5)));

        agreeTermsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TermsActivity.this,LetsGoActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }



}
