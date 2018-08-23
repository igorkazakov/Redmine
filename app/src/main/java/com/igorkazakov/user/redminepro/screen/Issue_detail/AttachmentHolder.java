package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.room.entity.AttachmentEntity;
import com.igorkazakov.user.redminepro.screen.attachment.AttachmentActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 02.08.17.
 */

public class AttachmentHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.attachmentNameTextView)
    TextView mTitleTextView;

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
    }

    @Override
    public void onClick(View view) {
        AttachmentActivity.start(mContext, mAttachmentEntity.getContentUrl());
    }
}