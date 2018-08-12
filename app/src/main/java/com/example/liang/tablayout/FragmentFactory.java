package com.example.liang.tablayout;

import android.support.v4.app.Fragment;

import java.util.HashMap;

public class FragmentFactory {

    private static HashMap<Integer,BaseFragment>mFragmentMap=new HashMap<Integer, BaseFragment>();
    public static BaseFragment createFragment(int pos){
        BaseFragment fragment=mFragmentMap.get(pos);
        if (fragment==null){
            switch (pos){
                case 0:
                    fragment=new JuruFragment();
                    break;
                case 1:
                    fragment=new QiaotunFragment();
                    break;
                case 2:
                    fragment=new DadiaoFragment();
                    break;
                case 3:
                    fragment=new XiaonaiFragment();
                    break;
                case 4:
                    fragment=new XiaozuiFragment();
                    break;
            }
            mFragmentMap.put(pos,fragment);//保存在集合中
        }
        return fragment;
    }
}
