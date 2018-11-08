package org.androidtown.khunect_01;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.file.Files;

public class SigninActivity extends AppCompatActivity {

    private EditText editTextNickname;
    private EditText editTextId;
    private EditText editTextPw1;
    private EditText editTextPw2;
    private EditText editTextEmail;
    private EditText editTextMajor;
    private ImageView image_profile;

    private static String nickname;
    private static String userId;
    private static String pw1;
    private static String pw2;
    private static String email;
    private static String major;
    private static String selectedImagePath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        editTextNickname = (EditText) findViewById((R.id.text_userNickname));
        editTextId = (EditText) findViewById(R.id.text_userID);
        editTextPw1 = (EditText) findViewById(R.id.text_userPassword1);
        editTextPw2 = (EditText) findViewById(R.id.text_userPassword2);
        editTextEmail = (EditText) findViewById(R.id.text_email);
        editTextMajor = (EditText) findViewById(R.id.text_major);

        image_profile = (ImageView) findViewById(R.id.imageView_profile);

    }


    private int PICK_IMAGE_REQUEST = 1;
    private static final int SELECT_PICTURE = 1;

    public void onUploadButtonClicked(View view) {

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

                    if (resultCode == RESULT_OK) {
                        if (requestCode == SELECT_PICTURE) {
                            // 선택한 이미지에서 비트맵 생성
                            InputStream in = getContentResolver().openInputStream(data.getData());
                            Bitmap img = BitmapFactory.decodeStream(in);
                            in.close();
                            // 이미지 표시
                            image_profile.setImageBitmap(img);

                            Uri selectedImageUri = data.getData();
                            selectedImagePath = getPath(selectedImageUri);
                        }
                    }
                    /*
                    // 선택한 이미지에서 비트맵 생성
                    InputStream in = getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();
                    // 이미지 표시
                    image_profile.setImageBitmap(img);
*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
        }
    }

    public String getPath(Uri uri) {
        // uri가 null일경우 null반환
        if( uri == null ) {
            return null;
        }
        // 미디어스토어에서 유저가 선택한 사진의 URI를 받아온다.
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // URI경로를 반환한다.
        return uri.getPath();
    }


    public void onSignupButtonClicked(View view) throws Exception {
        nickname = editTextNickname.getText().toString();
        userId = editTextId.getText().toString();
        pw1 = editTextPw1.getText().toString();
        pw2 = editTextPw2.getText().toString();
        email = editTextEmail.getText().toString();
        major = editTextMajor.getText().toString();


        if (nickname.isEmpty()) {
            Toast.makeText(getApplicationContext(), "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else if (userId.isEmpty()) {
            Toast.makeText(getApplicationContext(), "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else if (pw1.isEmpty() || pw2.isEmpty()) {
            Toast.makeText(getApplicationContext(), "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else if (!pw1.equals(pw2)) {
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않아요.", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(getApplicationContext(), "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else if (major.isEmpty()) {
            Toast.makeText(getApplicationContext(), "전공을 입력해주세요.", Toast.LENGTH_SHORT).show();
        } else {
            send_post();
        }
    }

    public void onCancelButtonClicked(View view) {
        super.onBackPressed();
    }


    public static void send_post() throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://13.125.196.191/api/user/create";
                String charset = "UTF-8";
                //File textFile = new File("C:\\asdf\\hello.jpg");
                //File binaryFile = new File(selectedImagePath);
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
                    writer.append("Content-Type: text/plain; charset=" + userId).append(CRLF);
                    writer.append(CRLF).append(userId).append(CRLF).flush();

                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"password\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + pw1).append(CRLF);
                    writer.append(CRLF).append(pw1).append(CRLF).flush();

                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"nickname\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + nickname).append(CRLF);
                    writer.append(CRLF).append(nickname).append(CRLF).flush();

                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"email\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + email).append(CRLF);
                    writer.append(CRLF).append(email).append(CRLF).flush();

                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"major\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + major).append(CRLF);
                    writer.append(CRLF).append(major).append(CRLF).flush();



            // Send binary file.
                    if (selectedImagePath != null) {
                        File binaryFile = new File(selectedImagePath);
                        writer.append("--" + boundary).append(CRLF);
                        writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
                        writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
                        writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                        writer.append(CRLF).flush();
                        //Files.copy(binaryFile.toPath(), output);
                        //output.flush(); // Important before continuing with writer!
                        writer.append(CRLF).flush(); // CRLF is important! It indicates end of boundary.
                    }
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
    }


};
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

        }

    }*/