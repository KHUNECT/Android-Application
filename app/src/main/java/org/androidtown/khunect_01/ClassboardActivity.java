package org.androidtown.khunect_01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ClassboardActivity extends AppCompatActivity {
    private String className = "Classboard";

    private static String classnumber = "학수번호";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classboard);

        TextView tx1 = (TextView)findViewById(R.id.label_classname); //id:label_classname의 텍스트를 tx1이라는 변수로 저장.

        Intent intent = getIntent();

        String name = intent.getExtras().getString("classname"); //classname에 저장돼있는 정보를 name에 저장
        name += " 게시판";
        tx1.setText(name); //label_classname에 name을 set.
    }

    public void onPostingButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
        intent.putExtra("boardid", "학수번호");
        startActivity(intent);
    }
}
