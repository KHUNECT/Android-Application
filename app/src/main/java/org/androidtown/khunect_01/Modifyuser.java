package org.androidtown.khunect_01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class Modifyuser extends AppCompatActivity {

    private ImageView profile;
    private TextView userid;
    private TextView nickname;
    private TextView email;
    private TextView major;


    private Bitmap img = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyuser);

        userid = (TextView)findViewById(R.id.textView9_userid);
        nickname = (TextView)findViewById(R.id.textView10_nickname);
        email = (TextView)findViewById(R.id.textView11_email);
        major = (TextView)findViewById(R.id.textView12_major);
        profile = (ImageView)findViewById(R.id.imageView_profile2);

        update_screen();
    }

    public void update_screen()
    {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String urldisplay = User_detail.Image;
                Bitmap mIcon11 = null;
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
                img = mIcon11;
            }
        });thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        profile.setImageBitmap(img);
        userid.setText(" ID:\t"+User_detail.userId);
        nickname.setText( " 닉네임:\t"+User_detail.nickname);
        email.setText(" 이메일:\t"+User_detail.email);
        major.setText(" 전공:\t"+User_detail.major);
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

    public void onKlasClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), Signupklas.class);
        startActivity(intent);
    }
}
