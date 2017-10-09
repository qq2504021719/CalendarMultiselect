package com.bignerdranch.android.calendarmultiselect;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class CalendarMultiSelectActivity extends AppCompatActivity {

    public Context mContext;

    public final String TAG = "日历";

    // 选择年
    public String mXuanYear = "2017";
    // 选择月
    public String mXuanMonth = "02";

    // 开始日期
    public String mKaiSiRiQi = "01";
    // 结束日期
    public String mJieShuRiQi = "31";

    // 月初周几号
    public int mZhouJi = 0;

    // 月末周几
    public int mMoZhouJi = 0;

    // 日历父节点
    public LinearLayout mLinear_ri_li;

    // 选择年
    public TextView mTextView_nian;
    // 年数据
    public String[] stringsNian = new String[2];

    // 选择月
    public TextView mTextView_yue;
    // 月数据
    public String[] stringsyue = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};

    // 当天
    public TextView mTextView_jing_ri;

    // 选择器
    private AlertDialog alertDialog1;

    // 1 编辑模式 0 显示模式
    public int mMoShi = 1;

    // 已选择数据
    HashMap<String,HashMap<String,HashMap<String,String>>> myiXuanZheData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        // 组件初始化
        ViewInit();

        // 值操作
        value();

        // 组件操作
        // 组件操作, 操作
        ZhuJianCaoZhuo();
    }

    /**
     * 组件组件初始化
     */
    public void ViewInit(){
        mLinear_ri_li = (LinearLayout)findViewById(R.id.linear_ri_li);
        // 年
        mTextView_nian = (TextView)findViewById(R.id.textView_nian);
        // 月
        mTextView_yue = (TextView)findViewById(R.id.textView_yue);
        // 当前日
        mTextView_jing_ri = (TextView)findViewById(R.id.textView_jing_ri);
    }

    /**
     * 组件操作
     */
    public void ZhuJianCaoZhuo(){
        // 选择年
        mTextView_nian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
                alertBuilder.setItems(stringsNian, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int index) {
                        mXuanYear = stringsNian[index];
                        // 视图创建,选择年月显示
                        ViewShowNianYue();
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = alertBuilder.create();
                alertDialog1.show();
            }
        });
        // 选择月
        mTextView_yue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
                alertBuilder.setItems(stringsyue, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int index) {
                        mXuanMonth = stringsyue[index];
                        // 视图创建,选择年月显示
                        ViewShowNianYue();
                        alertDialog1.dismiss();
                    }
                });
                alertDialog1 = alertBuilder.create();
                alertDialog1.show();

            }
        });

        // 今日
        mTextView_jing_ri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                value();
            }
        });

    }

    /**
     * 设置年 月显示
     */
    public void nianYueShow(String nianStr,String yueStr){
        mTextView_nian.setText(nianStr+"年");
        mTextView_yue.setText(yueStr+"月");
    }

    /**
     * 设置默认年月
     */
    public void setMoRenNianYue(){

        // 开始日期
        mKaiSiRiQi = getFirstDayOfMonth(Integer.valueOf(mXuanYear),Integer.valueOf(mXuanMonth));
        // 结束日期
        mJieShuRiQi = getLastDayOfMonth(Integer.valueOf(mXuanYear),Integer.valueOf(mXuanMonth));
        // 月初周几
        mZhouJi = getWeekOfDate(mXuanYear+"-"+mXuanMonth+"-"+mKaiSiRiQi);
        // 月末周几
        mMoZhouJi = getWeekOfDate(mXuanYear+"-"+mXuanMonth+"-"+mJieShuRiQi);
    }

    /**
     * 值操作
     */
    public void value(){
        // 当前年赋值
        stringsNian[0] = getYearMonth(1);
        // 下一年赋值
        stringsNian[1] = String.valueOf(Integer.valueOf(getYearMonth(1))+1);
        // 当前年
        mXuanYear = getYearMonth(1);
        // 当前月
        mXuanMonth = getYearMonth(2);
        // 设置默认年月
        setMoRenNianYue();
        // 视图创建,选择年月显示
        ViewShowNianYue();
    }

    /**
     * 视图创建,选择年月显示
     */
    public void ViewShowNianYue(){
        // 设置年月
        setMoRenNianYue();
        // 显示当前年,月
        nianYueShow(mXuanYear,mXuanMonth);

        // 创建布局
        CreateBuJu();
    }

    // 创建日历
    public void CreateBuJu(){
        // 清空内容
        mLinear_ri_li.removeAllViews();


        // 最后一天
        int jieshu = Integer.valueOf(mJieShuRiQi);
        // 数组长度
        int arrnum = jieshu+mZhouJi+6-mMoZhouJi;
        // 构造显示数组
        String[] strings = new String[arrnum];
        // 月初评接
        for(int i = 0;i<=mZhouJi;i++){
            strings[i] = "";
        }
        // 本身显示日期
        for(int i = 1;i<=jieshu;i++){
            strings[(mZhouJi+i)-1] = ""+i;
        }
        // 月末评接
        for(int i = 1;i<=6-mMoZhouJi;i++){
            strings[jieshu+mZhouJi-1+i] = "";
        }
        CreateView(strings);
    }

    /**
     * 创建视图
     * @param strings
     */
    public void CreateView(String[] strings){
        int num = 0;

        LinearLayout waiBuZuJian = CreateLinearLayout(1);

        for(int i = 0;i<strings.length;i++){
            if(strings[i] != null){
                LinearLayout neiBuZuJian = CreateLinearLayout(2);
                TextView textView = CreateTextView(strings[i],0,0,0);
                // 查看是否创建背景色
                if(ValueIs(strings[i])){
                    textView = CreateTextView(strings[i],1,R.color.baise,R.drawable.ri_qi_background);
                }

                neiBuZuJian.addView(textView);
                waiBuZuJian.addView(neiBuZuJian);
                num++;
                if(num == 7){
                    mLinear_ri_li.addView(waiBuZuJian);
                    waiBuZuJian = CreateLinearLayout(1);
                    num = 0;
                }else if((strings.length-1) == i){
                    mLinear_ri_li.addView(waiBuZuJian);
                    waiBuZuJian = CreateLinearLayout(1);
                }
            }
        }
    }

    public boolean ValueIs(String string){
        if(myiXuanZheData.get(mXuanYear) != null){
            if(myiXuanZheData.get(mXuanYear).get(mXuanMonth) != null){
                if(myiXuanZheData.get(mXuanYear).get(mXuanMonth).get(string) != null){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param string
     * @return 当前日期是星期几
     */
    public int getWeekOfDate(String string) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int[] weekDays = {0,1,2,3,4,5,6};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 获取指定的年的月的第一天
     * @param year
     * @param month
     * @return
     */
    public String getFirstDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
        return new SimpleDateFormat( "dd").format(cal.getTime());
    }

    /**
     * 获取指定的年的月的最后一天
     * @param year
     * @param month
     * @return
     */
    public String getLastDayOfMonth(int year, int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month-1);
        cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat( "dd").format(cal.getTime());
    }

    /**
     * 返回年 月
     * @param is 1 返回年 2返回月
     * @return
     */
    public String getYearMonth(int is){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
        if(is == 1){
            return new SimpleDateFormat( "yyyy").format(cal.getTime());
        }else if(is == 2){
            return new SimpleDateFormat( "MM").format(cal.getTime());
        }
        return null;
    }

    /**
     * 创建textview
     * @param string 显示内容
     * @param leix 1 选中状态 0 为选中状态
     * @param color 标题颜色
     * @param backgr 背景资源
     * @return
     */
    public TextView CreateTextView(String string, int leix, int color,int backgr){
        TextView textView = new TextView(mContext);
        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(68,68);

        textView.setLayoutParams(layoutParam);
        textView.setText(string);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(5,5,5,5);
        textView.setTextColor(getResources().getColor(R.color.huise6));

        if(leix == 1 && string != ""){
            textView.setTextColor(getResources().getColor(color));
            textView.setBackground(getResources().getDrawable(backgr));
        }

        if(mMoShi == 1){
            final String XinShiStr = string;
            final TextView textView1 = textView;
            textView.setOnClickListener(new View.OnClickListener() {
                // 是否点击 1 点击 0 未点击
                int isDianJi = 0;
                @Override
                public void onClick(View view) {
                    if(ValueIs(XinShiStr)){
                        textView1.setTextColor(getResources().getColor(R.color.huise6));
                        textView1.setBackground(getResources().getDrawable(R.drawable.ri_qi_background1));
                        // 删除存储的数据
                        myiXuanZheData.get(mXuanYear).get(mXuanMonth).remove(XinShiStr);
                    }else{
                        Log.i(TAG,XinShiStr);
                        textView1.setTextColor(getResources().getColor(R.color.baise));
                        textView1.setBackground(getResources().getDrawable(R.drawable.ri_qi_background));
                        // 存储选择




                        // 对应年月入存在

                        if(myiXuanZheData.get(mXuanYear) != null && myiXuanZheData.get(mXuanYear).get(mXuanMonth) != null){
                            myiXuanZheData.get(mXuanYear).get(mXuanMonth).put(XinShiStr,XinShiStr);
                        }else{
                            HashMap<String,String> hashMap = new HashMap<>();
                            hashMap.put(XinShiStr,XinShiStr);
                            HashMap<String,HashMap<String,String>> hashMap1 = new HashMap<>();
                            hashMap1.put(mXuanMonth,hashMap);
                            // 存储到二维数组
                            if(myiXuanZheData.get(mXuanYear) != null){
                                myiXuanZheData.get(mXuanYear).put(mXuanMonth,hashMap);
                            }else{
                                myiXuanZheData.put(mXuanYear,hashMap1);
                            }

                        }


                    }
                }
            });
        }


        return textView;
    }

    /**
     * 创建布局
     * @param leix 1外部行布局  2内部布局
     * @return
     */
    public LinearLayout CreateLinearLayout(int leix){
        LinearLayout linearLayout = new LinearLayout(mContext);

        LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);

        if(leix == 2){
            layoutParam = new LinearLayout.LayoutParams(0,LinearLayout.LayoutParams.MATCH_PARENT,1);
        }


        linearLayout.setLayoutParams(layoutParam);

        if(leix == 1){
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            linearLayout.setPadding(0,30,0,30);
            linearLayout.setBackground(getResources().getDrawable(R.drawable.bottom_border));
        }else if(leix == 2){
            linearLayout.setGravity(Gravity.CENTER);
        }

        return linearLayout;
    }
}
