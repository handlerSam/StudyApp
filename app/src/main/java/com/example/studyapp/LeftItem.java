package com.example.studyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class LeftItem extends LinearLayout {
    public View view;
    public LeftItem(Context context, LinearLayout root){
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.left_recycler_item2_item,LeftItem.this);
    }
}
