package com.example.studyapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class TopTitle extends LinearLayout {
    public TextView topText;
    public ImageView addImage;
    public ImageView helpImage;
    public TextView changeSizeButton;
    public TextView disorganizeButton;
    public void change(int number){
        switch(number){
            case 0:
                topText.setText(R.string.leftActivity);
                addImage.setVisibility(INVISIBLE);
                helpImage.setVisibility(VISIBLE);
                changeSizeButton.setVisibility(INVISIBLE);
                disorganizeButton.setVisibility(INVISIBLE);
                addImage.setClickable(false);
                break;
            case 1:
                topText.setText(R.string.middleActivity);
                addImage.setImageResource(R.drawable.add);
                addImage.setVisibility(VISIBLE);
                helpImage.setVisibility(VISIBLE);
                changeSizeButton.setVisibility(VISIBLE);
                disorganizeButton.setVisibility(VISIBLE);
                addImage.setClickable(true);
                break;
            case 2:
                topText.setText(R.string.rightActivity);
                addImage.setImageResource(R.drawable.recycle);
                addImage.setVisibility(VISIBLE);
                helpImage.setVisibility(VISIBLE);
                disorganizeButton.setVisibility(INVISIBLE);
                changeSizeButton.setVisibility(INVISIBLE);
                addImage.setClickable(true);
                break;
        }
    }

    public TopTitle(Context context, AttributeSet attr){
        super(context,attr);
        LinearLayout linearLayout = (LinearLayout)LayoutInflater.from(context).inflate(R.layout.title,TopTitle.this);
        topText = linearLayout.findViewById(R.id.titleTitle);
        addImage = linearLayout.findViewById(R.id.addImage);
        helpImage = linearLayout.findViewById(R.id.helpImage);
        changeSizeButton = linearLayout.findViewById(R.id.changeSize);
        disorganizeButton = linearLayout.findViewById(R.id.disorganizeList);
        topText.getPaint().setFakeBoldText(true);
        topText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.baseContext2);//??????getApplicationContext????????????????????????????????????
                dialog.setTitle("???????????????");//??????
                dialog.setMessage("??????????????????");//??????
                dialog.setCancelable(false);//????????????????????????????????????
                dialog.setPositiveButton("??????????????????20", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //????????????
                        SharedPreferences.Editor editor = itemNumberShared.edit();
                        for(int i = 1; i <= 27; i++){
                            editor.putInt(String.valueOf(i),20);
                        }
                        editor.apply();
                    }});
                dialog.setNegativeButton("??????????????????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //????????????
                        SharedPreferences.Editor editor = itemNumberShared.edit();
                        for(int i = 1; i <= 27; i++){
                            editor.putInt(String.valueOf(i),0);
                        }
                        editor.apply();
                    }});
                dialog.show();*/
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.baseContext2);
                dialog.setTitle("??????????????????");//??????
                dialog.setMessage(R.string.version);//??????
                dialog.setCancelable(true);//????????????????????????????????????
                dialog.show();
            }
        });
        changeSizeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int now = MainActivity.settingShared.getInt("setting", 0);
                if(now == 2){
                    now = 0;
                }else{
                    now++;
                }
                SharedPreferences.Editor editor = MainActivity.settingShared.edit();
                editor.putInt("setting", now);
                editor.apply();
                String str = "?????????????????????"+ ((now == 0)? "?????????":now == 1? "????????????":"?????????");
                str += ",??????????????????????????????????????????";
                Toast.makeText(MainActivity.baseContext2,str,Toast.LENGTH_SHORT).show();
            }
        });
        change(0);

        helpImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                    Message msg = new Message();
                    msg.what = 4;
                    MainActivity.handler.sendMessage(msg);
            }
        });

        disorganizeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.disorderList();
                if(MiddleFragment.mlist.size() > 0){
                    Message msg = new Message();//????????????service
                    msg.what = 5;
                    MainActivity.handler.sendMessage(msg);
                }
                Toast.makeText(MainActivity.baseContext2,"?????????",Toast.LENGTH_SHORT).show();
            }
        });
    }

}
