package org.androidtown.khunect_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //자동로그인이 아니면
        startLoginActivity();

        //로딩화면
        Intent intent = new Intent(this, LoadingActivity.class);
        startActivity(intent);

        finish();
    }


    public void startLoginActivity(){
        Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent2);
    }


    //필없는거. 나중에 지울 것임
    public void onClickedLogin(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

}
