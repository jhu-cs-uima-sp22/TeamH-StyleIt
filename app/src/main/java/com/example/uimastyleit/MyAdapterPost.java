package com.example.uimastyleit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapterPost extends RecyclerView.Adapter<MyAdapterPost.MyViewHolder> {

    public MyAdapterPost(Context context, ArrayList<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    Context context;
    ArrayList<Post> postList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.post_element, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Post post = postList.get(position);
        holder.userPost.setText(post.getUser().getName());
        holder.descrip.setText(post.getDescription());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView userPost, descrip;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userPost = itemView.findViewById(R.id.postName);
            descrip = itemView.findViewById(R.id.postDescription);
        }
    }
}
