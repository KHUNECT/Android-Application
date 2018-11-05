package org.androidtown.khunect_01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
    private EditText editTextEmail;
    private EditText editTextMajor;
    private ImageView image_profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        editTextNickname = (EditText) findViewById((R.id.text_userNickname));
        editTextId = (EditText) findViewById(R.id.text_userID);
        editTextPw1 = (EditText) findViewById(R.id.text_userPassword1);
        editTextPw2 = (EditText) findViewById(R.id.text_userPassword2);
        editTextEmail = (EditText) findViewById(R.id.text_email);
        editTextMajor =  (EditText) findViewById(R.id.text_major);

        image_profile = (ImageView)findViewById(R.id.imageView_profile);

    }


    private int PICK_IMAGE_REQUEST = 1;

    public void onUploadButtonClicked (View view)
    {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    image_profile.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void onSignupButtonClicked(View view) throws UnsupportedEncodingException
    {


        String nickname = editTextNickname.getText().toString();
        String userId = editTextId.getText().toString();
        String pw1 = editTextPw1.getText().toString();
        String pw2 = editTextPw2.getText().toString();
        String email = editTextEmail.getText().toString();
        String major = editTextMajor.getText().toString();

        sendPost();
        Toast.makeText(this, "가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
/*
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
        else if(useremail.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else if (usermajor.isEmpty())
        {
            Toast.makeText(getApplicationContext(), "전공을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
        else
        {*/
        // Create a new HttpClient and Post Header

    }

    public void onCancelButtonClicked(View view) {
        super.onBackPressed();
    }




    public void sendPost() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://13.125.196.191/api/user/create");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    conn.setRequestProperty("Accept","application/json");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);

                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("userId", editTextId.toString());
                    jsonParam.put("nickname", editTextNickname.toString());
                    jsonParam.put("password", editTextPw1.toString());
                    jsonParam.put("email", editTextEmail.toString());
                    jsonParam.put("major", editTextMajor.toString());

                    Log.i("JSON", jsonParam.toString());
                    DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                    //os.writeBytes(URLEncoder.encode(jsonParam.toString(), "UTF-8"));
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
    }

    /*
     Post 방식으로 Http 전송하기

    private void select_doProcess() {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("http://13.125.196.191/api/user/create");
        ArrayList<NameValuePair> nameValues =
                new ArrayList<NameValuePair>(3);

        try {
            //Post방식으로 넘길 값들을 각각 지정을 해주어야 한다.
            nameValues.add(new BasicNameValuePair(
                    "nickname", URLDecoder.decode(usernickname, "UTF-8")));
            nameValues.add(new BasicNameValuePair(
                    "userId", URLDecoder.decode(userid, "UTF-8")));
            nameValues.add(new BasicNameValuePair(
                    "password", URLDecoder.decode(userpw, "UTF-8")));

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
*/
}
