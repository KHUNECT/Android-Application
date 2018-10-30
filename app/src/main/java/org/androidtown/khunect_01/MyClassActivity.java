package org.androidtown.khunect_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MyClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);
    }

    public void onClickedHomeButton(View view) {
        super.onBackPressed();
    }

    public void onClickedTogetherButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(this, TogetherActivity.class);
        startActivity(intent);
    }

    public void onClickedAdsButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(this, AdsActivity.class);
        startActivity(intent);
    }

    public void onClickedTradeButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(this, TradeActivity.class);
        startActivity(intent);
    }

    public void onClassClicked(View view) {
        String text = ((Button)view).getText().toString(); //버튼에 적힌 텍스트
        Intent intent = new Intent(getApplicationContext(), ClassboardActivity.class);

        intent.putExtra("classname", text); //classname으로 ClassboardActivity에 넘김

        startActivity(intent);
    }
}
