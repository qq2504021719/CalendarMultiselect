package com.bignerdranch.android.calendarmultiselect;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 */

public class MainActivity extends AppCompatActivity {

    // 返回标识
    public final static int REQUEST_PHOTO = 0;

    // 选择
    public Button mXuan_ze;
    // 选择内容
    public TextView mXuan_ze_nei_rong;
    // 默认选中
    public Button mMo_ren_xuan_zhong;
    // 默认选中数组
    List<DayColor> mMoRenData = new ArrayList<>();

    public Context mContext;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_z);

        mContext = this;

        // 组件初始化
        ViewInit();

        // 值操作
        value();

        // 组件操作, 操作
        ZhuJianCaoZhuo();
    }

    // 组件初始化
    public void ViewInit(){
        mXuan_ze = (Button)findViewById(R.id.xuan_ze);
        // 选择内容
        mXuan_ze_nei_rong = (TextView)findViewById(R.id.xuan_ze_nei_rong);
        // 默认选中
        mMo_ren_xuan_zhong = (Button)findViewById(R.id.mo_ren_xuan_zhong);
    }

    // 值操作
    public void value(){
        // 默认选中复制 mMoRenData
        DayColor dayColor = new DayColor();
        dayColor.setDay("2017104");
        dayColor.setColor(R.drawable.zhuti_ri_qi_background);
        mMoRenData.add(dayColor);

        DayColor dayColor1 = new DayColor();
        dayColor1.setDay("20171027");
        dayColor1.setColor(R.drawable.zhuti_ri_qi_background);
        mMoRenData.add(dayColor1);
    }

    // 组件操作, 操作
    public void ZhuJianCaoZhuo(){
        // 选择日期
        mXuan_ze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 清空默认选中
                Config.mYiXuanZheData = new ArrayList<DayColor>();
                // 编辑模式
                Config.mMoShi = 1;
                // 启动
                Intent intent = new Intent(MainActivity.this, CalendarMultiSelectActivity.class);
                startActivityForResult(intent, REQUEST_PHOTO);
            }
        });

        // 默认选中
        mMo_ren_xuan_zhong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 查询模式
                Config.mMoShi = 0;
                // 默认选中赋值
                Config.mYiXuanZheData = mMoRenData;
                // 启动
                Intent intent = new Intent(MainActivity.this, CalendarMultiSelectActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 接收返回值
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 日期选择返回
        if(requestCode == REQUEST_PHOTO){
            String date = data.getStringExtra(CalendarMultiSelectActivity.mFanHuiBiao);
            mXuan_ze_nei_rong.setText("你选择了 : "+date);
        }

    }
}
