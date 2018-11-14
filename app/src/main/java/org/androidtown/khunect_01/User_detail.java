package org.androidtown.khunect_01;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class User_detail {
    public static String userId = "";
    public static String nickname = "";
    public static String major = "";
    public static String email= "";
    public static String Image= "";
    public static String ObjectId = "";

    public static KLAS_detail klas_arr;
    public static void set_klasdetail(JSONObject jobj)
    {

    }

    public static void update_user()
    {
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

                    /*int responseCode = con.getResponseCode();
                    response_code = responseCode;
                    //System.out.println("\nSending 'GET' request to URL : " + url);
                    Log.i("Response Code",""+responseCode);*/
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
        /*try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    public static void set_objectId(String obj){
        ObjectId = obj;
    }

    public static void set_user(String userid, String nick, String Major, String Email, String _image)
    {
        userId = userid;
        nickname = nick;
        major = Major;
        email = Email;
        Image = _image;

    }

    public static void logout()
    {
        userId = "";
        nickname = "";
        major = "";
        email = "";
        Image = "";

    }

}


