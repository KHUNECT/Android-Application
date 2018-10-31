package org.androidtown.khunect_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AdsActivity extends AppCompatActivity {
    private String className = "Ads";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
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

    //알바버튼
    public void onAlbaButtonClicked(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), AdsAlbaActivity.class);
        startActivity(intent);
    }//#

    public void onButtonClicked(View view)
    {
        Toast.makeText(this, "뒤에 버튼이 눌렸어요.", Toast.LENGTH_SHORT).show();
    }

    public void onPostingButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
        startActivity(intent);
    }
}
