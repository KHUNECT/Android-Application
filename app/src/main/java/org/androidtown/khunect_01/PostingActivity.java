package org.androidtown.khunect_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PostingActivity extends AppCompatActivity {

    private static String boardid;
    private static String boardName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        TextView text_boardname = (TextView)findViewById(R.id.textView_boardName);

        Intent intent = getIntent(); /*데이터 수신*/
        boardid = intent.getExtras().getString("boardid"); /*String형*/


        switch (boardid){
            case "study" : boardName = "스터디 게시판"; break;
            case "hobby" : boardName = "취미 게시판"; break;
            case "club" : boardName = "동아리 게시판";break;
            case "alba" : boardName = "알바 게시판"; break;
            case "contest": boardName = "공모전 게시판"; break;
            case "market": boardName = "거래 게시판"; break;
            case "gonggu": boardName = "공구 게시판"; break;
            default://학수번호 api get으로 과목명 가져오기
        }
        text_boardname.setText(boardName);
    }

    public void onSubmitButtonClicked(View view) {

    }
}
