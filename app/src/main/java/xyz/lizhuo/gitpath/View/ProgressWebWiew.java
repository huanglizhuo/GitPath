package xyz.lizhuo.gitpath.View;

import android.content.Context;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import xyz.lizhuo.gitpath.R;

/**
 * Created by lizhuo on 16/3/26.
 */
public class ProgressWebWiew extends WebView {
    public interface UrlLoadingListener {
        void loading(String url);
    }

    private ProgressBar progressBar;
    private UrlLoadingListener urlLoadingListener;


    public ProgressWebWiew(Context context, AttributeSet attrs) {
        super(context, attrs);
        progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color));
        progressBar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 5, 0, 0));
        addView(progressBar);
        setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                if (urlLoadingListener != null) {
                    urlLoadingListener.loading(url);
                }
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setProgress(newProgress);
                    //progressBar.postDelayed(()->progressBar.setVisibility(GONE),200);
                } else {
                    if (progressBar.getVisibility() == GONE) {
                        progressBar.setVisibility(VISIBLE);
                    }
                    progressBar.setProgress(newProgress);
                }
            }
        });
    }

    public void setUrlLoadingListener(final UrlLoadingListener listener) {
        urlLoadingListener = listener;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressBar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressBar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }
}
