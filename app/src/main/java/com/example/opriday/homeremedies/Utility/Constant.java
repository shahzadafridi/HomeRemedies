package com.example.opriday.homeremedies.Utility;

import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.Network.RetrofitClient;

/**
 * Created by Opriday on 12/23/2018.
 */

public class Constant {
    //Network
   // public static String  BASE_URL = "http://192.168.43.156/";//huwaie mate10 lite
    public static String BASE_URL="http://172.16.113.238/homeremedies/";//waqas
    public static IRetrofitClient getService(){
        return RetrofitClient.getClient(BASE_URL).create(IRetrofitClient.class);
    }
    //youtube
    public static String  BASE_URL_YOUTUBE = "https://www.googleapis.com/";
    public static IRetrofitClient getYoutubeService(){
        return RetrofitClient.getClient(BASE_URL_YOUTUBE).create(IRetrofitClient.class);
    }
    //SharePrefrence/login
    public static String LOGIN_SESSION="login";
    public static String USER_NAME="user_name";
    public static String LOGIN_SESSION_STATUS = "status";
    public static String USER_TYPE = "type";
}
