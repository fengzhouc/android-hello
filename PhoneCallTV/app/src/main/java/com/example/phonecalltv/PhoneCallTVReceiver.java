package com.example.phonecalltv;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
/**
 * Created by zhoufeng on 2021/02/06.
 */
public class PhoneCallTVReceiver extends BroadcastReceiver {

    private Context mcontext;
    @Override
    public void onReceive(Context context, Intent intent){

        mcontext = context;
        Log.e("action", intent.getAction());
        if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
        //如果是去电（拨出）
            Log.e("onReceive","拨出");
        }else{
        //查了下android文档，貌似没有专门用于接收来电的action,所以，非去电即来电，这里主要是PHONE_STATE，监听电话状态改变
            Log.e("onReceive","来电");
            // 电话状态改变时，再使用TelephonyManager细化状态
            TelephonyManager tm = (TelephonyManager)context.getSystemService(Service.TELEPHONY_SERVICE);
            //设置一个监听器
            tm.listen(listener, PhoneStateListener.LISTEN_CALL_STATE);
        }
    }

    private PhoneStateListener listener=new PhoneStateListener(){
        @Override
        public void onCallStateChanged(int state, final String incomingNumber) {
            // TODO Auto-generated method stub
            // state 当前状态 incomingNumber,貌似没有去电的API
            // 注意，方法必须写在super方法后面，否则incomingNumber无法获取到值。
            super.onCallStateChanged(state, incomingNumber);
            Intent intent1 = new Intent(mcontext, PhoneService.class);

            intent1.putExtra("incomingNumber", incomingNumber);
            switch(state){
                case TelephonyManager.CALL_STATE_IDLE:
                    Log.e("onCallStateChanged","挂断");
                    // 挂断的时候移除悬浮窗
                    intent1.putExtra("state", 0);
                    mcontext.startService(intent1);
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK: // 通话中
                    Log.e("onCallStateChanged","接听");
                    break;
                case TelephonyManager.CALL_STATE_RINGING: //来电
                    /*
                     * 设置一个悬浮窗来显示来电信息
                     */
                    intent1.putExtra("state", 1);
                    mcontext.startService(intent1);
            }
        }
    };

};