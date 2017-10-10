package com.bignerdranch.android.calendarmultiselect;

/**
 * Created by Administrator on 2017/10/10.
 */

public class DayColor {
    // 是那一天
    String mDay;
    // 背景颜色
    int mColor;
    // 字体颜色
    int mFontColor;

    public DayColor(){
        mColor = R.drawable.ri_qi_background;
        mFontColor = R.color.baiseCalendar;
    }

    public void setFontColor(int fontSize) {
        mFontColor = fontSize;
    }

    public int getFontColor() {
        return mFontColor;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public void setColor(int color) {
        mColor = color;
    }

    public String getDay() {
        return mDay;
    }

    public int getColor() {
        return mColor;
    }
}
