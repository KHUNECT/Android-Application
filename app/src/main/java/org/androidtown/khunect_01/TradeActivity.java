package org.androidtown.khunect_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class TradeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
    }

    public void onClickedHomeButton(View view) {
        super.onBackPressed();
    }

    public void onClickedTogetherButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), TogetherActivity.class);
        startActivity(intent);
    }

    public void onClickedAdsButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), AdsActivity.class);
        startActivity(intent);
    }

    public void onClickedMyclassButton(View view) {
        if(KLAS_detail.klas_detail == null)
        {
            Toast.makeText(getApplicationContext(), "KLAS 연동이 필요합니다. 내정보를 확인하세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MyClassActivity.class);
        startActivity(intent);
    }

    public void onSellButtonClicked(View view){
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), TradeSellActivity.class);
        startActivity(intent);
    }

    public void onPostingButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
        intent.putExtra("boardid","market");
        startActivity(intent);
    }
}
