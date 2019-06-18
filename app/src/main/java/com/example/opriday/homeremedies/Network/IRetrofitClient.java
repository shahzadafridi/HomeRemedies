package com.example.opriday.homeremedies.Network;

import com.example.opriday.homeremedies.Model.Remedies;
import com.example.opriday.homeremedies.Model.Respond;

import com.example.opriday.homeremedies.Model.Youtube.PlayList;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Opriday on 12/23/2018.
 */

public interface IRetrofitClient {

    @GET("user_login.php/")
    Call<Respond> getSession(@Query("email") String email, @Query("password") String password, @Query("user_type") String userType);

    @GET("user_registration.php/")
    Call<Respond> registerUser(@Query("username") String username, @Query("email") String email, @Query("password") String password);

    @GET("remedies.php/")
    Call<Remedies> getRemediesList(@Query("request_type") String request_type);

    @GET("remedies.php/")
    Call<Respond> addRemedie(@Query("request_type") String request_type,@Query("name") String name, @Query("type") String type, @Query("detail") String detail,@Query("posted_by") String posted_by);

    @GET("remedies.php/")
    Call<Respond> updateRemedie(@Query("request_type") String request_type,@Query("name") String name, @Query("type") String type, @Query("detail") String detail, @Query("id") String id);

    @GET("remedies.php/")
    Call<Respond> deleteRemedie(@Query("request_type") String request_type,@Query("id") String id);

//    @GET("youtube/v3/playlistItems")
//    Call<PlayListVideoInfo> getYoutubePlayListResponse(@Query("part") String part, @Query("playlistId") String playListId, @Query("key") String key, @Query("maxResults") String maxResult);

    @GET("playlistItems")
    Call<PlayList> getYoutubePlayListsResponse(@Query("part") String part, @Query("playlistId") String playlistId, @Query("key") String key, @Query("maxResults") String maxResult);


}
