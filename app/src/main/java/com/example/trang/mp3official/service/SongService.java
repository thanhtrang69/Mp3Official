package com.example.trang.mp3official.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;

import com.example.trang.mp3official.manager.SongManager;
import com.example.trang.mp3official.song.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Trang on 4/18/2017.
 */

public class SongService extends Service implements MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener {
    private static final String ACTION_STOP = "com.example.trang.mp3official.service.STOP";
    private static final String ACTION_PAUSE = "com.example.trang.mp3official.service.PAUSE";
    private static final String ACTION_NEXT = "com.example.trang.mp3official.service.NEXT";
    private static final String ACTION_PREVIOUS = "com.example.trang.mp3official.service.PREVIOUS";
    private static final String ACTION_REPEAT = "com.example.trang.mp3official.service.REPEAT";
    private static final String ACTION_RANDOM = "com.example.trang.mp3official.service.RANDOM";
    private static final int STATE_PAUSE = 1;
    private ArrayList<Song> arrayList;
    private MediaPlayer mediaPlayer;
    private Uri mUri;
    private int state = 0;
    private int pos = 0;
    private boolean repeat;
    private Handler handler = new Handler();
    private int mediaPosition;
    private int mediaMax;
    public static final String ACTION_BROASCAST = "com.example.trang.mp3official.service.BROASCAST";
    private Intent seekIntent;
    private static int songEnded;
    private SongManager manager;
    private int b = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        seekIntent = new Intent(ACTION_BROASCAST);
        manager = new SongManager();
        mediaPlayer.setOnSeekCompleteListener(this);
        arrayList = manager.getAll(this);
        setupHandler();
    }

    public IBinder onBind(Intent intent) {

        return new ServiceBinder();
    }


    public void playPauseSong() {
        mediaPlayer.pause();
    }

    public void porogess(int a) {
        b = a;
        mediaPlayer.seekTo(a);
    }


    public SongService() {

    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {
        porogess(b);
    }


    public class ServiceBinder extends Binder {
        public SongService getSongService() {
            return SongService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if (action.equals(ACTION_NEXT)) {
                nextSong();
            } else if (action.equals(ACTION_PREVIOUS)) {
                previousSong();
            } else if (action.equals(ACTION_STOP)) {
                stopSong();
                stopSelf();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void setupHandler() {
        handler.removeCallbacks(sendUpdatesToUI);
        handler.postDelayed(sendUpdatesToUI, 500);
    }

    private Runnable sendUpdatesToUI = new Runnable() {
        @Override
        public void run() {
            LogMediaPosition();
            handler.postDelayed(this, 1000);
        }
    };

    private void LogMediaPosition() {
        if (mediaPlayer.isPlaying()) {
            mediaPosition = mediaPlayer.getCurrentPosition();
            mediaMax = mediaPlayer.getDuration();
            seekIntent.putExtra("counter", String.valueOf(mediaPosition));
            seekIntent.putExtra("mediamax", String.valueOf(mediaMax));
            seekIntent.putExtra("songEnded", String.valueOf(songEnded));
            sendBroadcast(seekIntent);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        return false;
    }


    public void randomSong(final int posR) {
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Random rd = new Random();
                startSong(arrayList.get(rd.nextInt(posR)).getData());
            }
        });
    }

    public void repeatSong(boolean repeat) {
        this.repeat = repeat;
        mediaPlayer.setLooping(repeat);
    }

    public boolean isRepeat() {
        return repeat;
    }

    public void stopSong() {
        mediaPlayer.stop();

    }

    public void previousSong() {
        startSong(arrayList.get(pos - 1).getData());
        pos--;
    }

    public void nextSong() {
        startSong(arrayList.get(pos + 1).getData());
        pos++;
    }

    public void selecteSong(int po) {
        setUri(arrayList.get(po).getData());
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }

    public void setSongList(ArrayList<Song> listSong) {
        this.arrayList = listSong;
    }

    public void startSong(Uri songUri) {
        mediaPlayer.reset();
        mUri = songUri;
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(getApplicationContext(), mUri);
            mediaPlayer.prepare();
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mediaPlayer.start();

                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    nextSong();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(sendUpdatesToUI);
    }
}
