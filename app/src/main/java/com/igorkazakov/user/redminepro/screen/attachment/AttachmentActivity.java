package com.igorkazakov.user.redminepro.screen.attachment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.screen.base.BaseActivity;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttachmentActivity extends BaseActivity {

    @BindView(R.id.photo_view)
    PhotoView photoView;

    @BindView(R.id.attachmentContainer)
    FrameLayout attachmentContainer;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private static String IMAGE_URL_KEY = "IMAGE_URL_KEY";

    public static void start(@NonNull Context context, @NonNull String imageUrl) {
        Intent intent = new Intent(context, AttachmentActivity.class);
        intent.putExtra(IMAGE_URL_KEY, imageUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attachment);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showLoading();
        String url = getIntent().getExtras().getString(IMAGE_URL_KEY);

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(ApiFactory.getClient()))
                .build();

        picasso.load(url)
                .into(photoView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        hideLoading();

                    }

                    @Override
                    public void onError() {
                        hideLoading();
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
