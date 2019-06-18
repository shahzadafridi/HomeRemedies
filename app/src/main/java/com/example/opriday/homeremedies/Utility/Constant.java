package com.example.opriday.homeremedies.Utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.example.opriday.homeremedies.Screens.Activities.MainActivity;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;

public class Constant {


    //Common
    public static String CREATED_AT="created_at";

    //SharePrefrence/login
    public static String LOGIN_SESSION="login";
    public static String IS_LOGIN = "status";
    public static String ID="id";
    public static String NAME="name";
    public static String EMAIL="email";
    public static String ROLE="role";

    //Remedie
    public static String REMEDIE_ID = "remedie_id";
    public static String USER_ID = "user_id";
    public static String USER_NAME="user_name";
    public static String TITLE="title";
    public static String TYPE="type";
    public static String CATEGORY="category";
    public static String DESCRIPTION="description";
    public static String PICTURE="picture";
    public static int READ_EXTERNAL_STORAGE_CODE=103;

    public static String getUserDetail(Context context,String key){
        SharedPreferences sharedPreferences = SharedPrefManager.getCustomSharedPreferences(context, Constant.LOGIN_SESSION, context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key,null);
        return value;
    }

    public static boolean isAdmin(Context context){
       String  getUserType = Constant.getUserDetail(context,Constant.ROLE);
        if(!TextUtils.isEmpty(getUserType)) {
            if (getUserType.contentEquals("admin")){
                return true;
            }
        }
        return false;
    }

    public static String getImagePath(Context context, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String getBase64EncodedString(Bitmap bitmap) {
        if (bitmap == null){
            Log.e("ImageCompression","bitmap is null.");
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); //compress to which format you want.
        byte[] byte_arr = stream.toByteArray();
        String image_str = Base64.encodeToString(byte_arr, Base64.DEFAULT);
        return image_str;
    }

    public static boolean checkReadExternalStoragePermission(Context context){
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            requestReadExternalStoragePermission(context);
            return false;
        }
    }

    public static void requestReadExternalStoragePermission(Context context){
        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_CODE);
    }
}
