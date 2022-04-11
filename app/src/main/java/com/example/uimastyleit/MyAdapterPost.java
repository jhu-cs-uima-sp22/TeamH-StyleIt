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
        holder.post = post;
        holder.position = position;
        holder.descrip.setText(post.getDescription());
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView userPost, descrip;
        Post post;
        int position;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            DAOPost postDao  = new DAOPost();
            HashMap<String, Object> hashmap = new HashMap<>();
            userPost = itemView.findViewById(R.id.postName);
            descrip = itemView.findViewById(R.id.postDescription);
            Button likeBut = itemView.findViewById(R.id.likeButton);
            Button dislikeBut = itemView.findViewById(R.id.dislikeButton);

//            likeBut.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    hashmap.put("likes", post.getLikes()+1);
//                    postDao.update("-N-KpHQcSMTgBa7n-U1H", hashmap);
//                    post.addLike();
//
//                }
//            });

//            dislikeBut.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    post.dislikes();
//                }
//            });
        }
    }
}
