package com.example.studyapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LeftFragment extends Fragment {
    RecyclerView leftRecycler;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main4,container,false);
        leftRecycler = view.findViewById(R.id.leftRecycler);
        leftRecycler.setAdapter(new LeftAdapter());
        leftRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        return view;
    }
}
