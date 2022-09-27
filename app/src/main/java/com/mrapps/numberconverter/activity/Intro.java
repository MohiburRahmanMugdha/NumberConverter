package com.mrapps.numberconverter.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.mrapps.numberconverter.R;
import com.mrapps.numberconverter.adapter.SlideViewPagerAdapter;

public class Intro extends AppCompatActivity {

    public static ViewPager viewPager;
    SlideViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        viewPager=findViewById(R.id.viewpager);
        adapter=new SlideViewPagerAdapter(this);
        viewPager.setAdapter(adapter);


    }
}