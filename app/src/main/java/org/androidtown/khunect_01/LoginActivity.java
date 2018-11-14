package org.androidtown.khunect_01;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.PersistentCookieStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextId;
    private EditText editTextPassword;

    private static String userId;
    private static String password;

    private static int response_code = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextId = (EditText) findViewById(R.id.InputText_ID);
        editTextPassword = (EditText) findViewById(R.id.InputText_PW);

        AsyncHttpClient client = HttpClient.getInstance();
        PersistentCookieStore myCookieStore = new PersistentCookieStore(this);
        client.setCookieStore((myCookieStore));
    }



    //뒤로가기 두번 = 종료
    private final long FINISH_INTERVAL_TIME = 2000;
    private long   backPressedTime = 0;
    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && FINISH_INTERVAL_TIME >= intervalTime)
        {
            super.onBackPressed();
        }
        else
        {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "종료하려면 한 번 더 누르세요.", Toast.LENGTH_SHORT).show();
        }
    }//#

    //로그인 버튼
    public void onLoginButtonClicked(View view) throws Exception {
        userId = editTextId.getText().toString();
        password = editTextPassword.getText().toString();
        /*sendpostJSON();
        if(response_code == 200) {*/
            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
            sendget();
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(intent);
        /*}
        else{
            Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
        }*/

        /*RequestParams params = new RequestParams();
        if(!userId.isEmpty() && !password.isEmpty()) { //로그인 api는 post임 수정해야함.

            Log.i("Msg", "Clicked Login Btn id : " + userId + " pwd : " + password);
            params.put("userId", userId);
            params.put("password", password);
            HttpClient.post("user/login", params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();

                    //세션유지코드

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    System.out.println("response code : " + i);
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "로그인 정보를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }*/
    }

    public static void sendpostFormdata() throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://13.125.196.191/api/user/login";
                String charset = "UTF-8";
                String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
                String CRLF = "\r\n"; // Line separator required by multipart/form-data.

                URLConnection connection = null;
                try {
                    connection = new URL(url).openConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

                try (
                        OutputStream output = connection.getOutputStream();
                        PrintWriter writer = new PrintWriter(new OutputStreamWriter(output, charset), true);
                ) {
                    // Send normal param.
                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"userId\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                    writer.append(CRLF).append(userId).append(CRLF).flush();

                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"password\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + charset).append(CRLF);
                    writer.append(CRLF).append(password).append(CRLF).flush();

                    // End of multipart/form-data.
                    writer.append("--" + boundary + "--").append(CRLF).flush();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Request is lazily fired whenever you need to obtain information about response.
                int responseCode = 0;
                try {
                    responseCode = ((HttpURLConnection) connection).getResponseCode();
                    response_code = responseCode;
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Log.i("Response Code", Integer.toString(responseCode));
                try {
                    Log.i("MSG" , ((HttpURLConnection) connection).getResponseMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        thread.join();
    }

    public void sendpostJSON() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://13.125.196.191/api/user/login");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("userId", userId);
                    jsonParam.put("password", password);

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
                    os.writeBytes(jsonParam.toString());

                    os.flush();
                    os.close();

                    Log.i("STATUS", String.valueOf(conn.getResponseCode()));
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

    public void sendget() throws IOException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    URL obj = new URL("http://13.125.196.191/api/user/detail"+"?userId="+userId);
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
                    JSONObject jObject = new JSONObject(response.toString());
                    User_detail.set_user(jObject.getString("userId"),jObject.getString("nickname"),jObject.getString("major"),
                            jObject.getString("email"),jObject.getString("resizedImage"));
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

    //회원가입 버튼
    public void onSignupButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
        startActivity(intent);
    }

}
