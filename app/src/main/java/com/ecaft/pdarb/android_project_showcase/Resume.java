package com.ecaft.pdarb.android_project_showcase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.view.View;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class Resume extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        WebViewClient myWebViewClient = new WebViewClient();
        myWebView.setWebViewClient(myWebViewClient);
        myWebView.getSettings().setDomStorageEnabled(true);
        myWebView.getSettings().setJavaScriptEnabled(true);
        TextView noLink = (TextView) findViewById(R.id.no_link);


        Bundle args = getIntent().getExtras();
        if(args != null && !args.getString("link").equals("")){
            myWebView.loadUrl(args.getString("link"));
        }
        else{
            myWebView.setVisibility(View.GONE);
            noLink.setVisibility(View.VISIBLE);
        }

    }
}
