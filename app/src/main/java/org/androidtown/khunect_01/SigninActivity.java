package org.androidtown.khunect_01;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SigninActivity extends AppCompatActivity {

    private EditText editNickname;
    private EditText editTextId;
    private EditText editTextPw1;
    private EditText editTextPw2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        editNickname = (EditText) findViewById((R.id.text_userNickname));
        editTextId = (EditText) findViewById(R.id.text_userID);
        editTextPw1 = (EditText) findViewById(R.id.text_userPassword1);
        editTextPw2 = (EditText) findViewById(R.id.text_userPassword2);
    }

    public void onSignupButtonClicked(View view) {
        String nickname = editNickname.getText().toString();
        String Id = editTextId.getText().toString();
        String Pw1 = editTextPw1.getText().toString();
        String Pw2 = editTextPw2.getText().toString();

        if (nickname.isEmpty())
        {
            Toast.makeText(this, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }

        else if (Id.isEmpty())
        {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }

        else if (Pw1.isEmpty())
        {
            Toast.makeText(this, "패스워드를 입력해주세요.", Toast.LENGTH_SHORT).show();
        }

        else if (Pw1 != Pw2)
        {
            Toast.makeText(this, "비밀번호가 일치하지 않아요.", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //inserttoToDataBase(nickname, Id, Pw1);
        }

    }

    public void onCancelButtonClicked(View view) {
        super.onBackPressed();
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
