package com.igorkazakov.user.redminepro.screen.AttachmentScreen;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.github.chrisbanes.photoview.PhotoView;
import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.api.ApiFactory;
import com.igorkazakov.user.redminepro.screen.dashboard.DashboardActivity;
import com.igorkazakov.user.redminepro.screen.general.LoadingFragment;
import com.igorkazakov.user.redminepro.screen.general.LoadingView;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.LoaderLifecycleHandler;

public class AttachmentActivity extends AppCompatActivity implements AttachmentView {

    @BindView(R.id.photo_view)
    PhotoView photoView;

    @BindView(R.id.attachmentContainer)
    FrameLayout attachmentContainer;

    private AttachmentPresenter mPresenter;
    private LoadingView mLoadingView;
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        mLoadingView = new LoadingFragment(this, attachmentContainer);
        LifecycleHandler lifecycleHandler = LoaderLifecycleHandler.create(this, getSupportLoaderManager());
        mPresenter = new AttachmentPresenter(this, lifecycleHandler);

        mLoadingView.showLoading();
        String url = getIntent().getExtras().getString(IMAGE_URL_KEY);

        Picasso picasso = new Picasso.Builder(this)
                .downloader(new OkHttp3Downloader(ApiFactory.getClient()))
                .build();

        picasso.load(url)
                .into(photoView, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        mLoadingView.hideLoading();

                    }

                    @Override
                    public void onError() {
                        mLoadingView.hideLoading();
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
