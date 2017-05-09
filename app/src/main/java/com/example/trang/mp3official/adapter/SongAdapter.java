package com.example.trang.mp3official.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.trang.mp3official.R;
import com.example.trang.mp3official.song.Song;

import java.util.ArrayList;

/**
 * Created by Trang on 4/18/2017.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongHolder> {
    private ArrayList<Song> arrayList;
    private Context mContext;
    private onItemClickListner onItemClickListner;

    public void setOnItemClickListner(onItemClickListner ClickListner) {
        this.onItemClickListner = ClickListner;
    }

    public SongAdapter(ArrayList<Song> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }


    @Override
    public SongAdapter.SongHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SongHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song, parent, false));
    }

    @Override
    public void onBindViewHolder(final SongAdapter.SongHolder holder, int position) {
        holder.tvIdSong.setText(arrayList.get(position).getIndex()+" ");
        holder.tvNameSong.setText(arrayList.get(position).getTitel());
        holder.tvNameAuthor.setText(arrayList.get(position).getAuthor());
        holder.llItemSong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListner.onItemClick(holder.getAdapterPosition());

            }
        });

    }

    @Override
    public int getItemCount() {
        return (arrayList != null) ? arrayList.size() : 0;
    }

    public class SongHolder extends RecyclerView.ViewHolder {
        private LinearLayout llItemSong;
        private TextView tvNameSong;
        private TextView tvNameAuthor;
        private TextView tvIdSong;

        public SongHolder(View itemView) {
            super(itemView);
            llItemSong = (LinearLayout) itemView.findViewById(R.id.ll_item_song);
            tvIdSong = (TextView) itemView.findViewById(R.id.tv_id_song);
            tvNameAuthor = (TextView) itemView.findViewById(R.id.tv_name_author);
            tvNameSong = (TextView) itemView.findViewById(R.id.tv_name_song);
        }
    }
    public interface onItemClickListner {
        void onItemClick(int posisson);
    }
}
