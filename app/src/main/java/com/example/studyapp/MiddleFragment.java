package com.example.studyapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MiddleFragment extends Fragment {
    public View view;
    public RecyclerView middleRecycler;
    public static int MAXWWORD = 200;
    public static List<MiddleRecyclerItem> mlist = new ArrayList<>();
    public static String[][] reciteString = new String[MAXWWORD][2];
    public static int[] reciteInt = new int[MAXWWORD];
    public static boolean[] available = new boolean[MAXWWORD];
    public static MiddleAdapter middleAdapter = new MiddleAdapter(mlist);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main2,container,false);

        //更新recyclerView
        middleRecycler = view.findViewById(R.id.middleRecycler);
        middleRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        middleRecycler.setAdapter(middleAdapter);
        MiddleFragment.middleAdapter.notifyItemRangeChanged(0,MiddleFragment.mlist.size()+1);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mlist.clear();
        for(int i = 0; i <MAXWWORD; i++){
            if(MainActivity.reciteShared.getString("content"+i,"").equals("")){
                reciteString[i][0] = "";
                reciteString[i][1] = "";
                reciteInt[i] = 0;
                available[i] = true;
            }else{
                reciteString[i][0] = MainActivity.reciteShared.getString("content"+i,"");
                reciteString[i][1] = MainActivity.reciteShared.getString("hint"+i,"");
                reciteInt[i] = MainActivity.reciteShared.getInt("number"+i,0);
                available[i] = false;
                mlist.add(new MiddleRecyclerItem(reciteString[i][0],reciteString[i][1],reciteInt[i] = 0,i));
            }
        }

    }
}
