package org.androidtown.khunect_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class TogetherActivity extends AppCompatActivity {
    private String boardid = "study";
    private static int response_code = 0;

    private ListView listview;
    private ListViewAdapter adapter;
    private JSONArray jarr;

    private static String classnumber = "학수번호";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_together);

        try {
            sendget();

// Adapter 생성
            adapter = new ListViewAdapter();

            // 리스트뷰 참조 및 Adapter달기
            listview = (ListView) findViewById(R.id.listview);
            listview.setAdapter(adapter);

            for (int i = 0; i < jarr.length(); i++) {
                JSONObject jobj = jarr.getJSONObject(i);
                adapter.addItem(jobj.getString("_id"), jobj.getString("title"), jobj.getString("writerNickname"));
            }
//onItemClickListener를 추가
            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    // get item
                    ListViewItem item = (ListViewItem) parent.getItemAtPosition(position);

                    String idStr = item.getId();
                    String titleStr = item.getTitle();
                    String descStr = item.getDesc();

                    Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                    intent.putExtra("_id", idStr);
                    intent.putExtra("title", titleStr);
                    intent.putExtra("writerNickname", descStr);
                    intent.putExtra("boardName", "스터디 게시판");
                    startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendget() throws IOException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL obj = new URL("http://13.125.196.191/api/board/"+boardid);
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                    // optional default is GET
                    con.setRequestMethod("GET");


                    //add request header
                    //con.setRequestProperty("userId", userId);

                    int responseCode = con.getResponseCode();
                    response_code = responseCode;
                    //System.out.println("\nSending 'GET' request to URL : " + url);
                    Log.i("Response Code",""+responseCode);
                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    jarr = new JSONArray(response.toString());



                    //print result
                    Log.i("Result",response.toString());
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void onClickedHomeButton(View view) {
        super.onBackPressed();
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

    public void onHobbyButtonClicked(View view) {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), TogetherHobbyActivity.class);
        startActivity(intent);
    }

    public void onPostingButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
        intent.putExtra("boardid", boardid);
        intent.putExtra("boardID", boardid);
        startActivity(intent);
    }
}
