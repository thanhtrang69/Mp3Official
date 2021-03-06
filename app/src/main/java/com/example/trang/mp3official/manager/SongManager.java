package com.example.trang.mp3official.manager;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.trang.mp3official.song.Song;

import java.util.ArrayList;

/**
 * Created by Trang on 4/18/2017.
 */

public class SongManager {
    public SongManager() {
    }

    public ArrayList<Song> getAll(Context mContext) {
        Cursor cursor;
        ArrayList<Song> songArrayList = new ArrayList<>();
        cursor = mContext.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                , new String[]{MediaStore.Audio.Media.TITLE
                        , MediaStore.Audio.Media.DATA
                        , MediaStore.Audio.Media.ARTIST
                        , MediaStore.Audio.Media.DURATION
                        , MediaStore.Audio.Media.DATA
                        , MediaStore.Audio.Media._ID}, null, null, MediaStore.Audio.Media.TITLE + " ASC");
        if (cursor == null || cursor.getCount() == 0) {
            cursor.close();
            return songArrayList;
        }
        int indexTitel = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int indexData = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int indexDuration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int indextAuthor = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int indextContent= cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
        int index = 1;
        while (cursor.moveToNext()) {
            String titel = cursor.getString(indexTitel);
            String author = cursor.getString(indextAuthor);
            String content = cursor.getString(indextContent);
            Uri data = Uri.parse(cursor.getString(indexData));
            long duration = cursor.getLong(indexDuration);
            songArrayList.add(new Song(titel, index++, author, data, duration, content));
        }
        cursor.close();
        return songArrayList;
    }
}
