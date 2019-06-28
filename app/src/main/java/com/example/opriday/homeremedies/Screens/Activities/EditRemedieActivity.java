package com.example.opriday.homeremedies.Screens.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.opriday.homeremedies.Model.Respond;
import com.example.opriday.homeremedies.Network.IRetrofitRemedie;
import com.example.opriday.homeremedies.R;
import com.example.opriday.homeremedies.Network.RetrofitConstant;
import com.example.opriday.homeremedies.Utility.Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditRemedieActivity extends AppCompatActivity implements View.OnClickListener {

    EditText name,type,detail;
    ProgressBar progressBar;
    ImageView image;
    CoordinatorLayout coordinatorLayout;
    Button submit;
    IRetrofitRemedie remedieClient;
    String sTitle, sCategory, sDescription,id;
    Bundle getBundle;
    int IMAGE_CODE = 10001;
    Uri imageUri = null;
    String strBase64Encode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_remedie);
        getBundle = getIntent().getExtras();
        progressBar = (ProgressBar) findViewById(R.id.progressBar_editRemedie);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_editRemedie);
        image = (ImageView) findViewById(R.id.image_editRemedie);
        submit = (Button)findViewById(R.id.submit);
        name = (EditText) findViewById(R.id.name_editRemedie);
        type = (EditText) findViewById(R.id.type_editRemedie);
        detail = (EditText) findViewById(R.id.detail_editRemedie);
        submit.setOnClickListener(this);
        image.setOnClickListener(this);
        remedieClient = RetrofitConstant.getRetrofitRemedieClient();
        id = getBundle.getString(Constant.REMEDIE_ID);
        sTitle = getBundle.getString(Constant.TITLE);
        sCategory = getBundle.getString(Constant.CATEGORY);
        sDescription = getBundle.getString(Constant.DESCRIPTION);
        name.setText(sTitle);
        type.setText(sCategory);
        detail.setText(sDescription);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.submit) {
            progressBar.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
            String sTitle = name.getText().toString();
            String sCategory = type.getText().toString();
            String sDescription = detail.getText().toString();
            if (TextUtils.isEmpty(sTitle) && TextUtils.isEmpty(sCategory) && TextUtils.isEmpty(sDescription)) {
                Toast.makeText(EditRemedieActivity.this, "Enter complete detail!", Toast.LENGTH_SHORT).show();
            } else {
                UpdateRemedie(sTitle, sCategory, sDescription, id);
            }
        }else if (v.getId() == R.id.image_editRemedie){
            if (Constant.checkReadExternalStoragePermission(EditRemedieActivity.this)) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_CODE);
            } else {
                Toast.makeText(EditRemedieActivity.this, "Permission needed, to access poster image", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_CODE) {
                imageUri = data.getData();
                Log.e("ImageCompression",imageUri.toString());
                String getPathStr = Constant.getImagePath(EditRemedieActivity.this,imageUri);
                Log.e("ImageCompression", "Pickup image path:" + getPathStr);
                Bitmap bitmap = BitmapFactory.decodeFile(getPathStr);
                image.setImageBitmap(bitmap);
                strBase64Encode = Constant.getBase64EncodedString(bitmap);
            }
        }
    }

    private void UpdateRemedie(String title, String category, String description, String id) {
        remedieClient.updateRemedie(title,description,category,id).enqueue(new Callback<Respond>() {
            @Override
            public void onResponse(Call<Respond> call, Response<Respond> response) {
                Respond res = response.body();
                if (res != null) {
                    Log.e("EditRemedieActivity", res.getMessage());
                    if (res.getStatus().contains("success")) {
                        Log.e("EditRemedieActivity", res.getStatus());
                        onSnackBar(res.getMessage(), Color.GREEN);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(EditRemedieActivity.this, HomeRemedieActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        }, 2000);
                    }
                }
            }

            @Override
            public void onFailure(Call<Respond> call, Throwable t) {

            }
        });
    }

    public void onSnackBar(String message, int color){
        progressBar.setVisibility(View.GONE);
        submit.setVisibility(View.VISIBLE);
        Snackbar snackbar  = Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_SHORT);
        View view = snackbar.getView();
        TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
        tv.setTextColor(color);
        snackbar.show();
    }
}