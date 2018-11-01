package org.androidtown.khunect_01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;

public class SignupphtoActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;

    private String nickname;
    private String userid;
    private String password;
    private String email;
    private String major;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        nickname = intent.getExtras().getString("nickname");
        userid = intent.getExtras().getString("userid");
        password = intent.getExtras().getString("password");
        email = intent.getExtras().getString("email");
        major = intent.getExtras().getString("major");

        imageView = (ImageView)findViewById(R.id.imageView_profile);

    }

    public void onUploadButtonClicked (View view){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    imageView.setImageBitmap(img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
    public void onBackButtonClicked(View view) {
        super.onBackPressed();
    }

    public void onSignupButtonClicked(View view) {
    }
}
