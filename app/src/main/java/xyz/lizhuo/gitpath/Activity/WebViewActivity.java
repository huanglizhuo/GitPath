package xyz.lizhuo.gitpath.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.Bind;
import butterknife.ButterKnife;
import xyz.lizhuo.gitpath.R;

public class WebViewActivity extends AppCompatActivity {

    @Bind(R.id.content_web)
    WebView mContentWeb;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra("url");

        mContentWeb.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });
        mContentWeb.loadUrl(url);
    }

}
