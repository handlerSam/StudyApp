package com.example.studyapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.List;

public class MiddleAdapter extends RecyclerView.Adapter<MiddleAdapter.ViewHolder> {

    public List<MiddleRecyclerItem> middleRecyclerItemList;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView contentTextView;
        TextView hintTextView;
        TextView numberTextView;
        Button deleteButton;
        public ViewHolder(View view){
            super(view);
            contentTextView = view.findViewById(R.id.textView5);
            hintTextView = view.findViewById(R.id.textView6);
            //numberTextView = view.findViewById(R.id.textView7);
            deleteButton = view.findViewById(R.id.button);
        }
    }

    public MiddleAdapter(List<MiddleRecyclerItem> list){
        this.middleRecyclerItemList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.middle_recycler_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.deleteButton.setOnClickListener(new NoDoubleClick() {
            @Override
            public void onNoDoubleClick(View v) {
                int position = holder.getAdapterPosition();
                int id = MiddleFragment.mlist.get(position).id;
                MiddleFragment.reciteString[id][0] = "";
                MiddleFragment.reciteString[id][1] = "";
                MiddleFragment.reciteInt[id] = 0;
                MiddleFragment.available[id] = true;

                SharedPreferences.Editor editor = MainActivity.reciteShared.edit();
                editor.putString("content"+id, "");
                editor.putString("hint"+id, "");
                editor.putInt("number"+id, 0);
                editor.apply();

                MiddleFragment.mlist.remove(position);
                MiddleFragment.middleAdapter.notifyItemRemoved(position);
                MiddleFragment.middleAdapter.notifyItemRangeChanged(position,getItemCount()-position);

                if(MiddleFragment.mlist.size()==0){
                    NotificationManager manager =  MainActivity.notificationManager;
                    manager.cancel(1);
                }
                if(MiddleFragment.mlist.size() > 0){
                    Message msg = new Message();//重新启动service
                    msg.what = 5;
                    MainActivity.handler.sendMessage(msg);
                }
            }
        });
        holder.deleteButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.baseContext2)
                        .setTitle("确定删除所有背诵内容？")
                        .setMessage("此操作不可逆。")
                        .setCancelable(true)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        SharedPreferences.Editor editor = MainActivity.reciteShared.edit();
                                        for(int i = 0; i < MainActivity.MAXWWORD; i++){
                                            if(!MainActivity.reciteShared.getString("content"+i,"").equals("")){
                                                editor.putString("content"+i,"");
                                                editor.putString("hint"+i,"");
                                                editor.apply();
                                            }
                                        }
                                        MainActivity.change(1);
                                        Toast.makeText(MainActivity.baseContext2,"已全部移除",Toast.LENGTH_SHORT).show();
                                    }
                                }
                        )
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                builder.create().show();
                return true;
            }
        });
        return holder;
    }
    public abstract class NoDoubleClick implements View.OnClickListener{
        private Long lastClickTime = 0L;
        @Override
        public void onClick(View v) {
            Long now = Calendar.getInstance().getTimeInMillis();
            if(now - lastClickTime >= 1000){
                lastClickTime = now;
                onNoDoubleClick(v);
            }
        }
        abstract public void onNoDoubleClick(View v);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MiddleRecyclerItem item = middleRecyclerItemList.get(position);
        holder.contentTextView.setText("记忆:"+item.content);
        //holder.numberTextView.setText("已经背诵:"+item.number+"次");
        holder.hintTextView.setText("提示："+item.hint);
    }

    @Override
    public int getItemCount() {
        return middleRecyclerItemList.size();
    }
}
