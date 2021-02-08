package com.example.phonecalltv;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.security.PrivilegedAction;

public class PhoneWindowManager {
    /**
     * 显示的view
     */
    private static View mFloatWindowView;


    /**
     * 悬浮窗View的参数
     */
    private static WindowManager.LayoutParams bigWindowParams;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;

    /**
     * 用于获取手机可用内存
     */
    private static ActivityManager mActivityManager;



    /**
     * 创建一个悬浮窗。位置为屏幕正中间。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void createBigWindow(Context context,String name) {
        WindowManager windowManager = getWindowManager(context);
        if (mFloatWindowView == null) {
            LayoutInflater inflate = LayoutInflater.from(context);
            mFloatWindowView = inflate.inflate(R.layout.alert, null);
            if (bigWindowParams == null) {
                bigWindowParams = new WindowManager.LayoutParams();
                bigWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                // 悬浮窗不可触摸，不接受任何事件,同时不影响后面的事件响应，就是提示作用
                bigWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                bigWindowParams.gravity= Gravity.CENTER;
                bigWindowParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                bigWindowParams.height = 600;
                bigWindowParams.format = PixelFormat.RGBA_8888;

            }
            //TODO 根据来电号码设置视图数据
            TextView textView = mFloatWindowView.findViewById(R.id.name);
            textView.setText(name);
            windowManager.addView(mFloatWindowView, bigWindowParams);
        }
    }

    /**
     * 将悬浮窗从屏幕上移除。
     *
     * @param context
     *            必须为应用程序的Context.
     */
    public static void removeBigWindow(Context context) {
        if (mFloatWindowView != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(mFloatWindowView);
            mFloatWindowView = null;
        }
    }


    /**
     * 是否有悬浮窗显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return mFloatWindowView != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context
     *            必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    /**
     * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
     *
     * @param context
     *            可传入应用程序上下文。
     * @return ActivityManager的实例，用于获取手机可用内存。
     */
    private static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context
     *            可传入应用程序上下文。
     * @return 当前可用内存。
     */
    private static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }

}
