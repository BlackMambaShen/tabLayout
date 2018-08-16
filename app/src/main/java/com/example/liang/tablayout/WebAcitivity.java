package com.example.liang.tablayout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebAcitivity extends AppCompatActivity {

    private WebView wb_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_acitivity);
         wb_content = (WebView) findViewById(R.id.wb_content);
        wb_content.loadUrl("https://weibo.com/");
        WebSettings settings = wb_content.getSettings();
        settings.setJavaScriptEnabled(true);
        wb_content.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl("www.fcw51.com");
                return true;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&wb_content.canGoBack()){
            wb_content.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            wb_content.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
