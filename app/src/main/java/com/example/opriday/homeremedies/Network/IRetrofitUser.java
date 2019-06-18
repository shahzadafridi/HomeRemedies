package com.example.opriday.homeremedies.Network;

import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Model.UserData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IRetrofitUser {



    @FormUrlEncoded
    @POST("user/create")
    Call<Respond> createUser(@Field("name") String name,
                                          @Field("email") String email,
                                          @Field("password") String password,
                                          @Field("role") String role);

    @FormUrlEncoded
    @POST("user/login")
    Call<UserData> login(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("user/search/by")
    Call<UserData> searchBy(@Field("key") String key, @Field("value") String value);

    @FormUrlEncoded
    @POST("user/update")
    Call<Respond> updateName(@Field("user_id") String user_id , @Field("name") String name);

    @FormUrlEncoded
    @POST("user/update")
    Call<Respond> updateEmail(@Field("user_id") String user_id , @Field("name") String name);

    @FormUrlEncoded
    @POST("user/update")
    Call<Respond> updateRole(@Field("user_id") String user_id , @Field("name") String name);


//    @FormUrlEncoded
//    @POST("customers")
//    Call<Respond> updatePassword(@Field("password") String password);

}
