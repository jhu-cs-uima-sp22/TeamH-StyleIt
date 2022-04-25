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

public class MyAdapterItem extends RecyclerView.Adapter<MyAdapterItem.MyViewHolder> {

    public MyAdapterItem(Context context, ArrayList<Item> itemList, OnPostListener onPostListener) {
        this.context = context;
        this.itemList = itemList;
        this.mOnPostListener = onPostListener;
    }

    private OnPostListener mOnPostListener;
    Context context;
    ArrayList<Item> itemList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_element, parent, false);
        return new MyViewHolder(v, mOnPostListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item item = itemList.get(position);
//        holder.userPost.setText(item.getUser().getName());
        holder.item = item;
        holder.position = position;
        holder.itemName.setText(item.getTitle());
        holder.itemSize.setText(item.getSize());
        holder.itemPrice.setText(String.valueOf(item.getPrice()));
        holder.itemDescription.setText(item.getDescription());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnPostListener{
        void onPostClick(int position);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView itemName, itemSize, itemDescription, itemPrice;
        Item item;
        int position;
        private int pos1;
        OnPostListener onPostListener;

        public MyViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            this.onPostListener = onPostListener;
            DAOItem itemDAO  = new DAOItem();
            HashMap<String, Object> hashmap = new HashMap<>();
            itemName = itemView.findViewById(R.id.itemName);
            itemDescription = itemView.findViewById(R.id.itemDescription);
            itemSize = itemView.findViewById(R.id.itemSize);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemView.setOnClickListener(this);
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

        @Override
        public void onClick(View view) {
            onPostListener.onPostClick(getAdapterPosition());
        }
    }

    public void searchChange(ArrayList<Item> filtered) {
        itemList = filtered;
        notifyDataSetChanged();
    }
}
