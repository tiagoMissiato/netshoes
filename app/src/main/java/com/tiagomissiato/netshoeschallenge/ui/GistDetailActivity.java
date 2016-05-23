package com.tiagomissiato.netshoeschallenge.ui;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tiagomissiato.netshoeschallenge.R;
import com.tiagomissiato.netshoeschallenge.models.GistItem;
import com.tiagomissiato.netshoeschallenge.utils.RoundedCornersTransformation;

import butterknife.Bind;
import butterknife.ButterKnife;

public class GistDetailActivity extends AppCompatActivity {

    public static final String BUNDLE_GIST = "com.tiagomissiato.netshoeschallenge.ui.GistDetailActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.scroll_news_detail)
    ScrollView mScrollDetail;
    @Bind(R.id.web_view_news_detail)
    WebView mWebViewDetail;
    @Bind(R.id.gist_owner)
    TextView mGistOwner;
    @Bind(R.id.gist_image)
    ImageView mGistImage;
    @Bind(R.id.header)
    LinearLayout mHeader;

    float mStartImageY;

    GistItem mGist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gist_detail);

        ButterKnife.bind(this);

        configureToolbar();

        Bundle extras = getIntent().getExtras();
        if(extras == null) finish();

        mGist = (GistItem) extras.getSerializable(BUNDLE_GIST);

        if(mGist == null) finish();

        String ownerName = getString(R.string.anonymous);
        if(mGist.owner != null) {
            ownerName = mGist.owner.login;

            if(!TextUtils.isEmpty(mGist.owner.avatar)) {
                Glide.with(this)
                        .load(mGist.owner.avatar)
                        .centerCrop()
                        .bitmapTransform(new RoundedCornersTransformation(this, 30, 0, RoundedCornersTransformation.CornerType.ALL))
                        .into(mGistImage);
            }
        }
        mGistOwner.setText(getString(R.string.gist_owner_file, ownerName, mGist.gist.filename));

        mScrollDetail.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                mScrollDetail.getViewTreeObserver().removeOnPreDrawListener(this);
                mStartImageY = mHeader.getY();
                mScrollDetail.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        int scrollY = mScrollDetail.getScrollY() - toolbar.getHeight();
                        mHeader.setY((-scrollY + mStartImageY) * 0.5f);
                    }
                });

                return false;
            }
        });

        mWebViewDetail.getSettings().setJavaScriptEnabled(true);
        mWebViewDetail.loadUrl(mGist.gist.raw_url);
        mWebViewDetail.getSettings().setLoadWithOverviewMode(true);
        mWebViewDetail.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebViewDetail.setScrollContainer(false);
    }

    private void configureToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

}
