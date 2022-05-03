package com.example.uimastyleit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapterComment extends RecyclerView.Adapter<MyAdapterComment.MyViewHolder> {

    public MyAdapterComment(Context context, ArrayList<Comment> commentList, OnPostListener onPostListener) {
        this.context = context;
        this.commentList = commentList;
        this.mOnPostListener = onPostListener;
    }

    private OnPostListener mOnPostListener;
    Context context;
    ArrayList<Comment> commentList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.comment_element, parent, false);
        return new MyViewHolder(v, mOnPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.userPost.setText(comment.getUser().getName());
        holder.comment = comment;
        holder.position = position;
        holder.body.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public interface OnPostListener{
        void onPostClick(int position);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView userPost, body;
        Comment comment;
        int position;
        private int pos1;
        OnPostListener onPostListener;

        public MyViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            this.onPostListener = onPostListener;
            userPost = itemView.findViewById(R.id.dispCommentName);
            body = itemView.findViewById(R.id.dispCommentBody);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }
}
