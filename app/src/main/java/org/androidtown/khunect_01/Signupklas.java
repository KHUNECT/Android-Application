package org.androidtown.khunect_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Signupklas extends AppCompatActivity {

    private static int response_code = 0;

    private EditText Text_klasid;
    private EditText Text_klaspw;

    private static String userId = "";
    private static String klasId = "";
    private static String klasPassword = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupklas);
        Text_klasid = (EditText) findViewById(R.id.editText_klasid);
        Text_klaspw = (EditText) findViewById(R.id.editText_klaspw);

    }

    public void onKlasButtonClicked(View view) throws Exception {
        klasId = Text_klasid.getText().toString();
        klasPassword = Text_klaspw.getText().toString();

        if (klasId.isEmpty()) {
            Toast.makeText(getApplicationContext(), "아이디을 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else if (klasPassword.isEmpty()) {
            Toast.makeText(getApplicationContext(), "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            send_post();
            if (response_code == 200) {

                Toast.makeText(getApplicationContext(), "연동 성공", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "KLAS 정보를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void send_post() throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://13.125.196.191/api/user/setLecture");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("userId", User_detail.ObjectId);
                    jsonParam.put("klasId", klasId);
                    jsonParam.put("klasPassword", klasPassword);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

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


                    JSONObject obj = new JSONObject(response.toString());
                    User_detail.set_klasdetail(obj);

                    response_code = conn.getResponseCode();
                    Log.i("STATUS", String.valueOf(response_code));
                    Log.i("MSG" , conn.getResponseMessage());

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

    public void onCancelClicked(View view) {
        super.onBackPressed();
    }
};
