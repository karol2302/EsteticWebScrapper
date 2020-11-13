package com.example.esteticscrapper;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NextPage extends Activity {
    LinearLayout ll;
    TextView tv;
    Intent i;
    String url;

    private WebView web;


    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        ll = new LinearLayout(this);
        tv = new TextView(this);
        i = getIntent();
        url = i.getStringExtra("url");

        tv.setText("Url =" + url);
        web = new WebView(this);
        web.setWebViewClient(new myWebClient());
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(url);

        ll.addView(web);
        ll.addView(tv);

        setContentView(ll);

    }

    public class myWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }
}