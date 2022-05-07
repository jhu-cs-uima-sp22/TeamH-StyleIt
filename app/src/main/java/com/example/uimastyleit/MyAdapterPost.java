package com.example.uimastyleit;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

public class MyAdapterPost extends RecyclerView.Adapter<MyAdapterPost.MyViewHolder> {

    public MyAdapterPost(Context context, ArrayList<Post> postList, OnPostListener onPostListener) {
        this.context = context;
        this.postList = postList;
        this.mOnPostListener = onPostListener;
    }

    private OnPostListener mOnPostListener;
    Context context;
    ArrayList<Post> postList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.post_element, parent, false);
        return new MyViewHolder(v, mOnPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StorageReference storageReference;
        Post post = postList.get(position);
        holder.userPost.setText(post.getUser().getName());
        holder.post = post;
        holder.position = position;
        holder.descrip.setText(post.getDescription());
        if(post.getHasImage()) {
            holder.imageView.setVisibility(View.VISIBLE);
            storageReference = FirebaseStorage.getInstance().getReference();
            StorageReference profileRef = storageReference.child("posts/" + post.getPostId() + "/postImage.jpg");
            profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(holder.imageView);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public interface OnPostListener{
        void onPostClick(int position);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView userPost, descrip;
        Post post;
        int position;
        ImageView imageView;
        private int pos1;
        OnPostListener onPostListener;

        public MyViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            this.onPostListener = onPostListener;
            userPost = itemView.findViewById(R.id.postName);
            descrip = itemView.findViewById(R.id.postDescription);
            imageView = itemView.findViewById(R.id.postImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }
}
