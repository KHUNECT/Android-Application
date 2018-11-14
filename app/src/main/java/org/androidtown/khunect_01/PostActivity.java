package org.androidtown.khunect_01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class PostActivity extends AppCompatActivity {

    private static TextView textView_boardname;
    private static TextView textView_info;
    private static TextView textView_title;
    private static TextView textView_context;
    private static TextView textView_comment;
    private static ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        textView_boardname = (TextView)findViewById(R.id.text_게시판이름);
        //User_detail.userId
    }
}
