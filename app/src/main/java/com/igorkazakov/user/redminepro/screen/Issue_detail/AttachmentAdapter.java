package com.igorkazakov.user.redminepro.screen.Issue_detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.igorkazakov.user.redminepro.R;
import com.igorkazakov.user.redminepro.database.room.entity.AttachmentEntity;

import java.util.List;

/**
 * Created by user on 02.08.17.
 */

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentHolder> {

    private List<AttachmentEntity> attachmentEntities;

    public AttachmentAdapter(List<AttachmentEntity> issueModels) {
        this.attachmentEntities = issueModels;
    }

    @Override
    public AttachmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.attachment_issue_item, parent, false);
        return new AttachmentHolder(view, context);
    }

    @Override
    public void onBindViewHolder(AttachmentHolder holder, int position) {
        holder.bind(attachmentEntities.get(position));
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return attachmentEntities.size();
    }
}
