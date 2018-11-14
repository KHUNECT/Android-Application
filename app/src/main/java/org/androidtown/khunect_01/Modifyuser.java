package org.androidtown.khunect_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Modifyuser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyuser);
    }

    public void onProfileClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), ModifyprofileActivity.class);
        startActivity(intent);
    }

    public void onNickClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), ModnicknameActivity.class);
        startActivity(intent);
    }

    public void onPasswordClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), ModpasswordActivity.class);
        startActivity(intent);
    }
}
