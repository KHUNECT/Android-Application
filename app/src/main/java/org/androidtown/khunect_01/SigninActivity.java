package org.androidtown.khunect_01;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class SigninActivity extends AppCompatActivity {

    private EditText editTextNickname;
    private EditText editTextId;
    private EditText editTextPw1;
    private EditText editTextPw2;

    private String usernickname;
    private String userpw;
    private String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        editTextNickname = (EditText) findViewById((R.id.text_userNickname));
        editTextId = (EditText) findViewById(R.id.text_userID);
        editTextPw1 = (EditText) findViewById(R.id.text_userPassword1);
        editTextPw2 = (EditText) findViewById(R.id.text_userPassword2);
    }

    public void onSignupButtonClicked(View view) {
        usernickname = editTextNickname.getText().toString();
        userid = editTextId.getText().toString();
        String Pw1 = editTextPw1.getText().toString();
        String Pw2 = editTextPw2.getText().toString();

        if (usernickname.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }

        else if (userid.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }

        else if (Pw1.isEmpty() || Pw2.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else if (!Pw1.equals(Pw2))
        {
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않아요.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
            userpw = Pw1;
            select_doProcess();
        }

    }

    public void onCancelButtonClicked(View view) {
        super.onBackPressed();
    }

    /*
     Post 방식으로 Http 전송하기
    */
    private void select_doProcess() {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://192.168.219.101:8090/readus/insertuserinfo.do");
        ArrayList<NameValuePair> nameValues =
                new ArrayList<NameValuePair>();

        try {
            //Post방식으로 넘길 값들을 각각 지정을 해주어야 한다.
            nameValues.add(new BasicNameValuePair(
                    "userNickname", URLDecoder.decode(usernickname, "UTF-8")));
            nameValues.add(new BasicNameValuePair(
                    "userId", URLDecoder.decode(userid, "UTF-8")));
            nameValues.add(new BasicNameValuePair(
                    "userPw", URLDecoder.decode(userpw, "UTF-8")));

            //HttpPost에 넘길 값을들 Set해주기
            post.setEntity(
                    new UrlEncodedFormEntity(
                            nameValues, "UTF-8"));
        } catch (UnsupportedEncodingException ex) {
            Log.e("Insert Log", ex.toString());
        }

        try {
            //설정한 URL을 실행시키기
            HttpResponse response = client.execute(post);
            //통신 값을 받은 Log 생성. (200이 나오는지 확인할 것~) 200이 나오면 통신이 잘 되었다는 뜻!
            Log.i("Insert Log", "response.getStatusCode:" + response.getStatusLine().getStatusCode());

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/*
    private void inserttoToDatabase(String nickname, String Id, String Pw) {
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SigninActivity.this, "Please Wait", null, true, true);
            }
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
            }
            @Override
            protected String doInBackground(String... params) {

                try {
                    String Id = (String) params[0];
                    String Pw = (String) params[1];

                    String link = "http://본인PC IP주소/post.php";
                    String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
                    data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");

                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();

                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    StringBuilder sb = new StringBuilder();
                    String line = null;

                    // Read Server Response
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    return sb.toString();
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
        }
        InsertData task = new InsertData();
        task.execute(Id, Pw);
    }
*/
}
