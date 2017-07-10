package com.flyzend.baseproject.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by 王灿 on 2017/1/10.
 */

public class ViewUtil {
    public static int dp2px(Context context , int dp){
        return  (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,dp,
                context.getResources().getDisplayMetrics());
    }
    public static int sp2px(Context context,int sp){
        return  (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,sp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * dp 转 px
     * @param context
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * px 转 sp
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * sp 转 px
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 将colorId 转换成ResId
     * @param mContext
     * @param color
     * @return
     */
    public static int getColor(Context mContext,int color) {
        return mContext.getResources().getColor(color);
    }
}
