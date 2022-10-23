package com.example.studyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.icu.text.CaseMap;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class LeftAdapter extends RecyclerView.Adapter<LeftAdapter.LeftViewHolder> {
    public static String fetchText;

    public static String fetchYear;
    public static String fetchMonth;
    public static String fetchDay;
    public static String fetchHour;
    public static String fetchMinute;

    public static Handler handler;
    public LinearLayout container;
    public LinearLayout container2;

    static class LeftViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView hint;
        ImageView add;
        LinearLayout container;
        public LeftViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.leftTitleText);
            hint = view.findViewById(R.id.leftTitleHint);
            add = view.findViewById(R.id.leftAdd);
            container = view.findViewById(R.id.leftItemContainer);
        }
    }


    public LeftAdapter(){
        super();
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch(msg.what){
                    case 0://在取得了Alertdialog的数据后（添加计划）
                        final LeftItem item = new LeftItem(MainActivity.baseContext,container);
                        View v = item.view;
                        final TextView title = v.findViewById(R.id.leftItemTitle);
                        final TextView content = v.findViewById(R.id.leftItemContent);
                        final ImageView imageView = v.findViewById(R.id.leftItemImage);
                        final Button button = v.findViewById(R.id.leftItemComplete);

                        int id = 0;
                        for(int i = 0; i <= 9; i++){
                            if(MainActivity.targetShared.getString("content"+i,"").equals("")){
                                id = i;
                                break;
                            }
                        }

                        final int inputId = id;
                        int number = MainActivity.targetShared.getInt("number",0);
                        SharedPreferences.Editor editor = MainActivity.targetShared.edit();
                        editor.putString("content"+id,fetchText);
                        editor.putInt("lastyear"+id,0);
                        editor.putInt("lastmonth"+id,-1);
                        editor.putInt("lastday"+id,0);
                        editor.putInt("already"+id,0);
                        editor.putInt("number",number+1);
                        editor.apply();

                        String str = "目标"+id+":"+fetchText;
                        title.setText(str);
                        content.setText("已经坚持了"+0+"天了");
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.baseContext2);
                                dialog.setTitle("放弃");//标题
                                dialog.setMessage("确认要放弃这一计划？不再考虑一下？");//正文
                                dialog.setCancelable(true);//是否能点击屏幕取消该弹窗
                                dialog.setPositiveButton("放弃", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int number = MainActivity.targetShared.getInt("number",0);
                                        SharedPreferences.Editor editor = MainActivity.targetShared.edit();
                                        editor.putString("content"+inputId,"");
                                        editor.putInt("lastyear"+inputId,0);
                                        editor.putInt("lastmonth"+inputId,-1);
                                        editor.putInt("lastday"+inputId,0);
                                        editor.putInt("already"+inputId,0);
                                        editor.putInt("number",number-1);
                                        editor.apply();
                                        container.removeView(item);
                                    }});
                                dialog.setNegativeButton("再想想", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }});
                                dialog.show();
                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//每日签到
                                button.setVisibility(View.INVISIBLE);
                                int already = MainActivity.targetShared.getInt("already"+inputId,0);
                                SharedPreferences.Editor editor = MainActivity.targetShared.edit();
                                editor.putInt("lastyear"+inputId,MainActivity.year);
                                editor.putInt("lastmonth"+inputId,MainActivity.month);
                                editor.putInt("lastday"+inputId,MainActivity.day);
                                editor.putInt("already"+inputId,already+1);
                                editor.apply();

                                Message msg = new Message();
                                msg.what = 1;
                                MainActivity.handler.sendMessage(msg);
                                Log.v("Sam","send");

                                content.setText("已经坚持了"+(already+1)+"天了");
                            }
                        });

                        container.addView(item);
                        MainActivity.change(0);
                        break;




                    case 1://在取得了Alert的数据后（添加DDL）
                        final LeftItem2 item2 = new LeftItem2(MainActivity.baseContext,container2);
                        final View v2 = item2.view;
                        final TextView title2 = v2.findViewById(R.id.leftItemTitle2);
                        final TextView contentOverTime = v2.findViewById(R.id.leftItemContent21);
                        final TextView contentLeftTime = v2.findViewById(R.id.leftItemContent22);
                        final Button button2 = v2.findViewById(R.id.leftItemComplete2);

                        int id2 = 0;
                        for(int i = 0; i < MainActivity.DDLMAXNUMBER; i++){
                            if(MainActivity.ddlShared.getString("content"+i,"").equals("")){
                                id2 = i;
                                break;
                            }
                        }
                        final int inputId2 = id2;

                        SharedPreferences.Editor editor2 = MainActivity.ddlShared.edit();
                        int number2 = MainActivity.ddlShared.getInt("number",0);
                        editor2.putInt("number",number2+1);
                        editor2.putString("content"+id2,fetchText);
                        editor2.putInt("year"+id2,Integer.valueOf(fetchYear));
                        editor2.putInt("month"+id2,Integer.valueOf(fetchMonth));
                        editor2.putInt("day"+id2,Integer.valueOf(fetchDay));
                        editor2.putInt("hour"+id2,Integer.valueOf(fetchHour));
                        editor2.putInt("minute"+id2,Integer.valueOf(fetchMinute));
                        editor2.apply();


                        String str3 = "DDL名称：" + MainActivity.ddlShared.getString("content"+id2,"");
                        title2.setText(str3);
                        SharedPreferences pre = MainActivity.ddlShared;
                        String str4 = pre.getInt("year"+id2,0)+"年"+pre.getInt("month"+id2,0)+"月"+pre.getInt("day"+id2,0)+"日"+pre.getInt("hour"+id2,0)+"时"+pre.getInt("minute"+id2,0)+"分";
                        contentOverTime.setText("结束时间："+str4);

                        Calendar now = Calendar.getInstance();
                        now.set(Calendar.YEAR,pre.getInt("year"+id2,0));
                        now.set(Calendar.MONTH,(pre.getInt("month"+id2,0)-1));
                        now.set(Calendar.DAY_OF_MONTH,pre.getInt("day"+id2,0));
                        now.set(Calendar.HOUR_OF_DAY,pre.getInt("hour"+id2,0));
                        now.set(Calendar.MINUTE,pre.getInt("minute"+id2,0));
                        now.set(Calendar.SECOND,0);
                        Calendar standardTime = Calendar.getInstance();
                        standardTime.set(Calendar.SECOND,0);
                        long diff = now.getTime().getTime() - standardTime.getTime().getTime();
                        long days = diff / (1000 * 60 * 60 * 24);
                        long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                        long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
                        if(diff >= 0){
                            String str5 = days+"天"+hours+"小时"+minutes+"分钟";
                            contentLeftTime.setText("剩余时间："+str5);
                        }else{
                            contentLeftTime.setText("已过期");
                        }
                        contentLeftTime.setTextColor(days > 0? Color.parseColor("black"):(hours > 12? Color.parseColor("magenta"):Color.parseColor("red")));
                        button2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.baseContext2);
                                dialog.setTitle("结束DDL");//标题
                                dialog.setMessage("确定完成了这个DDL？");//正文
                                dialog.setCancelable(true);//是否能点击屏幕取消该弹窗
                                dialog.setPositiveButton("完成了！", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Message msg = new Message();
                                        msg.what = 3;
                                        MainActivity.handler.sendMessage(msg);
                                        int number = MainActivity.ddlShared.getInt("number",0);
                                        SharedPreferences.Editor editor = MainActivity.ddlShared.edit();
                                        editor.putString("content"+inputId2,"");
                                        editor.putInt("year"+inputId2,0);
                                        editor.putInt("month"+inputId2,-1);
                                        editor.putInt("day"+inputId2,0);
                                        editor.putInt("hour"+inputId2,0);
                                        editor.putInt("minute"+inputId2,0);
                                        editor.putInt("number",number-1);
                                        editor.apply();
                                        container2.removeView(item2);
                                    }});
                                dialog.setNegativeButton("还没呢", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {}});
                                dialog.show();
                            }
                        });
                        container2.addView(item2);
                        MainActivity.change(0);
                        break;
                    default:
                }
            }
        };
    }

    @NonNull
    @Override
    public LeftAdapter.LeftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LeftAdapter.LeftViewHolder viewHolder;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.left_recycler_item2,parent,false);
        viewHolder = new LeftAdapter.LeftViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeftViewHolder holder, int position) {
        switch(position){
            case 0:
                holder.title.setText("每日目标");
                TextPaint p = holder.title.getPaint();
                p.setFakeBoldText(true);
                int r = (int)(Math.random()*5+1);
                int str = r==1? R.string.tip1:r==2? R.string.tip2:r==3? R.string.tip3:r==4? R.string.tip4:R.string.tip5;
                holder.hint.setText(str);
                container = holder.container;
                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int number = MainActivity.targetShared.getInt("number",0);
                        if(number < 10){
                            Message message = new Message();
                            message.what = 0;
                            MainActivity.handler.sendMessage(message);
                        }else{
                            Toast.makeText(MainActivity.baseContext,"不要一次定太多目标哦",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //初始化
                for(int id = 0; id<= 9; id++){
                    if(!MainActivity.targetShared.getString("content"+id,"").equals("")){
                        final LeftItem item = new LeftItem(MainActivity.baseContext,container);
                        View v = item.view;
                        final TextView title = v.findViewById(R.id.leftItemTitle);
                        final TextView content = v.findViewById(R.id.leftItemContent);
                        final ImageView imageView = v.findViewById(R.id.leftItemImage);
                        final Button button = v.findViewById(R.id.leftItemComplete);

                        final int inputId = id;

                        String str2 = "目标"+id+":"+MainActivity.targetShared.getString("content"+id,"");
                        title.setText(str2);
                        content.setText("已经坚持了"+MainActivity.targetShared.getInt("already"+id,0)+"天了");
                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.baseContext2);
                                dialog.setTitle("放弃");//标题
                                dialog.setMessage("确认要放弃这一计划？不再考虑一下？");//正文
                                dialog.setCancelable(true);//是否能点击屏幕取消该弹窗
                                dialog.setPositiveButton("放弃", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int number = MainActivity.targetShared.getInt("number",0);
                                        SharedPreferences.Editor editor = MainActivity.targetShared.edit();
                                        editor.putString("content"+inputId,"");
                                        editor.putInt("lastyear"+inputId,0);
                                        editor.putInt("lastmonth"+inputId,-1);
                                        editor.putInt("lastday"+inputId,0);
                                        editor.putInt("already"+inputId,0);
                                        editor.putInt("number",number-1);
                                        editor.apply();
                                        container.removeView(item);
                                    }});
                                dialog.setNegativeButton("再想想", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {}});
                                dialog.show();
                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {//每日签到
                                button.setVisibility(View.INVISIBLE);
                                int already = MainActivity.targetShared.getInt("already"+inputId,0);
                                SharedPreferences.Editor editor = MainActivity.targetShared.edit();
                                editor.putInt("lastyear"+inputId,MainActivity.year);
                                editor.putInt("lastmonth"+inputId,MainActivity.month);
                                editor.putInt("lastday"+inputId,MainActivity.day);
                                editor.putInt("already"+inputId,already+1);
                                editor.apply();

                                Message msg = new Message();
                                msg.what = 1;
                                MainActivity.handler.sendMessage(msg);

                                content.setText("已经坚持了"+(already+1)+"天了");
                            }
                        });
                        if(MainActivity.year == MainActivity.targetShared.getInt("lastyear"+id,0)){
                            if(MainActivity.month == MainActivity.targetShared.getInt("lastmonth"+id,-1)){
                                if(MainActivity.day == MainActivity.targetShared.getInt("lastday"+id,0)){
                                    button.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                        container.addView(item);
                    }
                }

                break;

            case 1:
                holder.title.setText("DDL管理");
                TextPaint p2 = holder.title.getPaint();
                p2.setFakeBoldText(true);
                int r2 = (int)(Math.random()*5+1);
                int str2 = r2==1? R.string.tip6:r2==2? R.string.tip7:r2==3? R.string.tip8:r2==4? R.string.tip9:R.string.tip10;
                holder.hint.setText(str2);
                container2 = holder.container;
                holder.add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int number = MainActivity.ddlShared.getInt("number",0);
                        if(number < MainActivity.DDLMAXNUMBER){
                            Message message = new Message();
                            message.what = 2;
                            MainActivity.handler.sendMessage(message);
                        }else{
                            Toast.makeText(MainActivity.baseContext,"你的DDL有点太多啦...",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                //排序
                ArrayList<Item2> item2ArrayList = new ArrayList<>();
                item2ArrayList.clear();
                for(int id = 0; id < MainActivity.DDLMAXNUMBER; id++){
                    if(!MainActivity.ddlShared.getString("content"+id,"").equals("")){
                        int year = MainActivity.ddlShared.getInt("year"+id,0);
                        int month = MainActivity.ddlShared.getInt("month"+id,0);
                        int day = MainActivity.ddlShared.getInt("day"+id,0);
                        int hour = MainActivity.ddlShared.getInt("hour"+id,0);
                        int minute = MainActivity.ddlShared.getInt("minute"+id,0);
                        item2ArrayList.add(new Item2(MainActivity.ddlShared.getString("content"+id,""),year,month,day,hour,minute,id));
                    }
                }
                Collections.sort(item2ArrayList);

                //初始化
                for(int i = 0; i < item2ArrayList.size(); i++){
                    final LeftItem2 item = new LeftItem2(MainActivity.baseContext,container2);
                    View v = item.view;
                    final TextView title = v.findViewById(R.id.leftItemTitle2);
                    final TextView contentOverTime = v.findViewById(R.id.leftItemContent21);
                    final TextView contentLeftTime = v.findViewById(R.id.leftItemContent22);
                    final Button button = v.findViewById(R.id.leftItemComplete2);
                    final int inputId = item2ArrayList.get(i).id;
                    String str3 = "DDL名称：" + item2ArrayList.get(i).content;
                    title.setText(str3);
                    Calendar now = Calendar.getInstance();
                    now.set(Calendar.YEAR,item2ArrayList.get(i).year);
                    now.set(Calendar.MONTH,item2ArrayList.get(i).month-1);
                    now.set(Calendar.DAY_OF_MONTH,item2ArrayList.get(i).day);
                    now.set(Calendar.HOUR_OF_DAY,item2ArrayList.get(i).hour);
                    now.set(Calendar.MINUTE,item2ArrayList.get(i).minute);
                    now.set(Calendar.SECOND,0);
                    Calendar standardTime = Calendar.getInstance();
                    standardTime.set(Calendar.SECOND,0);
                    long diff = now.getTime().getTime() - standardTime.getTime().getTime();
                    long days = diff / (1000 * 60 * 60 * 24);
                    long hours = (diff-days*(1000 * 60 * 60 * 24))/(1000* 60 * 60);
                    long minutes = (diff-days*(1000 * 60 * 60 * 24)-hours*(1000* 60 * 60))/(1000* 60);
                    String str4 = item2ArrayList.get(i).year+"年"+item2ArrayList.get(i).month+"月"+item2ArrayList.get(i).day+"日"+item2ArrayList.get(i).hour+"时"+item2ArrayList.get(i).minute+"分";contentOverTime.setText("结束时间："+str4);
                    if(diff >= 0){
                        String str5 = days+"天"+hours+"小时"+minutes+"分钟";
                        contentLeftTime.setText("剩余时间："+str5);
                    }else{
                        contentLeftTime.setText("已过期");
                    }
                    contentLeftTime.setTextColor(days > 0? Color.parseColor("black"):(hours > 12? Color.parseColor("magenta"):Color.parseColor("red")));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.baseContext2);
                            dialog.setTitle("DDL管理");//标题
                            dialog.setMessage("想对该DDL进行什么操作？");//正文
                            dialog.setCancelable(true);//是否能点击屏幕取消该弹窗
                            dialog.setPositiveButton("完成了！", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int number = MainActivity.ddlShared.getInt("number",0);
                                    Message msg = new Message();
                                    msg.what = 3;
                                    MainActivity.handler.sendMessage(msg);
                                    SharedPreferences.Editor editor = MainActivity.ddlShared.edit();
                                    editor.putString("content"+inputId,"");
                                    editor.putInt("year"+inputId,0);
                                    editor.putInt("month"+inputId,-1);
                                    editor.putInt("day"+inputId,0);
                                    editor.putInt("hour"+inputId,0);
                                    editor.putInt("minute"+inputId,0);
                                    editor.putInt("number",number-1);
                                    editor.apply();
                                    container2.removeView(item);
                                    MainActivity.change(0);
                                }});
                            dialog.setNegativeButton("本次完成，七天后继续", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Message msg = new Message();
                                    msg.what = 3;
                                    MainActivity.handler.sendMessage(msg);
                                    SharedPreferences s = MainActivity.ddlShared;
                                    Calendar now = Calendar.getInstance();
                                    now.set(Calendar.YEAR,s.getInt("year"+inputId,0));
                                    now.set(Calendar.MONTH,s.getInt("month"+inputId,0)-1);
                                    now.set(Calendar.DAY_OF_MONTH,s.getInt("day"+inputId,0));
                                    now.set(Calendar.HOUR_OF_DAY,s.getInt("hour"+inputId,0));
                                    now.set(Calendar.MINUTE,s.getInt("minute"+inputId,0));
                                    now.set(Calendar.SECOND,0);
                                    now.add(Calendar.DAY_OF_MONTH,7);
                                    SharedPreferences.Editor editor = MainActivity.ddlShared.edit();
                                    editor.putInt("year"+inputId,now.get(Calendar.YEAR));
                                    editor.putInt("month"+inputId,(now.get(Calendar.MONTH)+1));
                                    editor.putInt("day"+inputId,now.get(Calendar.DAY_OF_MONTH));
                                    editor.apply();
                                    MainActivity.change(0);
                                }});
                            dialog.setNeutralButton("本次完成，一个月后继续", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Message msg = new Message();
                                    msg.what = 3;
                                    MainActivity.handler.sendMessage(msg);
                                    SharedPreferences s = MainActivity.ddlShared;
                                    Calendar now = Calendar.getInstance();
                                    now.set(Calendar.YEAR,s.getInt("year"+inputId,0));
                                    now.set(Calendar.MONTH,s.getInt("month"+inputId,0)-1);
                                    now.set(Calendar.DAY_OF_MONTH,s.getInt("day"+inputId,0));
                                    now.set(Calendar.HOUR_OF_DAY,s.getInt("hour"+inputId,0));
                                    now.set(Calendar.MINUTE,s.getInt("minute"+inputId,0));
                                    now.set(Calendar.SECOND,0);
                                    now.add(Calendar.MONTH,1);
                                    SharedPreferences.Editor editor = MainActivity.ddlShared.edit();
                                    editor.putInt("year"+inputId,now.get(Calendar.YEAR));
                                    editor.putInt("month"+inputId,(now.get(Calendar.MONTH)+1));
                                    editor.apply();
                                    MainActivity.change(0);
                                }
                            });
                            dialog.show();
                        }
                    });
                    container2.addView(item);
                }
                break;
            default:

        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }

}

class Item2 implements Comparable{
    public String content;
    public int year;
    int month;
    int day;
    int hour;
    int minute;
    int id;
    public Item2(String content, int year, int month, int day, int hour, int minute, int id){
        this.content = content;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.id = id;
    }

    @Override
    public int compareTo(Object o) {
        Item2 o2 = (Item2) o;
        if(o2.year > this.year){
            return -1;
        }else if(o2.year < this.year){
            return 1;
        }else{
            if(o2.month > this.month){
                return -1;
            }else if(o2.month < this.month) {
                return 1;
            }else{
                if(o2.day > this.day){
                    return -1;
                }else if(o2.day < this.day) {
                    return 1;
                }else{
                    if(o2.hour > this.hour){
                        return -1;
                    }else if(o2.hour < this.hour) {
                        return 1;
                    }else{
                        if(o2.minute > this.minute){
                            return -1;
                        }else if(o2.minute < this.minute) {
                            return 1;
                        }else{
                            return 0;
                        }
                    }
                }
            }
        }
    }
}
