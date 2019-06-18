package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Network.IRetrofitTip;
import com.example.opriday.homeremedies.Network.RetrofitConstant;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Utility.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewTipActivity extends AppCompatActivity implements View.OnClickListener, ListView.OnItemClickListener {

    EditText title,description;
    CoordinatorLayout coordinatorLayout;
    ProgressBar progressBar;
    ImageView imageView;
    Button post;
    int IMAGE_CODE = 10001;
    Uri imageUri = null;
    String strBase64Encode = "";
    String TAG = "NewTipActivity";
    IRetrofitTip tipClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tip);
        title = (EditText) findViewById(R.id.newtip_title);
        description = (EditText)findViewById(R.id.newtip_description);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_newtip);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.newtip_coordinatorlayout);
        imageView = (ImageView) findViewById(R.id.newtip_image);
        post = (Button) findViewById(R.id.newtip_post);
        post.setOnClickListener(this);
        imageView.setOnClickListener(this);
        tipClient = RetrofitConstant.getRetrofitTipClient();
    }

    @Override
    public void onClick(View v) {
        if (R.id.newtip_image == v.getId()){
            if (Constant.checkReadExternalStoragePermission(NewTipActivity.this)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_CODE);
            } else {
                Toast.makeText(NewTipActivity.this, "Permission needed, to access poster image", Toast.LENGTH_LONG).show();
            }
        }else if (R.id.newtip_post == v.getId()){
            progressBar.setVisibility(View.VISIBLE);
            post.setVisibility(View.GONE);
            String getTitle = title.getText().toString();
            String getDescription = description.getText().toString();
            String user_id = Constant.getUserDetail(NewTipActivity.this,"id");
            if (TextUtils.isEmpty(getTitle) && TextUtils.isEmpty(getDescription)){
                Toast.makeText(NewTipActivity.this,"Enter complete detail!",Toast.LENGTH_SHORT).show();
            }else {
                postTip(user_id,getTitle,getDescription,strBase64Encode);
            }
        }
    }


    private void postTip(String user_id, String getTitle, String getDescription, String base64EncodedString) {
        Log.e(TAG,base64EncodedString);
        tipClient.insertTip(user_id,getTitle,getDescription,base64EncodedString).enqueue(new Callback<Respond>() {
            @Override
            public void onResponse(Call<Respond> call, Response<Respond> response) {
                Respond res = response.body();
                if (res != null) {
                    Log.e(TAG, res.getMessage());
                    if (res.getStatus().contains("success")) {
                        Log.e(TAG, res.getStatus());
                        onSnackBar(res.getMessage(), Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(NewTipActivity.this, TipActivity.class);
                                startActivity(intent);
                            }
                        }, 600);
                    }
                }
            }

            @Override
            public void onFailure(Call<Respond> call, Throwable t) {
                Log.e(TAG,t.getMessage());
                onSnackBar(t.getMessage(), Color.GREEN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_CODE) {
                imageUri = data.getData();
                Log.e("ImageCompression",imageUri.toString());
                String getPathStr = Constant.getImagePath(NewTipActivity.this,imageUri);
                Log.e("ImageCompression", "Pickup image path:" + getPathStr);
                Bitmap bitmap = BitmapFactory.decodeFile(getPathStr);
                imageView.setImageBitmap(bitmap);
                strBase64Encode = Constant.getBase64EncodedString(bitmap);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        switch (requestCode) {
            case 103: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e(TAG, "permission granted");
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, IMAGE_CODE);
                } else {
                    Log.e(TAG, "permission denied");
                    Toast.makeText(this, "Permission needed, to access poster image", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }


    public void onSnackBar(String message, int color){
        progressBar.setVisibility(View.GONE);
        post.setVisibility(View.VISIBLE);
        Snackbar snackbar  = Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
