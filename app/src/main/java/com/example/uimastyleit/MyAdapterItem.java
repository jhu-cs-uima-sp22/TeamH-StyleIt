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
        StorageReference storageReference;
        Item item = itemList.get(position);
//        holder.userPost.setText(item.getUser().getName());
        holder.item = item;
        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("items/"+ item.getId() +"/itemImage.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(holder.itemImage);
            }
        });
        holder.position = position;
        holder.itemName.setText(item.getTitle());
        holder.itemSize.setText(item.getSize());
        holder.itemPrice.setText(String.valueOf(item.getPrice()));
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
        private ImageView itemImage;
        Item item;
        int position;
        private int pos1;
        OnPostListener onPostListener;

        public MyViewHolder(@NonNull View itemView, OnPostListener onPostListener) {
            super(itemView);
            this.onPostListener = onPostListener;
            itemImage = itemView.findViewById(R.id.itemDetailImage);
            itemName = itemView.findViewById(R.id.itemName);
            itemSize = itemView.findViewById(R.id.itemSize);
            itemPrice = itemView.findViewById(R.id.itemPrice);
            itemView.setOnClickListener(this);
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
