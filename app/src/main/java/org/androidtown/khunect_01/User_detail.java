package org.androidtown.khunect_01;

public class User_detail {
    public static String userId = "";
    public static String nickname = "";
    public static String major = "";
    public static String email= "";
    public static String Image= "";


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


