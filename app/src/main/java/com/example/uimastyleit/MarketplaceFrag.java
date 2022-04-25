package com.example.uimastyleit;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MarketplaceFrag extends Fragment implements MyAdapterItem.OnPostListener{
    private MainActivity myact;
    RecyclerView recyclerView;
    DatabaseReference dbRef;
    MyAdapterItem myAdapterItem;
    ArrayList<Item> itemList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("My Feed");

        View view = inflater.inflate(R.layout.frag_marketplace, container, false);

        recyclerView = view.findViewById(R.id.ItemList);
        dbRef = FirebaseDatabase.getInstance().getReference("Item");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemList = new ArrayList<>();
        myAdapterItem = new MyAdapterItem(getActivity(), itemList, this);

        recyclerView.setAdapter(myAdapterItem);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Item item = dataSnapshot.getValue(Item.class);
                    //postList.add(post);
                    itemList.add(0, item);
                }
                myAdapterItem.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "User error!", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


    @Override
    public void onPostClick(int position) {
        Intent intent = new Intent(getActivity(), ItemDetails.class);
        Item toPass = itemList.get(position);
        User userPost = toPass.getUser();
        intent.putExtra("item", toPass);
        intent.putExtra("userItem", userPost);
        startActivity(intent);
    }
}
