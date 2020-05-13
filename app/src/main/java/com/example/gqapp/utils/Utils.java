package com.example.gqapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.ColorRes;
import androidx.core.content.ContextCompat;

/**
 * created by lxx at 2019/8/9 22:49
 * 描述:自定义工具类
 */
public class Utils {

    private static final String TAG = "RangeSeekBar";

    public static void print(String log) {
        Log.d(TAG, log);
    }

    public static void print(Object... logs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object log : logs) {
            stringBuilder.append(log);
        }
        Log.d(TAG, stringBuilder.toString());
    }

    public static Bitmap drawableToBitmap(Context context, int width, int height, int drawableId) {
        if (context == null || width <= 0 || height <= 0 || drawableId == 0) return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return Utils.drawableToBitmap(width, height, context.getResources().getDrawable(drawableId, null));
        } else {
            return Utils.drawableToBitmap(width, height, context.getResources().getDrawable(drawableId));
        }
    }

    /**
     * make a drawable to a bitmap
     *
     * @param drawable drawable you want convert
     * @return converted bitmap
     */
    public static Bitmap drawableToBitmap(int width, int height, Drawable drawable) {
        Bitmap bitmap = null;
        try {
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
                bitmap = bitmapDrawable.getBitmap();
                if (bitmap != null && bitmap.getHeight() > 0) {
                    Matrix matrix = new Matrix();
                    float scaleWidth = width * 1.0f / bitmap.getWidth();
                    float scaleHeight = height * 1.0f / bitmap.getHeight();
                    matrix.postScale(scaleWidth, scaleHeight);
                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                    return bitmap;
                }
            }
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
            drawable.draw(canvas);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    /**
     * draw 9Path
     *
     * @param canvas Canvas
     * @param bmp    9path bitmap
     * @param rect   9path rect
     */
    public static void drawNinePath(Canvas canvas, Bitmap bmp, Rect rect) {
        NinePatch.isNinePatchChunk(bmp.getNinePatchChunk());
        NinePatch patch = new NinePatch(bmp, bmp.getNinePatchChunk(), null);
        patch.draw(canvas, rect);
    }

    public static void drawBitmap(Canvas canvas, Paint paint, Bitmap bmp, Rect rect) {
        try {
            if (NinePatch.isNinePatchChunk(bmp.getNinePatchChunk())) {
                drawNinePath(canvas, bmp, rect);
                return;
            }
        } catch (Exception e) {
        }
        canvas.drawBitmap(bmp, rect.left, rect.top, paint);
    }

    public static int dp2px(Context context, float dpValue) {
        if (context == null || compareFloat(0f, dpValue) == 0) return 0;
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * Compare the size of two floating point numbers
     *
     * @param a
     * @param b
     * @return 1 is a > b
     * -1 is a < b
     * 0 is a == b
     */
    public static int compareFloat(float a, float b) {
        int ta = Math.round(a * 1000000);
        int tb = Math.round(b * 1000000);
        if (ta > tb) {
            return 1;
        } else if (ta < tb) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * Compare the size of two floating point numbers with accuracy
     *
     * @param a
     * @param b
     * @return 1 is a > b
     * -1 is a < b
     * 0 is a == b
     */
    public static int compareFloat(float a, float b, int degree) {
        if (Math.abs(a-b) < Math.pow(0.1, degree)) {
            return 0;
        } else {
            if (a < b) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    public static float parseFloat(String s) {
        try {
            return Float.parseFloat(s);
        } catch (NumberFormatException e) {
            return 0f;
        }
    }

    public static Rect measureText(String text, float textSize) {
        Paint paint = new Paint();
        Rect textRect = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), textRect);
        paint.reset();
        return textRect;
    }

    public static boolean verifyBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled() || bitmap.getWidth() <= 0 || bitmap.getHeight() <= 0) {
            return false;
        }
        return true;
    }

    public static int getColor(Context context, @ColorRes int colorId) {
        if (context != null) {
            return ContextCompat.getColor(context.getApplicationContext(), colorId);
        }
        return Color.WHITE;
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {//获取版本号(内部识别号)
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 保存当前版本号
     * @param context
     * @param prefName
     * @param value
     */
    public static void putAppPrefInt(Context context, String prefName, int value) {
        if(context!=null){
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt(prefName, value);
            if(Build.VERSION.SDK_INT>=9){
                edit.apply();
            }else{
                edit.commit();
            }
        }
    }

    /**
     * 获取版本信息
     *
     * @param context
     * @return
     */
    public static String getVersionDes(Context context) {//获取版本号
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取本版本SDCard
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * bite 转 byte
     */
    public static byte bitToByte(String bit){
        int re = 0,len;
        if (null == bit) {
            return 0;
        }
        len = bit.length();
        if (len != 4 && len != 8) {
            return 0;
        }
        if (len == 8) {
            if (bit.charAt(0) == '0') {
                re = Integer.parseInt(bit,2) - 256;
            }
        }else {
            re = Integer.parseInt(bit, 2);
        }
        return (byte) re;
    }

    /**
     * byte转bit 有多重方法
     */
    public static String getBit(byte by){
        StringBuffer sb = new StringBuffer();
        sb.append((by >> 7) & 0x1)
                .append((by >> 6) & 0x1)
                .append((by >> 5) & 0x1)
                .append((by >> 4) & 0x1)
                .append((by >> 3) & 0x1)
                .append((by >> 2) & 0x1)
                .append((by >> 1) & 0x1)
                .append((by >> 0) & 0x1);
        return sb.toString();
    }

    /**
     *  int转byte[]
     * @param i 该方法将一个int类型的数据转换为byte[]形式，因为int为32bit，
     *          而byte为8bit所以在进行类型转换时，知会获取低8位，
     * @return 丢弃高24位。通过位移的方式，将32bit的数据转换成4个8bit的数据。
     * 注意 &0xff，在这当中，&0xff简单理解为一把剪刀，
     * 将想要获取的8位数据截取出来。
     */
    public static byte[] int2ByteArray(int i){
        byte[] result = new byte[3];
        result[0] = (byte) ((i >> 24) & 0xFF);
        result[1] = (byte) ((i >> 16) & 0xFF);
        result[2] = (byte) ((i >> 8) & 0xFF);
        result[3]=(byte)(i & 0xFF);
        return result;
    }

    /**
     * byte[]转int
     * 利用int2ByteArray方法，将一个int转为byte[]，
     * 但在解析时，需要将数据还原。同样使用移位的方式，将适当的位数进行还原，
     * 0xFF为16进制的数据，所以在其后每加上一位，就相当于二进制加上4位。
     * 同时，使用|=号拼接数据，将其还原成最终的int数据
     */
    public static int bytes2Int(byte[] bytes) {
        int num = bytes[3] & 0xFF;
        num |=((bytes[2]<<8)& 0xFF00);
        num |=((bytes[1]<<16)& 0xFF0000);
        num |=((bytes[0]<<24)& 0xFF0000);
        return num;
    }

    /**
     * 16进制转10进制
     */
    public static int hex2decimal(String hex){
        return Integer.parseInt(hex, 16);
    }

    /**
     * 10进制转16进制
     */
    public static String demical2Hex(int i){
        String s = Integer.toHexString(i);
        return s;
    }

    /**
     * 2进制与16进制的互相转换
     */
    public static String hexStringToByte(String hex){
        int i = Integer.parseInt(hex, 16);
        String str2  = Integer.toBinaryString(i);
        return str2;
    }

    /**
     * 2进制转10进制
     */
    public static int ByteToDecimal(String bytes){
        return Integer.valueOf(bytes, 2);
    }

    /**
     * 10进制转2进制
     */
    public static String Demical2Byte(int n){
        String result = Integer.toBinaryString(n);
        return result;
    }

    /**
     * 二进制字符串转换为byte数组,每个字节以","隔开
     **/
    public static byte[] conver2HexToByte(String hex2Str) {
        String[] temp = hex2Str.split(",");
        byte[] b = new byte[temp.length];
        for (int i = 0; i < b.length; i++) {
            b[i] = Long.valueOf(temp[i], 2).byteValue();
        }
        return b;
    }

    /**
     * byte数组转换为二进制字符串,每个字节以","隔开
     **/
    public static String conver2HexStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(Long.toString(b[i] & 0xff, 2) + ",");
        }
        return result.toString().substring(0, result.length() - 1);
    }

    /**
     * float 四舍五入保留一位小数
     * @param f
     * @return
     */
    public static String strOnePointOfFloat(float f){
        return String.format("%.1f", Double.valueOf(String.valueOf(f)));
    }

}
