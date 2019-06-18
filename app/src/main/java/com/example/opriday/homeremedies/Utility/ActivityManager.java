package com.example.opriday.homeremedies.Utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.opriday.homeremedies.Screens.Activities.DetailActivity;
import com.example.opriday.homeremedies.Screens.Activities.EditRemedieActivity;
import com.example.opriday.homeremedies.Screens.Activities.LoginActivity;
import com.example.opriday.homeremedies.Screens.Activities.MainActivity;
import com.example.opriday.homeremedies.Screens.Activities.ReviewActivity;
import com.example.opriday.homeremedies.Screens.Activities.SplashScreenActivity;

public class ActivityManager {

    public static void goToMainActivity(Context context){
        Intent main = new Intent(context, MainActivity.class);
        context.startActivity(main);
    }

    public static void goToStartActivity(Context context){
        Intent main = new Intent(context, SplashScreenActivity.class);
        main.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(main);
    }

    public static void goToLoginActivity(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public static void goToDetailActivity(Context context, Bundle bundle) {
        Intent i = new Intent(context, DetailActivity.class);
        i.putExtras(bundle);
        context.startActivity(i);
    }

    public static void goToReviewActivity(Context context, Bundle bundle) {
        Intent i = new Intent(context, ReviewActivity.class);
        i.putExtras(bundle);
        context.startActivity(i);
    }

    public static void goToEditActivity(Context context, Bundle bundle) {
        Intent i = new Intent(context, EditRemedieActivity.class);
        i.putExtras(bundle);
        context.startActivity(i);
    }
}
