package org.androidtown.khunect_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    private static int response_code;

    private ListView listview_hot;
    private ListViewAdapter adapter;
    private JSONArray jarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if(KLAS_detail.klas_detail == null) {
            try {
                sendpostJSON();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(response_code == 200)
            {

            }
            else{
                Toast.makeText(getApplicationContext(), "KLAS 연동 실패, 내 정보에서 연동해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
        /*try {
            sendget();

// Adapter 생성
            adapter = new ListViewAdapter();

            // 리스트뷰 참조 및 Adapter달기
            listview_hot = (ListView) findViewById(R.id.listview_hot);
            listview_hot.setAdapter(adapter);

            for (int i = 0; i < jarr.length(); i++) {
                JSONObject jobj = jarr.getJSONObject(i);
                adapter.addItem(jobj.getString("_id"), jobj.getString("title"), jobj.getString("writerNickname"));
            }
//onItemClickListener를 추가
            listview_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    startActivity(intent);
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
/*
    //게시판 id로 게시글 목록 만들기
    public void sendget() throws IOException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL obj = new URL("http://13.125.196.191/api/post/list/hot");
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
*/
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

    public void onClickedMyclassButton(View view) {
        /*if(KLAS_detail.klas_detail == null)
        {
            Toast.makeText(getApplicationContext(), "KLAS 연동이 필요합니다. 내정보를 확인하세요.", Toast.LENGTH_SHORT).show();
            return;
        }*/
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
        User_detail.logout();
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
        Toast.makeText(getApplicationContext(), User_detail.ObjectId, Toast.LENGTH_SHORT).show();
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

    public void onModifyClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), Modifyuser.class);
        startActivity(intent);
    }

    public void sendpostJSON() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://13.125.196.191/api/user/getLecture");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("userId", User_detail.ObjectId);

                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    response_code = conn.getResponseCode();
                    Log.i("STATUS", String.valueOf(response_code));
                    Log.i("MSG" , conn.getResponseMessage());


                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    //print result
                    Log.i("Result",response.toString());

                    JSONArray arr = new JSONArray(response.toString());
                    KLAS_detail.klas_detail = arr;

                    conn.disconnect();

                } catch (Exception e) {
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
}
