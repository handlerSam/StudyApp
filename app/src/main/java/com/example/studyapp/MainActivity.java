package com.example.studyapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.InputFilter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static TopTitle topTitle;
    public static BottomTitle bottomTitle;
    public static LinearLayout linearLayout;
    public static FragmentManager manager;
    public static SharedPreferences reciteShared;//content0~199,hint0~199,number0~199
    public static Intent middleAddIntent;
    public static Context baseContext;
    public static Intent intentService1;
    public static Intent intentService2;
    public static MiddleFragment middleFragment = new MiddleFragment();
    public static RightFragment rightFragment = new RightFragment();
    public static LeftFragment leftFragment = new LeftFragment();
    public static NotificationManager notificationManager;
    public static int screanWidth;
    public static int screanHeight;
    public static int densityDpi;
    public static SharedPreferences mapIdShared;//格式范例：10 8
    public static SharedPreferences itemNumberShared;//格式范例：10
    public static SharedPreferences targetShared;
    public static SharedPreferences ddlShared;
    public static SharedPreferences settingShared;//格式：int setting 0小界面 1中等界面 2大界面
    public static SharedPreferences wordReciteOrderShared;//格式：int 1
    public static Handler handler;
    public static Context baseContext2;
    public static int year;
    public static int month;
    public static int day;
    public static int hour;
    public static int minute;
    public static final int DDLMAXNUMBER = 30;
    public static int MAXWWORD = 200;//在MainActivity, FrontService, Front2Service MiddleFragment 修改
    public static Drawable[] sxList = new Drawable[239];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapIdShared = getSharedPreferences("mapId",MODE_PRIVATE);
        itemNumberShared = getSharedPreferences("itemNumber",MODE_PRIVATE);
        reciteShared = getSharedPreferences("recite",MODE_PRIVATE);
        targetShared = getSharedPreferences("target",MODE_PRIVATE);
        ddlShared = getSharedPreferences("ddl",MODE_PRIVATE);
        settingShared = getSharedPreferences("setting",MODE_PRIVATE);
        wordReciteOrderShared = getSharedPreferences("wordReciteOrder",MODE_PRIVATE);
        /*
        SharedPreferences.Editor editor = itemNumberShared.edit();
        for(int i = 1; i <= 27; i++){
            editor.putInt(String.valueOf(i),20);
        }
        editor.apply();
        */

        for(int i = 0; i <= 238; i++){
            String str;
            if(i<10){
                str = "00"+i;
            }else if(i < 100){
                str = "0"+i;
            }else{
                str = ""+i;
            }
            int id = getResources().getIdentifier("sxtest00"+str, "drawable",getPackageName());
            sxList[i] = getResources().getDrawable(id);
        }





        getSupportActionBar().hide();
        topTitle = findViewById(R.id.TopTitle);
        bottomTitle = findViewById(R.id.BottomTitle);
        linearLayout = findViewById(R.id.fragmentContainer);
        manager = getSupportFragmentManager();
        baseContext = getApplicationContext();
        intentService1 = new Intent(MainActivity.this, FrontService.class);
        intentService2 = new Intent(MainActivity.this, Front2Service.class);
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        screanWidth = metric.widthPixels;  // 屏幕宽度（像素）
        screanHeight = metric.heightPixels;  // 屏幕高度（像素）
        densityDpi = metric.densityDpi;
        baseContext2 = MainActivity.this;

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 0://左窗口的计划添加
                        final EditText editText = new EditText(MainActivity.this);
                        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(getResources().getInteger(R.integer.maxLength))});
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("添加计划")
                                .setView(editText)
                                .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if(!editText.getText().toString().equals("")){
                                                    LeftAdapter.fetchText = editText.getText().toString();
                                                    Message m = new Message();
                                                    m.what = 0;
                                                    LeftAdapter.handler.sendMessage(m);
                                                }
                                            }
                                        }
                                );
                        builder.create().show();
                        break;
                    case 1://一定概率启动addBlockService
                        int rand = (int)(Math.random()*3);
                        if(rand != 0){
                            Intent intent = new Intent(MainActivity.this,AddBlockService.class);
                            startService(intent);
                        }
                        break;
                    case 2://启动添加DDL的Alert
                        final AddTime addTime = new AddTime(MainActivity.this);
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("添加DDL")
                                .setView(addTime)
                                .setPositiveButton("添加", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                EditText edit = addTime.view.findViewById(R.id.ddlTitle);
                                                if(!edit.getText().toString().equals("")){
                                                    EditText year = addTime.view.findViewById(R.id.addYear);
                                                    EditText month = addTime.view.findViewById(R.id.addMonth);
                                                    EditText day = addTime.view.findViewById(R.id.addDay);
                                                    EditText hour = addTime.view.findViewById(R.id.addHour);
                                                    EditText minute = addTime.view.findViewById(R.id.addMinute);
                                                    if((!year.getText().toString().equals("")) && !month.getText().toString().equals("") && !day.getText().toString().equals("") && !hour.getText().toString().equals("") && !minute.getText().toString().equals("")){
                                                        if(Integer.parseInt(year.getText().toString()) >= MainActivity.year){
                                                            if(Integer.parseInt(month.getText().toString()) >= 1 && Integer.parseInt(month.getText().toString()) <= 12){
                                                                if(Integer.parseInt(day.getText().toString()) >= 1 && Integer.parseInt(day.getText().toString()) <= (getMonthNumber(Integer.parseInt(year.getText().toString()),Integer.parseInt(month.getText().toString())))){
                                                                    if(Integer.parseInt(hour.getText().toString()) >= 0 && Integer.parseInt(hour.getText().toString()) <= 23){
                                                                        if(Integer.parseInt(minute.getText().toString()) >= 0 && Integer.parseInt(minute.getText().toString()) <= 60){
                                                                            LeftAdapter.fetchText = edit.getText().toString();
                                                                            LeftAdapter.fetchYear = year.getText().toString();
                                                                            LeftAdapter.fetchMonth = month.getText().toString();
                                                                            LeftAdapter.fetchDay = day.getText().toString();
                                                                            LeftAdapter.fetchHour = hour.getText().toString();
                                                                            LeftAdapter.fetchMinute = minute.getText().toString();
                                                                            Message m = new Message();
                                                                            m.what = 1;
                                                                            LeftAdapter.handler.sendMessage(m);
                                                                        }else{
                                                                            Toast.makeText(MainActivity.this,"分钟格式不对哦",Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }else{
                                                                        Toast.makeText(MainActivity.this,"小时格式不对哦",Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }else{
                                                                    Toast.makeText(MainActivity.this,"日期格式不对哦",Toast.LENGTH_SHORT).show();
                                                                }
                                                            }else{
                                                                Toast.makeText(MainActivity.this,"月份格式不对哦",Toast.LENGTH_SHORT).show();
                                                            }
                                                        }else{
                                                            Toast.makeText(MainActivity.this,"年份格式不对哦",Toast.LENGTH_SHORT).show();
                                                        }
                                                    }else{
                                                        Toast.makeText(MainActivity.this,"时间不能为空啦！",Toast.LENGTH_SHORT).show();
                                                    }
                                                }else{
                                                    Toast.makeText(MainActivity.this,"内容不能为空啦！",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                );
                        builder2.create().show();
                        break;
                    case 3://一定启动addBlockService
                        Intent intent = new Intent(MainActivity.this,AddBlockService.class);
                        startService(intent);
                        break;
                    case 4://弹出帮助菜单
                        switch(BottomTitle.nowInMenu){
                            case 0:
                                String str0 = getResources().getString(R.string.leftActivity);
                                AlertDialog.Builder builder3 = new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(str0 + "功能提示：")
                                        .setCancelable(true)
                                        .setMessage(R.string.help0);
                                builder3.create().show();
                                break;
                            case 1:
                                String str1 = getResources().getString(R.string.middleActivity);
                                AlertDialog.Builder builder4 = new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(str1+"功能提示：")
                                        .setCancelable(true)
                                        .setMessage(R.string.help1);
                                builder4.create().show();
                                break;
                            case 2:
                                String str2 = getResources().getString(R.string.rightActivity);
                                AlertDialog.Builder builder5 = new AlertDialog.Builder(MainActivity.this)
                                        .setTitle(str2 + "功能提示：")
                                        .setCancelable(true)
                                        .setMessage(R.string.help2);
                                builder5.create().show();
                                break;
                        }
                        break;
                    case 5://重新启动活动
                        restartReciteService();
                        break;
                }
            }
        };

        boolean allEmpty = true;
        boolean firstUsedOrderList = true;
        for(int i = 0; i <= MAXWWORD-1; i++){
            if(reciteShared.getString("content"+i,"") != ""){
                allEmpty = false;
            }
            if(wordReciteOrderShared.getInt(""+i, 0) != 0){
                firstUsedOrderList = false;
            }
        }
        if(firstUsedOrderList){
            disorderList();
        }

        if(allEmpty == false){
            startService(intentService1);
        }

        middleAddIntent = new Intent(MainActivity.this,AddMiddleActivity.class);
        topTitle.addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(BottomTitle.nowInMenu == 1){
                    if(MiddleFragment.mlist.size()<=MAXWWORD-1){
                        startActivityForResult(middleAddIntent,1);
                    }else{
                        Toast.makeText(MainActivity.this,"要背的东西太多了啦！一次只能背200个哦！",Toast.LENGTH_SHORT).show();
                    }
                }else{//在rightFragment清屏
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("确定清除所有方块？")
                            .setMessage("所有方块会被放回仓库。")
                            .setCancelable(true)
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            for(int i = 0; i < RightFragment.WIDTHNUMBER; i++){
                                                for(int j = 1; j < RightFragment.HEIGHTNUMBER;j++){
                                                    if(mapIdShared.getInt(i+" "+j,0) != 0){
                                                        int blockId = mapIdShared.getInt(i+" "+j,0);
                                                        SharedPreferences.Editor editor = mapIdShared.edit();
                                                        editor.putInt(i+" "+j,0);
                                                        editor.apply();
                                                        int formerNumber = itemNumberShared.getInt(""+blockId,0);
                                                        SharedPreferences.Editor editor1 = itemNumberShared.edit();
                                                        editor1.putInt(""+blockId,formerNumber+1);
                                                        editor1.apply();
                                                    }
                                                }
                                            }
                                            change(2);
                                            Toast.makeText(MainActivity.baseContext2,"已清屏",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            )
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                    builder.create().show();
                }
            }
        });
        change(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            switch(requestCode){
                case 1:
                    SharedPreferences.Editor editor = reciteShared.edit();
                    for(int i = 0; i <= MAXWWORD-1; i++){
                        //Log.v("Sam",""+i);
                        if (MiddleFragment.available[i]) {
                            MiddleFragment.reciteString[i][0] = data.getStringExtra("content");
                            MiddleFragment.reciteString[i][1] = data.getStringExtra("hint");
                            MiddleFragment.reciteInt[i] = 0;
                            MiddleFragment.available[i] = false;

                            editor.putString("content"+i,MiddleFragment.reciteString[i][0]);
                            editor.putString("hint"+i,MiddleFragment.reciteString[i][1]);
                            editor.putInt("number"+i,0);
                            editor.apply();

                            MiddleFragment.mlist.add(new MiddleRecyclerItem(MiddleFragment.reciteString[i][0],MiddleFragment.reciteString[i][1],MiddleFragment.reciteInt[i],i));
                            MiddleFragment.middleAdapter.notifyItemRangeChanged(0,MiddleFragment.mlist.size());
                            boolean allEmpty = true;
                            for(int j = 0; j <= MAXWWORD-1; j++){
                                if(reciteShared.getString("content"+j,"") != ""){
                                    allEmpty = false;
                                }
                            }

                            if(allEmpty == false){
                                Intent intent = new Intent(MainActivity.this, FrontService.class);
                                startService(intent);
                            }
                            break;
                        }
                    }
                    boolean flag = data.getBooleanExtra("two", false);
                    if(flag){
                        for(int i = 0; i <= MAXWWORD-1; i++){
                            //Log.v("Sam",""+i);
                            if (MiddleFragment.available[i]) {
                                MiddleFragment.reciteString[i][1] = data.getStringExtra("content");
                                MiddleFragment.reciteString[i][0] = data.getStringExtra("hint");
                                MiddleFragment.reciteInt[i] = 0;
                                MiddleFragment.available[i] = false;

                                editor.putString("content"+i,MiddleFragment.reciteString[i][0]);
                                editor.putString("hint"+i,MiddleFragment.reciteString[i][1]);
                                editor.putInt("number"+i,0);
                                editor.apply();

                                MiddleFragment.mlist.add(new MiddleRecyclerItem(MiddleFragment.reciteString[i][0],MiddleFragment.reciteString[i][1],MiddleFragment.reciteInt[i],i));
                                MiddleFragment.middleAdapter.notifyItemRangeChanged(0,MiddleFragment.mlist.size());
                                boolean allEmpty = true;
                                for(int j = 0; j <= MAXWWORD-1; j++){
                                    if(reciteShared.getString("content"+j,"") != ""){
                                        allEmpty = false;
                                    }
                                }

                                if(allEmpty == false){
                                    Intent intent = new Intent(MainActivity.this, FrontService.class);
                                    startService(intent);
                                }
                                break;
                            }
                        }
                    }
                    break;
                default:
            }
        }
    }

    public static void change(int id){
        switch(id){
            case 0:
                FragmentTransaction transaction0= manager.beginTransaction();
                leftFragment = new LeftFragment();
                transaction0.replace(R.id.fragmentContainer, leftFragment);
                transaction0.commit();
                break;
            case 1:
                FragmentTransaction transaction1= manager.beginTransaction();
                middleFragment = new MiddleFragment();
                transaction1.replace(R.id.fragmentContainer,middleFragment);
                transaction1.commit();
                break;
            case 2:
                FragmentTransaction transaction2= manager.beginTransaction();
                rightFragment = new RightFragment();
                transaction2.replace(R.id.fragmentContainer,rightFragment);
                transaction2.commit();
                break;

        }
    }
    public static void disorderList(){
        int[] a = new int[MAXWWORD];
        for(int i = 0; i < MAXWWORD; i++){
            a[i] = i;
        }
        a = changeOrderList(a);
        SharedPreferences.Editor editor = wordReciteOrderShared.edit();
        for(int i = 0; i < MAXWWORD; i++){
            editor.putInt(String.valueOf(i),a[i]);
        }
        editor.apply();
    }
    public static int[] changeOrderList(int[] a){
        for(int i = 0; i <= MAXWWORD*2; i++){
            int sjs1 = (int)(Math.random()*MAXWWORD);
            int sjs2 = (int)(Math.random()*MAXWWORD);
            int temp = a[sjs1];
            a[sjs1] = a[sjs2];
            a[sjs2] = temp;
        }
        return a;
    }
    public void restartReciteService(){
        Intent intent = new Intent(MainActivity.baseContext2, Front3Service.class);
        startService(intent);
        Intent intent2 = new Intent(MainActivity.baseContext2, FrontService.class);
        startService(intent2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v("Sam","MainOnDestory");
    }

    public static int getMonthNumber(int year, int month){
        if(month == 2){
            return (year%4 == 0)? 29:28;
        }else{
            switch(month){
                case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                    return 31;
                default:
                    return 30;
            }
        }
    }
}

class MiddleRecyclerItem{
    String content;
    String hint;
    int number;
    int id;
    public MiddleRecyclerItem(String content,String hint,int number,int id){
        this.content = content;
        this.hint = hint;
        this.number = number;
        this.id = id;
    }
}

class RightRecyclerItem{
    int itemId;
    int itemNumber;
    public RightRecyclerItem(int itemId,int itemNumber){
        this.itemId = itemId;
        this.itemNumber = itemNumber;
    }
}
