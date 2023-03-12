package com.akifisitan.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter for comments recycler view
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private final Context ctx;
    private final List<Comment> data;

    public CommentAdapter(Context ctx, List<Comment> data){
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(ctx).inflate(R.layout.comments_row_layout, parent, false);
        return new CommentViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment currentComment = data.get(holder.getAdapterPosition());
        holder.txtListText.setText(currentComment.getText());
        holder.txtListName.setText(currentComment.getName());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView txtListName, txtListText;
        ConstraintLayout row;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txtListName = itemView.findViewById(R.id.txtListCommentsName);
            txtListText = itemView.findViewById(R.id.txtListCommentsText);
            row = itemView.findViewById(R.id.row_list_comments);
        }

    }

}
