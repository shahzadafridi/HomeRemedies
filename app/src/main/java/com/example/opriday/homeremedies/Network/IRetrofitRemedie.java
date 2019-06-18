package com.example.opriday.homeremedies.Network;

import com.example.opriday.homeremedies.Model.RemedieData;
import com.example.opriday.homeremedies.Model.Remedies;
import com.example.opriday.homeremedies.Model.Respond;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRetrofitRemedie {


    @GET("remedies/type")
    Call<RemedieData> remedies(@Query("type") String type);

    @FormUrlEncoded
    @POST("remedie/create")
    Call<Respond> insertRemedie(@Field("user_id") String user_id,
                                @Field("user_name") String user_name,
                                @Field("title") String title,
                                @Field("description") String description,
                                @Field("category") String category,
                                @Field("type") String type,
                                @Field("picture") String picture);

    @FormUrlEncoded
    @POST("remedie/update")
    Call<Respond> updateRemedie(@Field("title") String title,
                              @Field("description") String description,
                              @Field("category") String category,
                              @Field("id") String id);


    @FormUrlEncoded
    @POST("remedie/update")
    Call<Respond> updateTitle(@Field("id") String id, @Field("title") String title);

    @FormUrlEncoded
    @POST("remedie/update")
    Call<Respond> updateDescription(@Field("id") String id, @Field("description") String title);

    @FormUrlEncoded
    @POST("remedie/update")
    Call<Respond> updateCategory(@Field("id") String id, @Field("category") String title);

    @FormUrlEncoded
    @POST("remedie/update")
    Call<Respond> updateType(@Field("id") String id, @Field("type") String title);

    @FormUrlEncoded
    @POST("remedie/search/by")
    Call<Respond> serachBy(@Field("key") String key, @Field("value") String value);

    @DELETE("remedie/delete")
    Call<Respond> deleteRemedie(@Query("id") String id);
}
