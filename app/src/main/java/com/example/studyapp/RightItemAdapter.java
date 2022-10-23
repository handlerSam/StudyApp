package com.example.studyapp;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class RightItemAdapter extends RecyclerView.Adapter<RightItemAdapter.ViewHolder2> {

    public static List<RightRecyclerItem> mlist2;
    public static List<ViewHolder2> holder2List ;
    public static int selecteditem ;
    public static int[] itemOnViewholder ;

    static class ViewHolder2 extends RecyclerView.ViewHolder{
        TextView numberText;
        ImageView itemImage;
        public ViewHolder2(View view){
            super(view);
            numberText = view.findViewById(R.id.itemNumberText);
            itemImage = view.findViewById(R.id.itemImage);
        }
    }

    public RightItemAdapter(List<RightRecyclerItem> mlist2){
        this.mlist2 = mlist2;
        selecteditem = -1;
        holder2List = new ArrayList<>();
        itemOnViewholder = new int[29];
        for(int i = 0; i<=28; i++){
            itemOnViewholder[i] = -1;
        }
    }

    @NonNull
    @Override
    public ViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.right_recyclerview_item,parent,false);
        final ViewHolder2 viewHolder2 = new ViewHolder2(view);
        holder2List.add(viewHolder2);
        viewHolder2.numberText.setBackgroundColor(Color.argb(125,0,0,0));
        viewHolder2.itemImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder2.getAdapterPosition();
                if(selecteditem == position){//position=itemId,其中position0为删除，Itemid0 为空气
                    selecteditem = -1;
                    viewHolder2.itemImage.setImageResource(setImage(mlist2.get(position).itemId,position));
                }else{
                    int former = selecteditem;
                    selecteditem = position;
                    if(former != -1) {
                        if (RightItemAdapter.itemOnViewholder[former] != -1) {
                            holder2List.get(RightItemAdapter.itemOnViewholder[former]).itemImage.setImageResource(setImage(mlist2.get(former).itemId, former));
                        }
                    }
                    viewHolder2.itemImage.setImageResource(setImage(mlist2.get(position).itemId,position));
                }
            }
        });
        return viewHolder2;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder2 holder, int position) {
        int holderid = 0;
        for(int i = 0; i < holder2List.size(); i++ ){
            if(holder2List.get(i).equals(holder)){
                holderid = i;
                break;
            }
        }
        for(int i = 0; i < itemOnViewholder.length; i++){
            if(itemOnViewholder[i] == holderid){
                itemOnViewholder[i] = -1;
                break;
            }
        }
        itemOnViewholder[position] = holderid;
        RightRecyclerItem item = mlist2.get(position);
        holder.numberText.setText(String.valueOf(item.itemNumber));
        holder.itemImage.setImageResource(setImage(item.itemId,position));
    }

    @Override
    public int getItemCount() {
        return mlist2.size();
    }

    private int setImage(int itemid,int position){
        //0删除 1草地 2泥土 3石头 4圆石 5沙子 6沙砾 7树木 8木头 9工作台
        //10火炉 11TNT 12红砖 13书架 50基岩 14箱子 15梯子 16岩浆 17树叶
        //18青金石 19煤炭 20钻石 21黄金 22绿宝石 23铁矿 24红石 25南瓜
        //26南瓜灯 27水 28霜星
        if(selecteditem == position){
            switch(itemid){
                case 0:
                    return R.drawable.deleteup;
                case 1:
                    return R.drawable.grassup;
                case 2:
                    return R.drawable.soilup;
                case 3:
                    return R.drawable.rockup;
                case 4:
                    return R.drawable.roundstoneup;
                case 5:
                    return R.drawable.sandup;
                case 6:
                    return R.drawable.shaliup;
                case 7:
                    return R.drawable.treewoodup;
                case 8:
                    return R.drawable.woodup;
                case 9:
                    return R.drawable.workplaceup;
                case 10:
                    return R.drawable.stovefireup;
                case 11:
                    return R.drawable.tntup;
                case 12:
                    return R.drawable.redstoneup;
                case 13:
                    return R.drawable.bookshelfup;
                case 14:
                    return R.drawable.chestup;
                case 15:
                    return R.drawable.ladderup;
                case 16:
                    return R.drawable.lavaup;
                case 17:
                    return R.drawable.leaveup;
                case 18:
                    return R.drawable.mineblueup;
                case 19:
                    return R.drawable.minecoalup;
                case 20:
                    return R.drawable.minediamondup;
                case 21:
                    return R.drawable.minegoldup;
                case 22:
                    return R.drawable.minegreenup;
                case 23:
                    return R.drawable.mineironup;
                case 24:
                    return R.drawable.mineredup;
                case 25:
                    return R.drawable.pumpkinup;
                case 26:
                    return R.drawable.pumpkinlightup;
                case 27:
                    return R.drawable.waterup;
                case 28:
                    return R.drawable.shuangxingup;
                case 50:
                    return R.drawable.base;
                default:
                    return R.drawable.grassup;
            }
        }else{
            switch(itemid){
                case 0:
                    return R.drawable.delete;
                case 1:
                    return R.drawable.grassitem;
                case 2:
                    return R.drawable.soilitem;
                case 3:
                    return R.drawable.rockitem;
                case 4:
                    return R.drawable.roundstoneitem;
                case 5:
                    return R.drawable.sanditem;
                case 6:
                    return R.drawable.shaliitem;
                case 7:
                    return R.drawable.treewooditem;
                case 8:
                    return R.drawable.wooditem;
                case 9:
                    return R.drawable.workplaceitem;
                case 10:
                    return R.drawable.stovefireitem;
                case 11:
                    return R.drawable.tntitem;
                case 12:
                    return R.drawable.redstoneitem;
                case 13:
                    return R.drawable.bookshelfitem;
                case 14:
                    return R.drawable.chestitem;
                case 15:
                    return R.drawable.ladderitem;
                case 16:
                    return R.drawable.lavaitem;
                case 17:
                    return R.drawable.leaveitem;
                case 18:
                    return R.drawable.mineblueitem;
                case 19:
                    return R.drawable.minecoalitem;
                case 20:
                    return R.drawable.minediamonditem;
                case 21:
                    return R.drawable.minegolditem;
                case 22:
                    return R.drawable.minegreenitem;
                case 23:
                    return R.drawable.mineironitem;
                case 24:
                    return R.drawable.minereditem;
                case 25:
                    return R.drawable.pumpkinitem;
                case 26:
                    return R.drawable.pumpkinlightitem;
                case 27:
                    return R.drawable.wateritem;
                case 28:
                    return R.drawable.shuangxingitem;
                case 50:
                    return R.drawable.base;
                default:
                    return R.drawable.grassitem;
            }
        }
    }

}
