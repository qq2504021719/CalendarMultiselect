package com.bignerdranch.android.calendarmultiselect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class CalendarConfig {
    // 日期选中背景色
    public static int mMoRenBeiJingSe = R.drawable.zhuti_ri_qi_background;
    // 日期选中字体颜色
    public static int mMoRenZiTiSe = R.color.baiseCalendar;
    // 默认值
    public static List<DayColor> mYiXuanZheData = new ArrayList<>();
    // 1 编辑模式 0 显示模式
    public static int mMoShi = 0;
    // 按钮背景样式
    public static int mButtonBackground = R.drawable.button;
    // 字体颜色
    public static int mButtonTextColor = R.color.baiseCalendar;
    // 按钮文字
    public static int mButtonText = R.string.fanHui;
    // 按钮字体大小
    public static int mButtonFontSize = 16;
    // 选择模式 0 多选模式 1单选模式
    public static int mDanXuanMoShi = 0;

    // 周几不可选
    public static String[] mZhouJiBuKeXuan = {"周五","周六"};
    // 周几不可选提示
    public static String mZhouJiBuKeXuanTiShi = "休息日,不可选";

    // 已过的日期是否不可选 0 可选 1不可选
    public static int mYiGuoBuKeXuan = 1;

    // 已过的日期是否不可选提示
    public static String mYiGuoBuKeXuanTiShi = "不可选";


}
