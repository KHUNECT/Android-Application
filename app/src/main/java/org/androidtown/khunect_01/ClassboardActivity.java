package org.androidtown.khunect_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

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

public class ClassboardActivity extends AppCompatActivity {
    private static int response_code = 0;

    private String className = "Classboard";
    private String professorname ;
    private String boardid; //학수번호

    private ListView listview;
    private ListViewAdapter adapter;

    private static String classname;

    private JSONArray jarr;

    private static String classnumber = "학수번호";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classboard);

        TextView tx1 = (TextView)findViewById(R.id.label_classname); //id:label_classname의 텍스트를 tx1이라는 변수로 저장.

        Intent intent = getIntent();

        professorname = intent.getExtras().getString("professorname");
        className = intent.getExtras().getString("classname");
        classname = className; //classname에 저장돼있는 정보를 name에 저장
        classname += " 게시판";
        classname =classname+" ("+professorname+" 교수님)";
        tx1.setText(classname); //label_classname에 name을 set.


        boardid = intent.getExtras().getString("boardid");

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
                    intent.putExtra("boardName", classname);
                    startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //게시판 id로 게시글 목록 만들기
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

    public void onPostingButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), PostingActivity.class);
        intent.putExtra("boardid", className);
        intent.putExtra("boardID", boardid);
        startActivity(intent);
    }
}
