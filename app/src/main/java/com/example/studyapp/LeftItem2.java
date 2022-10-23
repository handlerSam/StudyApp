package com.example.studyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class LeftItem2 extends LinearLayout {
    public View view;
    public LeftItem2(Context context, LinearLayout root){
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.left_recycler_item1_item, LeftItem2.this);
    }
}
