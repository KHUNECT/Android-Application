package org.androidtown.khunect_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdsAlbaActivity extends AppCompatActivity {
    private String className = "AdsAlba";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_alba);
    }

    //MenuButtons
    public void onClickedHomeButton(View view) {
        super.onBackPressed();
    }

    public void onClickedTogetherButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), TogetherActivity.class);
        startActivity(intent);
    }

    public void onClickedTradeButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), TradeActivity.class);
        startActivity(intent);
    }

    public void onClickedMyclassButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MyClassActivity.class);
        startActivity(intent);
    }//#

    //동아리버튼
    public void onCircleButtonClicked(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), AdsActivity.class);
        startActivity(intent);
    }//#

    public void onPostingButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
        startActivity(intent);
    }
}
