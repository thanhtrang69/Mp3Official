package com.example.trang.mp3official.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trang.mp3official.R;
import com.example.trang.mp3official.manager.SongManager;
import com.example.trang.mp3official.song.Song;

import java.util.ArrayList;

/**
 * Created by Trang on 4/18/2017.
 */

public class ContenSongFragment extends Fragment {
    private View view;
    private ArrayList<Song> arrayList;
    private SongManager manager;
    private TextView textView;

    public ContenSongFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_content_song, container, false);
        initView();
        return view;
    }

    private void initView() {

    }
}
