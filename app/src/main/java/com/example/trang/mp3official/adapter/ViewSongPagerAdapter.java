package com.example.trang.mp3official.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.trang.mp3official.fragment.ContenSongFragment;
import com.example.trang.mp3official.fragment.PhotoSongFragment;
import com.example.trang.mp3official.fragment.PlayerFragment;

/**
 * Created by Trang on 4/18/2017.
 */

public class ViewSongPagerAdapter extends FragmentStatePagerAdapter {
    private PlayerFragment playerFragment = new PlayerFragment();
    private PhotoSongFragment photoSongFragment = new PhotoSongFragment();
    private ContenSongFragment contenSongFragment = new ContenSongFragment();

    public ViewSongPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = playerFragment;
        switch (position) {
            case 0:
                fragment = photoSongFragment;
                break;
            case 1:
                fragment = playerFragment;

                break;
            case 2:
                fragment = contenSongFragment;
                break;

        }
        return fragment;

    }

    @Override
    public int getCount() {
        return 3;
    }
}
