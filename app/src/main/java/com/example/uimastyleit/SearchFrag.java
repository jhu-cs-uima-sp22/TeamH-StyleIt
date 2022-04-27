package com.example.uimastyleit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Locale;

public class SearchFrag extends Fragment implements MyAdapterItem.OnPostListener {
    private MainActivity myact;

    RecyclerView recyclerView;
    DatabaseReference dbRef;
    MyAdapterItem myAdapterItem;
    ArrayList<Item> itemList;
    TextView results;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Search");
        View view = inflater.inflate(R.layout.frag_search, container, false);

        recyclerView = view.findViewById(R.id.search_list);
        dbRef = FirebaseDatabase.getInstance().getReference("Item");
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemList = new ArrayList<>();
        myAdapterItem = new MyAdapterItem(getActivity(), itemList, this);

        recyclerView.setAdapter(myAdapterItem);

        results = view.findViewById(R.id.textView5);
        results.setText("No results found!\nTry searching for something else.");
        results.setVisibility(View.INVISIBLE);

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

        EditText editText = view.findViewById(R.id.search_field);



        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return; // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                return; // Do nothing
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });


        return view;
    }

    private void filter(String string) {
        ArrayList<Item> filtered = new ArrayList<>();

        for(Item item : itemList) {
            String query = string.toLowerCase();
            String itemTitle = item.getTitle().toLowerCase();
            String condition = item.getCondition();
            String size = item.getSize();
            String seller = item.getUser().toString();
            boolean titleMatch = itemTitle.contains(query);
            boolean conditionMatch = condition.contains(query);
            boolean sizeMatch = size.contains(query);
            boolean sellerMatch = seller.contains(query);
            if(titleMatch || conditionMatch || sizeMatch || sellerMatch) {
                filtered.add(item);
            }
        }
        if(filtered.isEmpty()) {
            results.setVisibility(View.VISIBLE);
        } else {
            results.setVisibility(View.INVISIBLE);
        }
        myAdapterItem.searchChange(filtered);
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