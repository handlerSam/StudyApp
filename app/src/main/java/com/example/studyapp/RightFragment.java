package com.example.studyapp;

import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RightFragment extends Fragment implements View.OnTouchListener, View.OnClickListener {

    public static List<RightRecyclerItem> mlist2 = new ArrayList<>();
    public ConstraintLayout father;
    public int standardWidth;
    public static ImageView imageView;
    public static int originX;
    public static int originY;
    public static int containerHeight;
    public static int[][] mapId;//数值储存为id
    public static int WIDTHNUMBER = 15;
    public static int HEIGHTNUMBER;
    public static RecyclerView recyclerView;
    public static RightItemAdapter rightAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        standardWidth = MainActivity.screanWidth/WIDTHNUMBER;
        //数组初始化
        mlist2.clear();
        for(int i = 0; i <= 28; i++){
            int number = MainActivity.itemNumberShared.getInt(String.valueOf(i),0);
            if(i == 28){
                boolean havesx = MainActivity.itemNumberShared.getBoolean("havesx",false);
                if(!havesx){
                    number = 1;
                    SharedPreferences.Editor editor = MainActivity.itemNumberShared.edit();
                    editor.putBoolean("havesx",true);
                    editor.putInt("28",1);
                    editor.apply();
                }
            }
            mlist2.add(new RightRecyclerItem(i,number));
        }

        containerHeight = (int)(MainActivity.linearLayout.getMeasuredHeight() - 40.0/160*MainActivity.densityDpi);
        HEIGHTNUMBER = (containerHeight/standardWidth)+1;
        mapId = new int[WIDTHNUMBER][HEIGHTNUMBER];
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main3,container,false);
        recyclerView = view.findViewById(R.id.rightRecycler);
        father = view.findViewById(R.id.rightFather);
        father.setOnTouchListener(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        rightAdapter = new RightItemAdapter(mlist2);
        recyclerView.setAdapter(rightAdapter);
        //地图初始化
        for(int i = 0; i < WIDTHNUMBER; i++){
            mapId[i][0] = 50;
        }
        for(int i = 0; i < WIDTHNUMBER; i++){
            for(int j = 1; j < HEIGHTNUMBER; j++){
                mapId[i][j] = MainActivity.mapIdShared.getInt(i+" "+j,0);
            }
        }

        //方块初始化
        for(int i = 0; i <= WIDTHNUMBER-1; i++){
            for (int j = 0; j <= HEIGHTNUMBER-1; j++){
                setBlock(i,j);
            }
        }
        return view;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {//只给背景注册，响应添加事件
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(RightItemAdapter.selecteditem >= 1){
                    originX = (int)(event.getX()/standardWidth);
                    originY = (int)(containerHeight - event.getY());
                    imageView = new ImageView(getContext());
                    imageView.setImageResource(getImage(RightItemAdapter.selecteditem));
                    imageView.setId(View.generateViewId());
                    imageView.setAdjustViewBounds(true);
                    imageView.setAlpha(0.5f);
                    imageView.setMaxHeight(standardWidth);
                    imageView.setMaxWidth(standardWidth);
                    father.addView(imageView);
                    ConstraintSet newSet = new ConstraintSet();
                    newSet.clone(father);
                    newSet.connect(imageView.getId(),ConstraintSet.LEFT,father.getId(),ConstraintSet.LEFT,(originX)*standardWidth);
                    newSet.connect(imageView.getId(),ConstraintSet.BOTTOM,father.getId(),ConstraintSet.BOTTOM,(originY)*standardWidth);
                    newSet.applyTo(father);
                    imageView.setVisibility(View.VISIBLE);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(RightItemAdapter.selecteditem >= 1){
                    if(originX != (int)event.getX()/standardWidth || originY != (int)(containerHeight - event.getY())){
                        ConstraintSet newSet = new ConstraintSet();
                        newSet.clone(father);
                        newSet.connect(imageView.getId(),ConstraintSet.LEFT,father.getId(),ConstraintSet.LEFT,((int)(event.getX()/standardWidth))*standardWidth);
                        newSet.connect(imageView.getId(),ConstraintSet.BOTTOM,father.getId(),ConstraintSet.BOTTOM,((int)((containerHeight - event.getY())/standardWidth))*standardWidth);
                        newSet.applyTo(father);
                        originX = (int)(event.getX()/standardWidth);
                        originY = (int)(containerHeight - event.getY());
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(RightItemAdapter.selecteditem >= 1) {
                    if(mlist2.get(RightItemAdapter.selecteditem).itemNumber > 0){
                        int x = (int)(event.getX() / standardWidth);
                        int y = (int)((containerHeight - event.getY()) / standardWidth);
                        if( x >= 0 && x < WIDTHNUMBER && y >= 0 && y < HEIGHTNUMBER ){
                            if(mapId[x][y] == 0){
                                ConstraintSet newSet = new ConstraintSet();
                                newSet.clone(father);
                                newSet.connect(imageView.getId(), ConstraintSet.LEFT, father.getId(), ConstraintSet.LEFT, x * standardWidth);
                                newSet.connect(imageView.getId(), ConstraintSet.BOTTOM, father.getId(), ConstraintSet.BOTTOM, y * standardWidth);
                                newSet.applyTo(father);
                                imageView.setAlpha(1.0f);
                                SharedPreferences.Editor editor2 = MainActivity.mapIdShared.edit();
                                editor2.putInt(x+" "+y, RightItemAdapter.selecteditem);
                                editor2.apply();
                                mapId[x][y] = RightItemAdapter.selecteditem;
                                imageView.setOnClickListener(this);
                                mlist2.get(RightItemAdapter.selecteditem).itemNumber--;
                                if(RightItemAdapter.selecteditem == 28){
                                    AnimationDrawable sxani = new AnimationDrawable();
                                    for(int i = 0; i <= 238; i++){
                                        sxani.addFrame(MainActivity.sxList[i], 10);
                                    }
                                    sxani.setOneShot(false);
                                    imageView.setImageDrawable(sxani);
                                    sxani.start();
                                }
                                int number = MainActivity.itemNumberShared.getInt(String.valueOf(RightItemAdapter.selecteditem),0);
                                SharedPreferences.Editor editor = MainActivity.itemNumberShared.edit();
                                editor.putInt(String.valueOf(RightItemAdapter.selecteditem),number-1);
                                editor.apply();
                                if(RightItemAdapter.itemOnViewholder[RightItemAdapter.selecteditem] != -1){
                                    RightItemAdapter.holder2List.get(RightItemAdapter.itemOnViewholder[RightItemAdapter.selecteditem]).numberText.setText(String.valueOf(mlist2.get(RightItemAdapter.selecteditem).itemNumber));
                                }

                            }else{
                                father.removeView(imageView);
                            }
                        }else{
                            father.removeView(imageView);
                        }
                    }else{
                        father.removeView(imageView);
                    }
                }
                break;
            default:
        }
        return true;
    }

    @Override
    public void onClick(View v) {//只给方块注册，响应删除事件
        if(RightItemAdapter.selecteditem == 0 ){
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) v.getLayoutParams();
            int x = (int)((params.leftMargin + standardWidth*0.5) / standardWidth);
            int y = (int)((params.bottomMargin + standardWidth*0.5) / standardWidth);
            int itemId = mapId[x][y];
            if(itemId != 50){//50是基岩
                mapId[x][y] = 0;
                SharedPreferences.Editor editor2 = MainActivity.mapIdShared.edit();
                editor2.putInt(x+" "+y,0);
                editor2.apply();
                mlist2.get(itemId).itemNumber++;
                SharedPreferences.Editor editor = MainActivity.itemNumberShared.edit();
                editor.putInt(String.valueOf(itemId),mlist2.get(itemId).itemNumber);
                editor.apply();
                if(RightItemAdapter.itemOnViewholder[itemId] != -1){
                    RightItemAdapter.holder2List.get(RightItemAdapter.itemOnViewholder[itemId]).numberText.setText(String.valueOf(mlist2.get(itemId).itemNumber));
                }
                father.removeView(v);
            }
        }
    }

    public int getImage(int itemId){
        switch(itemId){
            case 1:
                return R.drawable.grass;
            case 2:
                return R.drawable.soil;
            case 3:
                return R.drawable.rock;
            case 4:
                return R.drawable.roundstone;
            case 5:
                return R.drawable.sand;
            case 6:
                return R.drawable.shali;
            case 7:
                return R.drawable.treewood;
            case 8:
                return R.drawable.wood;
            case 9:
                return R.drawable.workplace;
            case 10:
                return R.drawable.stovefire;
            case 11:
                return R.drawable.tnt;
            case 12:
                return R.drawable.redstone;
            case 13:
                return R.drawable.bookshelf;
            case 14:
                return R.drawable.chest;
            case 15:
                return R.drawable.ladder;
            case 16:
                return R.drawable.lava;
            case 17:
                return R.drawable.leave;
            case 18:
                return R.drawable.mineblue;
            case 19:
                return R.drawable.minecoal;
            case 20:
                return R.drawable.minediamond;
            case 21:
                return R.drawable.minegold;
            case 22:
                return R.drawable.minegreen;
            case 23:
                return R.drawable.mineiron;
            case 24:
                return R.drawable.minered;
            case 25:
                return R.drawable.pumpkin;
            case 26:
                return R.drawable.pumpkinlight;
            case 27:
                return R.drawable.water;
            case 28:
                return R.drawable.sxtest00000;
            case 50:
                return R.drawable.base;
            default:
                return R.drawable.grass;
        }
    }

    public void setBlock(int x,int y){
        if(mapId[x][y] >= 1) {
            imageView = new ImageView(getContext());
            if(mapId[x][y] != 28){
                imageView.setImageResource(getImage(mapId[x][y]));
            }else{
                AnimationDrawable sxani = new AnimationDrawable();
                for(int i = 0; i <= 238; i++){
                    sxani.addFrame(MainActivity.sxList[i], 10);
                }
                sxani.setOneShot(false);
                imageView.setImageDrawable(sxani);
                sxani.start();
            }
            imageView.setId(View.generateViewId());
            imageView.setAdjustViewBounds(true);
            imageView.setMaxHeight(standardWidth);
            imageView.setMaxWidth(standardWidth);
            father.addView(imageView);
            ConstraintSet newSet = new ConstraintSet();
            newSet.clone(father);
            newSet.connect(imageView.getId(), ConstraintSet.LEFT, father.getId(), ConstraintSet.LEFT, (x) * standardWidth);
            newSet.connect(imageView.getId(), ConstraintSet.BOTTOM, father.getId(), ConstraintSet.BOTTOM, (y) * standardWidth);
            newSet.applyTo(father);
            imageView.setVisibility(View.VISIBLE);
            imageView.setOnClickListener(RightFragment.this);
        }
    }
}
