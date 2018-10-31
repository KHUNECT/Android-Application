package org.androidtown.khunect_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class TogetherHobbyActivity extends AppCompatActivity {
    private String className = "TogetherHobby";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together_hobby);
    }

    public void onClickedHomeButton(View view) {
        super.onBackPressed();
    }

    public void onClickedMyclassButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MyClassActivity.class);
        startActivity(intent);
    }

    public void onClickedAdsButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), AdsActivity.class);
        startActivity(intent);
    }

    public void onClickedTradeButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), TradeActivity.class);
        startActivity(intent);
    }


    public void onStudyButtonClicked(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(),TogetherActivity.class);
        startActivity(intent);
    }

    public void onPostingButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
        startActivity(intent);
    }
}
