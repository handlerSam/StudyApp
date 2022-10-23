package com.example.studyapp;

import android.app.AlertDialog;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

public class AddBlockService extends Service {
    public static boolean flag4 = false;
    public SharedPreferences pref;

    @Override
    public void onCreate() {
        super.onCreate();
        pref = getSharedPreferences("itemNumber",MODE_PRIVATE);
        new ThreadService4().start();
        addBlock();
    }

    //0删除 1草地 2泥土 3石头 4圆石 5沙子 6沙砾 7树木 8木头 9工作台
    //10火炉 11TNT 12红砖 13书架 50基岩 14箱子 15梯子 16岩浆 17树叶
    //18青金石 19煤炭 20钻石 21黄金 22绿宝石 23铁矿 24红石 25南瓜
    //26南瓜灯 27水
    public AddBlockService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class ThreadService4 extends Thread{
        public volatile boolean exit = false;
        public void run() {
            Log.v("Sam","addThreadOn");
            while (!exit){
                if(AddBlockService.flag4){
                    Log.v("Sam","addThreadOff");
                    stopSelf();
                    exit = true;
                }
            }
        }
    }

    private void addBlock(){
        /*极品：5% 1个：4
                11TNT  20钻石 21黄金 22绿宝石 24红石 25南瓜  26南瓜灯
        稀有：15% 3个：3 12 17
                9工作台 10火炉 13书架 14箱子 16岩浆 18青金石  23铁矿
        普通：30% 6个：2 9 10 14 16 18
                4圆石 7树木 12红砖 15梯子 17树叶 19煤炭 27水
        低等：50% 10个：0 1 5 6 7 8 11 13 15 19
                1草地 2泥土 3石头 5沙子 8木头 6沙砾
        */
        int rand = (int)(Math.random()*20);
        int rand2 = (int)(Math.random()*1000);
        SharedPreferences.Editor editor;
        String id;
        int number;
        switch(rand){
            //低等：
            case 0: case 1: case 5: case 6: case 7:
            case 8: case 11: case 13: case 15: case 19:
                switch((rand2-rand)%6){
                    case 0://1草地
                        id = "1";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 1://2泥土
                        id = "2";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 2://3石头
                        id = "3";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 3://5沙子
                        id = "5";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 4://8木头
                        id = "8";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 5://6沙砾
                        id = "6";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                }
                break;
            //普通：
            case 2: case 9: case 10: case 14: case 16: case 18:
                switch((rand2-rand)%7){
                    case 0://4圆石
                        id = "4";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 1://7树木
                        id = "7";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 2://12红砖
                        id = "12";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 3://15梯子
                        id = "15";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 4://17树叶
                        id = "17";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 5://19煤炭
                        id = "19";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 6://27水
                        id = "27";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                }
                break;
            //稀有：
            case 3: case 12: case 17:
                switch((rand2-rand)%7){
                    case 0://9工作台
                        id = "9";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 1://10火炉
                        id = "10";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 2://13书架
                        id = "13";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 3://14箱子
                        id = "14";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 4://16岩浆
                        id = "16";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 5://18青金石
                        id = "18";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 6://23铁矿
                        id = "23";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                }
                break;
            //极品：
            case 4:
                switch((rand2-rand)%7){
                    case 0://11TNT
                        id = "11";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 1://20钻石
                        id = "20";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 2://21黄金
                        id = "21";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 3://22绿宝石
                        id = "22";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 4://24红石
                        id = "24";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 5://25南瓜
                        id = "25";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                    case 6://26南瓜灯
                        id = "26";
                        number = pref.getInt(id,0);
                        editor = pref.edit();
                        editor.putInt(id,number+1);
                        editor.apply();
                        sendAlert(id);
                        break;
                }
                break;
        }
    }

    private void sendAlert(String id){
        //0删除 1草地 2泥土 3石头 4圆石 5沙子 6沙砾 7树木 8木头 9工作台
        //10火炉 11TNT 12红砖 13书架 50基岩 14箱子 15梯子 16岩浆 17树叶
        //18青金石 19煤炭 20钻石 21黄金 22绿宝石 23铁矿 24红石 25南瓜
        //26南瓜灯 27水
        String name = "";
        switch(id){
            case "1":
                name = "草地";
                break;
            case "2":
                name = "泥土";
                break;
            case "3":
                name = "石头";
                break;
            case "4":
                name = "圆石";
                break;
            case "5":
                name = "沙子";
                break;
            case "6":
                name = "沙砾";
                break;
            case "7":
                name = "树木";
                break;
            case "8":
                name = "木头";
                break;
            case "9":
                name = "工作台";
                break;
            case "10":
                name = "火炉";
                break;
            case "11":
                name = "TNT";
                break;
            case "12":
                name = "红砖";
                break;
            case "13":
                name = "书架";
                break;
            case "14":
                name = "箱子";
                break;
            case "15":
                name = "梯子";
                break;
            case "16":
                name = "岩浆";
                break;
            case "17":
                name = "树叶";
                break;
            case "18":
                name = "青金石";
                break;
            case "19":
                name = "煤炭";
                break;
            case "20":
                name = "钻石";
                break;
            case "21":
                name = "黄金";
                break;
            case "22":
                name = "绿宝石";
                break;
            case "23":
                name = "铁矿";
                break;
            case "24":
                name = "红石";
                break;
            case "25":
                name = "南瓜";
                break;
            case "26":
                name = "南瓜灯";
                break;
            case "27":
                name = "水";
                break;
        }
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.baseContext2).setTitle("恭喜！")
                .setMessage("恭喜你获得了"+name+"x1!")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AddBlockService.flag4 = true;
                    }
                })
                .create();
        dialog.show();

    }
}
