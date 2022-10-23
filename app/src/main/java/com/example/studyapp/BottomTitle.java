package com.example.studyapp;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BottomTitle extends LinearLayout implements View.OnClickListener {

    public LinearLayout menu1;
    public LinearLayout menu2;
    public LinearLayout menu3;

    public List<ImageView> imageView = new ArrayList<>();
    public static int nowInMenu = 0;//0 1 2 代表左中右
    private int[] imageResource = new int[6];

    public BottomTitle(Context context, AttributeSet attr){
        super(context,attr);
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.bottom,BottomTitle.this);
        menu1 = linearLayout.findViewById(R.id.menu1);
        menu2 = linearLayout.findViewById(R.id.menu2);
        menu3 = linearLayout.findViewById(R.id.menu3);


        menu1.setOnClickListener(this);
        menu2.setOnClickListener(this);
        menu3.setOnClickListener(this);

        imageView.add((ImageView) menu1.findViewById(R.id.imageView1));
        imageView.add((ImageView) menu2.findViewById(R.id.imageView2));
        imageView.add((ImageView) menu3.findViewById(R.id.imageView3));


        //所在页面加载奇数，不在页面加载偶数
        imageResource[0] = R.drawable.menu11;
        imageResource[1] = R.drawable.menu1;
        imageResource[2] = R.drawable.menu21;
        imageResource[3] = R.drawable.menu4;
        imageResource[4] = R.drawable.menu31;
        imageResource[5] = R.drawable.menu5;

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.menu1:
                if(nowInMenu != 0){
                    imageView.get(nowInMenu).setImageResource(imageResource[nowInMenu*2+1]);
                    imageView.get(0).setImageResource(imageResource[0]);
                    MainActivity.topTitle.change(0);
                    MainActivity.change(0);
                    nowInMenu = 0;
                }
                break;
            case R.id.menu2:
                if(nowInMenu != 1){
                    imageView.get(nowInMenu).setImageResource(imageResource[nowInMenu*2+1]);
                    imageView.get(1).setImageResource(imageResource[2]);
                    MainActivity.topTitle.change(1);
                    MainActivity.change(1);
                    nowInMenu = 1;
                }
                break;
            case R.id.menu3:
                if(nowInMenu != 2){
                    imageView.get(nowInMenu).setImageResource(imageResource[nowInMenu*2+1]);
                    imageView.get(2).setImageResource(imageResource[4]);
                    MainActivity.topTitle.change(2);
                    MainActivity.change(2);
                    nowInMenu = 2;
                }
                break;
        }
    }
}
