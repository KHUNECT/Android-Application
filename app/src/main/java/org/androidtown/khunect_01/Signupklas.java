package org.androidtown.khunect_01;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Signupklas extends AppCompatActivity {

    private static int response_code = 0;

    private EditText Text_klasid;
    private EditText Text_klaspw;

    private static String userId = "";
    private static String klasId;
    private static String klasPassword;


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
            } else {
                Toast.makeText(getApplicationContext(), "알 수 없는 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        }
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
                    writer.append(CRLF).append(klasId).append(CRLF).flush();

                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"klasId\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + klasId).append(CRLF);
                    writer.append(CRLF).append(klasId).append(CRLF).flush();

                    writer.append("--" + boundary).append(CRLF);
                    writer.append("Content-Disposition: form-data; name=\"klasPassword\"").append(CRLF);
                    writer.append("Content-Type: text/plain; charset=" + klasPassword).append(CRLF);
                    writer.append(CRLF).append(klasPassword).append(CRLF).flush();

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
                    //response_code = responseCode;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response_code = responseCode;
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
};
