package com.example.opriday.homeremedies.Network;

import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Model.ReviewData;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IRetrofitReviews {


    @FormUrlEncoded
    @POST("review/create")
    Call<Respond> createReview(@Field("user_id") String user_id,
                             @Field("remedie_id") String remedie_id,
                             @Field("rating") String rating,
                             @Field("description") String description);


    @GET("reviews")
    Call<ReviewData> remedies();
}
