package com.dtds.mobileplatform.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


/*****************************************
 * @description:UI操作辅助类
 * @author:lixiaohui
 * @date: 2017/10/25
 * @company:深圳动态网络科技有限公司
 *****************************************/
public class UiHelper {
    /**
     * 设置TextView文本走马灯
     *
     * @param textView
     */
    public static void setTextViewMarquee(TextView textView) {
        textView.setMarqueeRepeatLimit(3);
        textView.setSingleLine(true);
        textView.setFocusable(true);
        textView.setFocusableInTouchMode(true);
        textView.requestFocus();
        textView.setHorizontallyScrolling(true);
        textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
    }

    @NonNull
    public static Spannable createTextColorSpannable(String allText, String colorText, @ColorInt int color) {
        if (TextUtils.isEmpty(colorText) || TextUtils.isEmpty(allText)) {
            return null;
        }
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(color);
        Spannable spannable = new SpannableStringBuilder(allText);
        spannable.setSpan(colorSpan, spannable.length() - colorText.length(), spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    @NonNull
    public static Spannable createTextSizeSpannable(String allText, String sizeText, @ColorInt int size) {
        if (TextUtils.isEmpty(sizeText) || TextUtils.isEmpty(allText)) {
            return null;
        }
        SpannableStringBuilder spannable = new SpannableStringBuilder(allText);
        spannable.setSpan(new AbsoluteSizeSpan(size, true), spannable.length() - sizeText.length(), spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

    public static void toast(Context context, String msg) {
        toast(context, msg, Toast.LENGTH_SHORT, Gravity.CENTER, 0, 0);
    }

    private static void toast(Context context, String msg, int duration,
                              int gravity, int xoffset, int yoffset) {
        if (context == null || TextUtils.isEmpty(msg)) {
            return;
        }
        if (context instanceof Activity && ((Activity) context).isFinishing()) {//如果窗口已经关闭就不必要弹出toast了
            return;
        }
        Toast toast = Toast.makeText(context, msg, duration);
        toast.setGravity(gravity, xoffset, yoffset);
        toast.show();
    }

    public static void toast(Context context, int id) {
        if (context != null) {
            toast(context, context.getString(id));
        }
    }

    /**
     * try get host activity from view.
     * views hosted on floating window like dialog and toast will sure return null.
     *
     * @return host activity; or null if not available
     */
    public static Activity getActivityFromView(View view) {
        Context context = view.getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }

    public static Size getScreenSize(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        return new Size(dm.widthPixels, dm.heightPixels);
    }

}
