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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;


public class PostingActivity extends AppCompatActivity {

    private static int response_code= 0;

    private static String userId = "testId01";

    private static String boardid; //게시판 ID
    private static String boardName;
    private static String title; //게시글 제목
    private static String context; //게시글 내용
    private static String writerId; //작성자 아이디
    private static String selectedImagePath = null; //업로드 이미지 path

    private EditText editText_title;
    private EditText editText_context;
    private ImageView imageview_upload;
    private ImageButton button_deleteimage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posting);

        editText_title = (EditText)findViewById(R.id.text_title);
        editText_context = (EditText)findViewById(R.id.text_context);
        imageview_upload = (ImageView)findViewById(R.id.imageView_image);
        button_deleteimage = (ImageButton)findViewById(R.id.imageButton_deleteImage);


        //게시판 이름 설정
        TextView text_boardname = (TextView)findViewById(R.id.textView_boardName);

        Intent intent = getIntent(); /*데이터 수신*/
        boardid = intent.getExtras().getString("boardid"); /*String형*/


        switch (boardid){
            case "study" : boardName = "스터디 게시판"; break;
            case "hobby" : boardName = "취미 게시판"; break;
            case "club" : boardName = "동아리 게시판";break;
            case "alba" : boardName = "알바 게시판"; break;
            case "contest": boardName = "공모전 게시판"; break;
            case "market": boardName = "거래 게시판"; break;
            case "gonggu": boardName = "공구 게시판"; break;
            default: boardName = "학수번호 게시판";//학수번호 api get으로 과목명 가져오기
        }
        text_boardname.setText(boardName);
        //# 게시판 이름설정
    }

    public void onSubmitButtonClicked(View view) throws Exception {
        title = editText_title.getText().toString();
        context = editText_context.getText().toString();

        sendpost();
        if(response_code == 200)
        {
            Toast.makeText(getApplicationContext(), "게시물이 등록되었습니다.", Toast.LENGTH_SHORT).show();

            // 게시판 새로고침

            //onBackPressed();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void sendpost() throws Exception {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://13.125.196.191/api/post/create";
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
                    writer.append("Content-Disposition: form-data; name=\"writerId\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + userId).append(CRLF);
                    writer.append(CRLF).append(userId).append(CRLF).flush();

                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"title\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + title).append(CRLF);
                    writer.append(CRLF).append(title).append(CRLF).flush();

                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"context\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + context).append(CRLF);
                    writer.append(CRLF).append(context).append(CRLF).flush();

                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"boardId\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + boardid).append(CRLF);
                    writer.append(CRLF).append(boardid).append(CRLF).flush();


                    // Send binary file.
                    if (selectedImagePath != null) {
                        File binaryFile = new File(selectedImagePath);

                        writer.append("--" + boundary).append(CRLF);
                        writer.append("Content-Disposition: form-data; name=\"image\"; filename=\"" + binaryFile.getName() + "\"").append(CRLF);
                        writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(binaryFile.getName())).append(CRLF);
                        writer.append("Content-Transfer-Encoding: binary").append(CRLF);
                        writer.append(CRLF).flush();
                        Files.copy(binaryFile.toPath(), output);
                        output.flush(); // Important before continuing with writer!
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


    private int PICK_IMAGE_REQUEST = 1;
    private static final int SELECT_PICTURE = 1;

    public void onUploadImageButtonClicked(View view) {
        Toast.makeText(getApplicationContext(), "이미지 업로드 버튼이 눌렸어요.", Toast.LENGTH_SHORT).show();

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
                            imageview_upload.setImageBitmap(img);
                            imageview_upload.setVisibility(View.VISIBLE);

                            button_deleteimage.setVisibility(View.VISIBLE);

                            Uri selectedImageUri = data.getData();
                            selectedImagePath = getPath(selectedImageUri);
                        }
                    }

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

    public void onDeleteImageButtonClicked(View view){
        Toast.makeText(getApplicationContext(), "이미지 삭제 버튼이 눌렸어요", Toast.LENGTH_SHORT).show();
        selectedImagePath = null;
        imageview_upload.setVisibility(View.INVISIBLE);
        button_deleteimage.setVisibility(View.INVISIBLE);
    }

};
