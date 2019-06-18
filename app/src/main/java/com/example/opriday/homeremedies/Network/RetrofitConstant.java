package com.example.opriday.homeremedies.Network;

import com.example.opriday.homeremedies.Network.IRetrofitClient;
import com.example.opriday.homeremedies.Network.IRetrofitRemedie;
import com.example.opriday.homeremedies.Network.IRetrofitUser;
import com.example.opriday.homeremedies.Network.RetrofitClient;

/**
 * Created by Opriday on 12/23/2018.
 */

public class RetrofitConstant {
    //Network
   // public static String  BASE_URL = "http://192.168.43.156/";//huwaie mate10 lite
    public static String BASE_URL="https://homeremediesandnaturalcares.000webhostapp.com/";//waqas

    public static IRetrofitClient getService(){
        return RetrofitClient.getClient(BASE_URL).create(IRetrofitClient.class);
    }

    public static IRetrofitUser getRetrofitUserClient(){
        return RetrofitClient.getClient(BASE_URL).create(IRetrofitUser.class);
    }

    public static IRetrofitRemedie getRetrofitRemedieClient(){
        return RetrofitClient.getClient(BASE_URL).create(IRetrofitRemedie.class);
    }

    public static IRetrofitReviews getRetrofitReviewClient(){
        return RetrofitClient.getClient(BASE_URL).create(IRetrofitReviews.class);
    }

    public static IRetrofitFavorite getRetrofitFavoirteClient(){
        return RetrofitClient.getClient(BASE_URL).create(IRetrofitFavorite.class);
    }

    public static IRetrofitTip getRetrofitTipClient(){
        return RetrofitClient.getClient(BASE_URL).create(IRetrofitTip.class);
    }
    //youtube
    public static String  BASE_URL_YOUTUBE = " https://www.googleapis.com/youtube/v3/";
    public static IRetrofitClient getYoutubeService(){
        return RetrofitClient.getClient(BASE_URL_YOUTUBE).create(IRetrofitClient.class);
    }

}
