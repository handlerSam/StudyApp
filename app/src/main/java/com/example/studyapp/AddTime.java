package com.example.studyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Calendar;

public class AddTime extends LinearLayout {
    public View view;
    public AddTime(Context context){
        super(context);
        view = LayoutInflater.from(context).inflate(R.layout.left_add_time, AddTime.this);
        Calendar now = Calendar.getInstance();
        EditText ed;
        ed = view.findViewById(R.id.addYear);
        ed.setText(""+now.get(Calendar.YEAR));
        ed = view.findViewById(R.id.addMonth);
        ed.setText(""+(now.get(Calendar.MONTH)+1));
        ed = view.findViewById(R.id.addDay);
        ed.setText(""+now.get(Calendar.DAY_OF_MONTH));
        ed = view.findViewById(R.id.addHour);
        ed.setText(""+now.get(Calendar.HOUR_OF_DAY));
        ed = view.findViewById(R.id.addMinute);
        ed.setText(""+now.get(Calendar.MINUTE));
    }
}
