package org.androidtown.khunect_01;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PostActivity extends AppCompatActivity {
    private static int response_code = 0;

    private static EditText edit_comment;

    private static TextView textView_boardname; //게시판이름
    private static TextView textView_nickname; //작성자닉네임
    private static TextView textView_title; //게시글 제목
    private static TextView textView_context; //게시글 내용
    private static ImageView profile; //작성자 프로필
    private static ImageView imageView; //게시글사진

    private static ListView listview; //댓글
    private ListViewAdapter adapter;

    private static String boardId; //게시판 id (학수번호 또는 study 이런것)
    private static String _id; //게시글 아이디
    private static String boardName;
    private static String title;
    private static String nickname;

    private static String imgurl; // 게시글 사진 경로

    private static JSONObject jobj; //postId로 불러온 게시글 세부내용

    private Bitmap img = null;
    private Bitmap img2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        edit_comment = (EditText)findViewById(R.id.editText_comment);
        textView_boardname = (TextView) findViewById(R.id.text_boardName);
        textView_context = (TextView) findViewById(R.id.text_context);
        textView_nickname = (TextView) findViewById(R.id.text_writernick);
        textView_title = (TextView) findViewById(R.id.text_title);
        profile = (ImageView) findViewById(R.id.imageView_writerprofile);
        imageView = (ImageView) findViewById(R.id.imageView_postimage);
        imageView.setVisibility(View.INVISIBLE);


        Intent intent = getIntent();
        _id = intent.getExtras().getString("_id");
        boardName = intent.getExtras().getString("boardName");
        title = intent.getExtras().getString("title");
        nickname = intent.getExtras().getString("writerNickname");

        try {
            sendget();

            if (response_code != 200) {
                Toast.makeText(getApplicationContext(), "게시글 불러오기에 실패했습니다.", Toast.LENGTH_SHORT).show();
                super.onBackPressed();
            }
            textView_boardname.setText(boardName);
            textView_title.setText(title);
            textView_nickname.setText(nickname);
            textView_context.setText(jobj.getString("postContext"));
            imgurl = jobj.getString("postImages");
            imgurl = imgurl.substring(2, imgurl.length() - 2);
            imgurl = imgurl.replace("\\","");

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    String urldisplay = User_detail.Image;
                    Bitmap mIcon11 = null;
                    try {
                        InputStream in = new java.net.URL(urldisplay).openStream();
                        mIcon11 = BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }
                    img = mIcon11;
                }
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            profile.setImageBitmap(img);

            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String urldisplay = imgurl;
                        Bitmap mIcon11 = null;

                        InputStream in = new java.net.URL(urldisplay).openStream();
                        mIcon11 = BitmapFactory.decodeStream(in);

                        img2 = mIcon11;
                        imageView.setVisibility(View.VISIBLE);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            thread.join();
            imageView.setImageBitmap(img2);


            // Adapter 생성
            adapter = new ListViewAdapter();
            //댓글
            JSONArray jarr = new JSONArray(jobj.getJSONArray("postComments").toString());
            listview = (ListView) findViewById(R.id.listview_comment);
            listview.setAdapter(adapter);

            for (int i = 0; i < jarr.length(); i++) {
                JSONObject job = jarr.getJSONObject(i);
                adapter.addItem("", job.getString("context"), job.getString("writerNickname"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    //게시글의 디테일 가져오기
    public void sendget() throws IOException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    URL obj = new URL("http://13.125.196.191/api/post/detail/"+_id);
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

                    jobj = new JSONObject(response.toString());



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

    public void onCommentClicked(View view) throws InterruptedException {
        send_commentpost();

    }

    public void send_commentpost() throws InterruptedException {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://13.125.196.191/api/post/addComment");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setDefaultUseCaches(false);
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    StringBuffer sb = new StringBuffer();
                    sb.append("userId").append("=").append(User_detail.ObjectId).append('&');
                    sb.append("postId").append("=").append(_id).append('&');
                    sb.append("context").append("=").append(edit_comment.getText().toString());

                    PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"UTF-8"));
                    pw.write(sb.toString());
                    pw.flush();

                    BufferedReader bf = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
                    StringBuilder buff = new StringBuilder();
                    String line;
                    while((line = bf.readLine())!=null){
                        buff.append(line);
                    }
                    Log.i("Response",line);
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
        update_comment();
    }

    private void update_comment() {
        try {
            sendget();
            // Adapter 생성
            adapter = new ListViewAdapter();
            //댓글
            JSONArray jarr = new JSONArray(jobj.getJSONArray("postComments").toString());
            listview = (ListView) findViewById(R.id.listview_comment);
            listview.setAdapter(adapter);

            for (int i = 0; i < jarr.length(); i++) {
                JSONObject job = jarr.getJSONObject(i);
                adapter.addItem("", job.getString("context"), job.getString("writerNickname"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
