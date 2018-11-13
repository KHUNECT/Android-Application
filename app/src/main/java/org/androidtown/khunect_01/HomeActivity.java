package org.androidtown.khunect_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    //뒤로가기 두번 = 종료
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            //super.onBackPressed();
            finishAffinity();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "종료하려면 한 번 더 누르세요.", Toast.LENGTH_SHORT).show();
        }
    }//#

    public void onClickedHomeButton(View view) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
    }

    public void onClickedMyclassButton(View view) {
        Intent intent = new Intent(getApplicationContext(), MyClassActivity.class);
        startActivity(intent);
    }

    public void onClickedTogetherButton(View view) {
        Intent intent = new Intent(getApplicationContext(), TogetherActivity.class);
        startActivity(intent);
    }

    public void onClickedAdsButton(View view) {
        Intent intent = new Intent(getApplicationContext(), AdsActivity.class);
        startActivity(intent);
    }

    public void onClickedTradeButton(View view) {
        Intent intent = new Intent(getApplicationContext(), TradeActivity.class);
        startActivity(intent);
    }

    public void onLogoutButtonClicked(View view) {
        //로그아웃
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    public void onSearchButtonClicked(View view) {
        //검색
        Intent intent = new Intent(getApplicationContext(), SearchhomeActivity.class);
        startActivity(intent);
    }

    public void onHotMoreButtonClicked(View view) {
        //뜨게
        Intent intent = new Intent(getApplicationContext(), HotgaeActivity.class);
        startActivity(intent);
    }

    public void onAlbaButtonClicked(View view) {

        Intent intent = new Intent(getApplicationContext(), AdsAlbaActivity.class);
        startActivity(intent);
    }

    public void onPostClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), PostActivity.class);
        startActivity(intent);
    }

    public void onKLASClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), Signupklas.class);
        startActivity(intent);
    }
}
