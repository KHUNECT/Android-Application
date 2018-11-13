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
        //send_post();
        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(intent);

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

    public static void send_post() throws IOException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://13.125.196.191/api/user/create");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(20000);
                    conn.setReadTimeout(20000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Cache-Control", "no-cache");

                    String boundary = Long.toHexString(System.currentTimeMillis()); // Just generate some unique random value.
                    conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary+";charset=utf-8");
                    DataOutputStream dos = new DataOutputStream(conn.getOutputStream());

                    String lineEnd = "\r\n";
                    String twoHyphens = "--";


                    dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"name\"" + lineEnd + lineEnd);
                    dos.writeUTF("이룸");
                    //writeBytes에 한글이 들어갈 경우 깨져서 전송된다. UrlEncoder로 UTF-8, EUC-KR을 해줘도 깨짐
                    dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"userId\"" + lineEnd + lineEnd);
                    dos.writeUTF("힌굴");
                    //한글 부분만 writeUTF 을 사용하여 전송하면 깨지지않고 보낼 수 있다.
                    dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"password\"" + lineEnd + lineEnd);
                    dos.writeUTF("qq");

                    dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"nickname\"" + lineEnd + lineEnd);
                    dos.writeUTF("dasd");

                    dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"email\"" + lineEnd + lineEnd);
                    dos.writeUTF("dasd");

                    dos.writeBytes(lineEnd + twoHyphens + boundary + lineEnd);
                    dos.writeBytes("Content-Disposition: form-data; name=\"major\"" + lineEnd + lineEnd);
                    dos.writeUTF("dasd");

                    int responseCode = 0;
                    responseCode = conn.getResponseCode();
                    response_code = responseCode;

                    Log.i("Response Code", Integer.toString(responseCode));
                    Log.i("MSG" , conn.getResponseMessage());
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        thread.start();

    }
    public static void sendpost() throws Exception {
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
                    writer.append("Content-Disposition: form-data; name=\"body\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + userId).append(CRLF);
                    writer.append(CRLF).append(userId).append(CRLF).flush();

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
                    con.setRequestProperty("userId", "test001");

                    int responseCode = con.getResponseCode();
                    //System.out.println("\nSending 'GET' request to URL : " + url);
                    //System.out.println("Response Code : " + responseCode);

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(con.getInputStream()));
                    String inputLine;
                    StringBuffer response = new StringBuffer();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();

                    //print result
                    Log.i("Result",response.toString());
                    System.out.println(response.substring(response.indexOf("userId"),response.indexOf(",\"resizedImage")));

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    //회원가입 버튼
    public void onSignupButtonClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), SigninActivity.class);
        startActivity(intent);
    }

}
