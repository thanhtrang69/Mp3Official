package com.example.trang.mp3official.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.trang.mp3official.R;
import com.example.trang.mp3official.adapter.ViewSongPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager pager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

    }

    private void initView() {

        pager = (ViewPager) findViewById(R.id.viewpagre);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();
        ViewSongPagerAdapter songPagerAdapter = new ViewSongPagerAdapter(fragmentManager);
        pager.setAdapter(songPagerAdapter);
        pager.setCurrentItem(2);
        tabLayout.setupWithViewPager(pager);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(songPagerAdapter);

    }



}
