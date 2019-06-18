package com.example.opriday.homeremedies.Network;

import com.example.opriday.homeremedies.Model.RemedieData;
import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Model.TipData;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRetrofitTip {

    @GET("tips")
    Call<TipData> tips();

    @FormUrlEncoded
    @POST("tip/create")
    Call<Respond> insertTip(@Field("user_id") String user_id,
                                @Field("title") String title,
                                @Field("description") String description,
                                @Field("picture") String picture);

    @FormUrlEncoded
    @POST("tip/update")
    Call<Respond> updateTip(@Field("title") String title,
                                @Field("description") String description,
                                @Field("id") String id);

    @FormUrlEncoded
    @POST("tip/search/by")
    Call<Respond> serachBy(@Field("key") String key, @Field("value") String value);

    @DELETE("tip/delete")
    Call<Respond> deleteTip(@Query("id") String id);

}
