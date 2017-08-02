package com.igorkazakov.user.redminepro.screen.IssueDetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.entity.AttachmentEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 02.08.17.
 */

public class AttachmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.attachmentNameTextView)
    TextView mTitleTextView;

    @BindView(R.id.dateTextView)
    TextView mDateTextView;

    @BindView(R.id.authorTextView)
    TextView mAuthorTextView;

    @BindView(R.id.descriptionTextView)
    TextView mDescriptionTextView;

    private Context mContext;
    private AttachmentEntity mAttachmentEntity;

    public AttachmentHolder(View itemView, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        ButterKnife.bind(this, itemView);
        mContext = context;
    }

    public void bind(AttachmentEntity entity) {

        mAttachmentEntity = entity;
        mTitleTextView.setText(entity.getFilename());
        mDateTextView.setText(entity.getCreatedOn());
        mAuthorTextView.setText(entity.getAuthorName());
        mDescriptionTextView.setText(entity.getDescription());
    }

    @Override
    public void onClick(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mAttachmentEntity.getContentUrl()));
        mContext.startActivity(browserIntent);
    }
}