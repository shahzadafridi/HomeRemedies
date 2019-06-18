package com.example.opriday.homeremedies.Screens.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Model.Review;
import com.example.opriday.homeremedies.Model.ReviewData;
import com.example.opriday.homeremedies.Network.IRetrofitReviews;
import com.example.opriday.homeremedies.Network.RetrofitConstant;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Screens.Adapters.ReviewAdapter;
import com.example.opriday.homeremedies.Utility.Constant;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewActivity extends AppCompatActivity implements View.OnClickListener {

    RatingBar ratingBar;
    EditText description;
    ListView listView;
    Button post;
    IRetrofitReviews reviewsClient;
    List<Review> list;
    Bundle getBundle;
    String TAG = "ReviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ratingBar = (RatingBar) findViewById(R.id.rattingBar_review);
        description = (EditText) findViewById(R.id.description_review);
        listView = (ListView) findViewById(R.id.listView_review);
        post = (Button) findViewById(R.id.post_review);
        reviewsClient = RetrofitConstant.getRetrofitReviewClient();
        ratingBar.setOnClickListener(this);
        post.setOnClickListener(this);
        getReviews();
        getBundle = getIntent().getExtras();
    }

    private void getReviews() {
        reviewsClient.remedies().enqueue(new Callback<ReviewData>() {
            @Override
            public void onResponse(Call<ReviewData> call, Response<ReviewData> response) {
                ReviewData data = response.body();
                if (data != null){
                    list = data.getData();
                    ReviewAdapter customAdapter = new ReviewAdapter(ReviewActivity.this,R.layout.review_layout,list);
                    listView.setAdapter(customAdapter);
                }
            }

            @Override
            public void onFailure(Call<ReviewData> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.post_review){
            String getRating = "" + ratingBar.getRating();
            String getDescription = description.getText().toString();
            String getRemedieId = getBundle.getString((Constant.REMEDIE_ID));
            String getUserId = getBundle.getString(Constant.USER_ID);
            reviewsClient.createReview(getUserId,getRemedieId,getRating,getDescription).enqueue(new Callback<Respond>() {
                @Override
                public void onResponse(Call<Respond> call, Response<Respond> response) {
                    Respond respond = response.body();
                    if (respond.getStatus().contentEquals("success")){
                        Log.e(TAG,"Review added.");
                        Toast.makeText(ReviewActivity.this,"Review added.",Toast.LENGTH_LONG).show();
                        getReviews();
                    }else {
                        Log.e(TAG,"Review not added.");
                    }
                }

                @Override
                public void onFailure(Call<Respond> call, Throwable t) {

                }
            });
        }
    }
}
