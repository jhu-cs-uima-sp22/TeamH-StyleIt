package com.example.uimastyleit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import java.util.HashMap;

public class HomeFrag extends Fragment implements MyAdapterPost.OnPostListener {
    private MainActivity myact;
    RecyclerView recyclerView;
    DatabaseReference dbRef;
    MyAdapterPost myAdapterPost;
    ArrayList<Post> postList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("My Feed");

        View view = inflater.inflate(R.layout.frag_home, container, false);

        recyclerView = view.findViewById(R.id.PostList);
        dbRef = FirebaseDatabase.getInstance().getReference("Post");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        postList = new ArrayList<>();
        myAdapterPost = new MyAdapterPost(getActivity(), postList, this);

        recyclerView.setAdapter(myAdapterPost);
        DAOPost postDao  = new DAOPost();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Post post = dataSnapshot.getValue(Post.class);
                    if(!postList.contains(post)) {
                        postList.add(0, post);
                        HashMap<String, Object> hashmap = new HashMap<>();
                        hashmap.put("dbId", dataSnapshot.getKey());
                        postDao.update(dataSnapshot.getKey(), hashmap);
                    }
                }
                myAdapterPost.notifyDataSetChanged();
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
        Intent intent = new Intent(getActivity(), PostDetails.class);
        Post toPass = postList.get(position);
        User userPost = toPass.getUser();
        intent.putExtra("post", toPass);
        intent.putExtra("user", userPost);
        startActivity(intent);
    }
}
