package com.example.opriday.homeremedies.Remedies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.opriday.homeremedies.R;

public class Detail extends AppCompatActivity {
    TextView remedieDetail;
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        webView = (WebView) findViewById(R.id.webview);
        webView.loadUrl("file:///android_asset/homeremedies.html");
//        remedieDetail = (TextView) findViewById(R.id.remedie_detail);
//        remedieDetail.setText(getString(R.string.rem_detail));


    }
}
