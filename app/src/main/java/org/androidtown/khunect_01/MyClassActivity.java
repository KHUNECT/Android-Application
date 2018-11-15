package org.androidtown.khunect_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

public class MyClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);


        try {
            ListView listview;
            ListViewAdapter adapter;

            // Adapter 생성
            adapter = new ListViewAdapter();

            // 리스트뷰 참조 및 Adapter달기
            listview = (ListView) findViewById(R.id.list_myclass);
            listview.setAdapter(adapter);

            for (int i = 0; i < KLAS_detail.klas_detail.length(); i++) {

                JSONObject jobj = KLAS_detail.klas_detail.getJSONObject(i);
                adapter.addItem(jobj.getString("boardId"),jobj.getString("title"),jobj.getString("professor"));

            }

            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    // get item
                    ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                    String idStr = item.getId();
                    String titleStr = item.getTitle();
                    String descStr = item.getDesc();

                    Intent intent = new Intent(getApplicationContext(), ClassboardActivity.class);
                    intent.putExtra("boardid", idStr);
                    intent.putExtra("classname", titleStr); //classname으로 ClassboardActivity에 넘김
                    intent.putExtra("professorname", descStr);
                    startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public void onClickedTradeButton(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), TradeActivity.class);
        startActivity(intent);
    }
}
