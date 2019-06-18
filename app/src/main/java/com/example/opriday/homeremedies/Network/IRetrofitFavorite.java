package com.example.opriday.homeremedies.Network;

import com.example.opriday.homeremedies.Model.FavoriteData;
import com.example.opriday.homeremedies.Model.RemedieData;
import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Model.ReviewData;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IRetrofitFavorite {


    @GET("favorites")
    Call<FavoriteData> favoriteRemedies(@Query("user_id") String user_id);


    @FormUrlEncoded
    @POST("favorite/create")
    Call<Respond> createfavoriteRemedie(@Field("user_id") String user_id,
                               @Field("remedie_id") String remedie_id);



    @DELETE("favorite/delete")
    Call<Respond> deleteFavoriteRemedie(@Query("id") String id);

}
