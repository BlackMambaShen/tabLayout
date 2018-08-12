package com.example.liang.tablayout;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp_content;
    private TabLayout tab;
    private MyAdapter myAdapter;
    private String[] arr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();
        vp_content.setAdapter(myAdapter);
        tab.setupWithViewPager(vp_content);
    }

    private void initData() {
       arr=new String[]{"巨乳","翘臀","大屌","大奶","小嘴"};
    }

    private void initUI() {
         vp_content =(ViewPager) findViewById(R.id.vp_content);
         tab =(TabLayout) findViewById(R.id.tab);
         tab.setTabTextColors(Color.WHITE,Color.RED);
          myAdapter=new MyAdapter(getSupportFragmentManager());
    }

    class MyAdapter extends FragmentPagerAdapter{

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment fragment = FragmentFactory.createFragment(position);
            return fragment;
        }

        public CharSequence getPageTitle(int position) {
            return arr[position];
        }

        @Override
        public int getCount() {
            return arr.length;
        }
    }
}
