package com.example.pdarb.android_project_showcase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.view.View;
import android.widget.TextView;

public class Resume extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resume);
        WebView myWebView = (WebView) findViewById(R.id.webview);
        TextView noLink = (TextView) findViewById(R.id.no_link);


        Bundle args = getIntent().getExtras();
        String pdfLink = "";
        if(args != null){
            pdfLink = args.getString("link");
            myWebView.loadUrl("http://www.google.com");
        }
        else{
            myWebView.setVisibility(View.GONE);
            noLink.setVisibility(View.VISIBLE);
        }

    }
}
