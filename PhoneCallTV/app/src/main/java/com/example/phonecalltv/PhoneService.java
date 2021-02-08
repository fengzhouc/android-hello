package com.example.phonecalltv;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class PhoneService extends Service {

    private  boolean isAdded = false;
    private String incomingNumber;
    private ActivityManager mActivityManager;
    /**
     * 用于在线程中创建或移除悬浮窗。
     */
    private Handler handler = new Handler();
    /**
     * 定时器，定时进行检测当前应该创建还是移除悬浮窗。
     */
    private Timer timer;
    private int mPhoneState;//0 挂断 1 来电 2 通话中 3 主动拨号

    public PhoneService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        Log.e("PhoneService", "onCreate");
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("PhoneService", "onStartCommand");
        incomingNumber = intent.getStringExtra("incomingNumber");
        mPhoneState = intent.getIntExtra("state", 3); // 0 挂断，1 来电
        String name = getMessage(incomingNumber);
        // 开启定时器，每隔0.5秒刷新一次
        if (timer == null) {
            timer = new Timer();
            timer.scheduleAtFixedRate(new RefreshTask(name), 0, 500);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Service被终止的同时也停止定时器继续运行
        timer.cancel();
        timer = null;
    }

    class RefreshTask extends TimerTask {
        private String incomingNumber;
        public RefreshTask(String incomingNumber) {
            this.incomingNumber = incomingNumber;
        }

        @Override
        public void run() {
            if(!PhoneWindowManager.isWindowShowing()){
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        PhoneWindowManager.createBigWindow(getApplicationContext(),incomingNumber);
                    }
                });
            }
            // 当前界面是桌面，且有悬浮窗显示，则移除悬浮窗。
            if (mPhoneState == 0) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        PhoneWindowManager.removeBigWindow(getApplicationContext());
                        Intent intent = new Intent(getApplicationContext(), PhoneService.class);
                        getApplicationContext().stopService(intent);
                    }
                });
            }

        }

    }


    // 根据来电号码查询信息
    private String getMessage(String incomingNumber){

        return "zhoufeng";
    }



    /**
     * 判断当前界面是否是桌面
     */
    public boolean isHome(){
        if(mActivityManager == null) {
            mActivityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        }
        List<ActivityManager.RunningTaskInfo> rti = mActivityManager.getRunningTasks(1);
        return getHomes().contains(rti.get(0).topActivity.getPackageName());
    }

    /**
     * 获得属于桌面的应用的应用包名称
     * @return 返回包含所有包名的字符串列表
     */
    private List<String> getHomes() {
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        // 属性
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for(ResolveInfo ri : resolveInfo) {
            names.add(ri.activityInfo.packageName);
        }
        return names;
    }

}