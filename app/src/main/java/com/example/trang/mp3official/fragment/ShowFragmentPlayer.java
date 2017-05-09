package com.example.trang.mp3official.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trang.mp3official.R;

/**
 * Created by Trang on 4/19/2017.
 */

public class ShowFragmentPlayer extends Fragment {
    private View view;

    public ShowFragmentPlayer() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_show_fragment_player, container, false);

        return view;
    }
}
