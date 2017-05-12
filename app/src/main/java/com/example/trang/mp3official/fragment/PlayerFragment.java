package com.example.trang.mp3official.fragment;

import android.app.Service;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.trang.mp3official.R;
import com.example.trang.mp3official.adapter.SongAdapter;
import com.example.trang.mp3official.manager.SongManager;
import com.example.trang.mp3official.service.SongService;
import com.example.trang.mp3official.song.Song;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Trang on 4/19/2017.
 */

public class PlayerFragment extends Fragment implements View.OnClickListener, SongAdapter.onItemClickListner {
    public static final String POSISSON = "posisson";
    private static int songEnded = 0;
    boolean mBroadcastIsRegistered;
    boolean mBufferBroadcastIsRegistered;
    private RecyclerView recyclerView;
    private View view;
    private TextView tvNameSong;
    private TextView tvNameAuthor;
    private TextView tvTimeFinish;
    private TextView tvTimeStart;
    private ImageButton tvCheck;
    private ImageButton imgClock;
    private ImageButton imgRandom;
    private ImageButton imgBack;
    private ImageButton imgPlay;
    private ImageButton imgStop;
    private ImageButton imgNext;
    private ImageButton imgRepeat;
    private Song songItem;
    private SongService songService;
    private ServiceConnection serviceConnection;
    private boolean isConnected;
    private SeekBar seekBar;
    private ArrayList<Song> arrayList;
    private int pos = 0;
    private PlayerFragment playerFragment;
    private SongManager manager;
    private SongAdapter adapter;
    private int seekMax;
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent serviceIntent) {
            updaUI(serviceIntent);
        }
    };
    private boolean isClickClock;

    public PlayerFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_player_song, container, false);
        initView();
        connectService();
        return view;
    }

    private void initView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        manager = new SongManager();
        arrayList = manager.getAll(getActivity());
        adapter = new SongAdapter(arrayList, getActivity());
        adapter.setOnItemClickListner(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        imgBack = (ImageButton) view.findViewById(R.id.img_previous);
        imgNext = (ImageButton) view.findViewById(R.id.img_next_song);
        imgPlay = (ImageButton) view.findViewById(R.id.img_play_song);
        imgRandom = (ImageButton) view.findViewById(R.id.img_random);
        imgRepeat = (ImageButton) view.findViewById(R.id.img_repeat_song);
        imgStop = (ImageButton) view.findViewById(R.id.img_stop_song);
        tvCheck = (ImageButton) view.findViewById(R.id.tv_edit);
        imgClock = (ImageButton) view.findViewById(R.id.img_bt_Clock);


        tvNameAuthor = (TextView) view.findViewById(R.id.tv_name_author_top);
        tvNameSong = (TextView) view.findViewById(R.id.tv_name_song_top);
        tvTimeFinish = (TextView) view.findViewById(R.id.tv_time_finish);
        tvTimeStart = (TextView) view.findViewById(R.id.tv_time_start);

        imgBack.setOnClickListener(this);
        imgPlay.setOnClickListener(this);
        imgRandom.setOnClickListener(this);
        imgRepeat.setOnClickListener(this);
        imgStop.setOnClickListener(this);
        imgNext.setOnClickListener(this);
        imgClock.setOnClickListener(this);


        seekBar = (SeekBar) view.findViewById(R.id.seekbar);

        seekBar.setClickable(false);
        seekBar.setClickable(false);
        imgPlay.setVisibility(View.VISIBLE);
        imgStop.setVisibility(View.GONE);

    }

    public void connectService() {
        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                isConnected = true;
                songService = ((SongService.ServiceBinder) service).getSongService();
                songItem = arrayList.get(0);
                long finishTimeNull = songItem.getDuration();
                tvNameSong.setText(songItem.getTitel());
                tvNameAuthor.setText(songItem.getAuthor());
                tvTimeFinish.setText(String.format("%d :%d",
                        TimeUnit.MILLISECONDS.toMinutes(finishTimeNull)
                        , TimeUnit.MILLISECONDS.toSeconds(finishTimeNull)
                                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(finishTimeNull))));

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                isConnected = false;
            }
        };
        Intent intent = new Intent(getActivity(), SongService.class);
        getActivity().bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void updaUI(Intent serviceIntent) {
        String counter = serviceIntent.getStringExtra("counter");
        final String mediamax = serviceIntent.getStringExtra("mediamax");
        int seekProgress = Integer.parseInt(counter);
        seekMax = Integer.parseInt(mediamax);
        seekBar.setMax(seekMax);
        seekBar.setProgress(seekProgress);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvTimeStart.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes(progress)
                        , TimeUnit.MILLISECONDS.toSeconds(progress) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(progress))));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                songService.porogess(seekBar.getProgress());
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                songService.porogess(seekBar.getProgress());
            }
        });

    }

    @Override
    public void onItemClick(int posisson) {
        this.pos = posisson;
        songService.startSong(arrayList.get(posisson).getData());
        imgPlay.setVisibility(View.GONE);
        imgStop.setVisibility(View.VISIBLE);
        tvNameSong.setText(arrayList.get(posisson).getTitel());
        tvNameAuthor.setText(arrayList.get(posisson).getAuthor());
        tvTimeFinish.setText(String.format("%d :%d",
                TimeUnit.MILLISECONDS.toMinutes(arrayList.get(posisson).getDuration())
                , TimeUnit.MILLISECONDS.toSeconds(arrayList.get(posisson).getDuration())
                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(arrayList.get(posisson).getDuration()))));
    }

    @Override
    public void onClick(View v) {
        manager = new SongManager();
        arrayList = manager.getAll(getActivity());
        songService.setSongList(arrayList);
        songService.selecteSong(pos);
        switch (v.getId()) {
            case R.id.img_play_song:
                if (isConnected) {
                    startedSongS();
                    getActivity().registerReceiver(broadcastReceiver, new IntentFilter(
                            SongService.ACTION_BROASCAST));
                    mBroadcastIsRegistered = true;

                }
                break;
            case R.id.img_stop_song:
                if (isConnected) {
                    songService.playPauseSong();
                    imgPlay.setVisibility(View.VISIBLE);
                    imgStop.setVisibility(View.GONE);

                }
                break;
            case R.id.img_next_song:
                if (isConnected) {
                    pos++;
                    if (pos == arrayList.size()) {
                        Toast.makeText(songService, "is Last", Toast.LENGTH_SHORT).show();
                        pos = 0;
                        songService.startSong(arrayList.get(pos).getData());

                    } else {
                        imgPlay.setVisibility(View.GONE);
                        imgStop.setVisibility(View.VISIBLE);
                        tvNameSong.setText(arrayList.get(pos).getTitel());
                        tvNameAuthor.setText(arrayList.get(pos).getAuthor());
                        tvTimeFinish.setText(String.format("%d :%d",
                                TimeUnit.MILLISECONDS.toMinutes(arrayList.get(pos).getDuration())
                                , TimeUnit.MILLISECONDS.toSeconds(arrayList.get(pos).getDuration())
                                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(arrayList.get(pos).getDuration()))));
                        songService.startSong(arrayList.get(pos).getData());
                    }

                }

                break;
            case R.id.img_previous:
                if (isConnected) {
                    pos--;
                    if (pos< 0) {
                        pos = arrayList.size()-1;
                        songService.startSong(arrayList.get(pos).getData());
                        imgPlay.setVisibility(View.GONE);
                        imgStop.setVisibility(View.VISIBLE);
                        tvNameSong.setText(arrayList.get(pos).getTitel());
                        tvNameAuthor.setText(arrayList.get(pos).getAuthor());
                        tvTimeFinish.setText(String.format("%d :%d",
                                TimeUnit.MILLISECONDS.toMinutes(arrayList.get(pos).getDuration())
                                , TimeUnit.MILLISECONDS.toSeconds(arrayList.get(pos).getDuration())
                                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(arrayList.get(pos).getDuration()))));
                    } else {

                        imgPlay.setVisibility(View.GONE);
                        imgStop.setVisibility(View.VISIBLE);
                        tvNameSong.setText(arrayList.get(pos).getTitel());
                        tvNameAuthor.setText(arrayList.get(pos).getAuthor());
                        songService.startSong(arrayList.get(pos).getData());
                        tvTimeFinish.setText(String.format("%d :%d",
                                TimeUnit.MILLISECONDS.toMinutes(arrayList.get(pos).getDuration())
                                , TimeUnit.MILLISECONDS.toSeconds(arrayList.get(pos).getDuration())
                                        - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(arrayList.get(pos).getDuration()))));

                    }

                }
                break;
            case R.id.img_random:
                Random random = new Random();
                Toast.makeText(songService, "Xáo Trộn", Toast.LENGTH_SHORT).show();
                int posR = random.nextInt(arrayList.size());
                songService.randomSong(posR);
                break;
            case R.id.img_repeat_song:
                if (isConnected) {
                    if (songService.isRepeat()) {
                        imgRepeat.setImageResource(R.drawable.ic_replay_black);
                        songService.repeatSong(false);
                    } else {
                        imgRepeat.setImageResource(R.drawable.ic_replay_on);
                        songService.repeatSong(true);
                    }

                }
                break;
            case R.id.img_bt_Clock:
                if (isConnected) {
                    if (isClickClock) {
                        imgClock.setImageResource(R.drawable.ic_alarm_black_24dp);
                        isClickClock = false;

                    } else {
                        setClickClockSong();
                        imgClock.setImageResource(R.drawable.ic_alarm_indigo_24dp);
                        isClickClock = true;
                    }

                }
                break;
            case R.id.tv_edit:
                break;
            default:
                break;
        }
    }

    public void setClickClockSong() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.show_clock_dialog, null);
        final TextView tv5 = (TextView) view.findViewById(R.id.tv_5);
        TextView tv10 = (TextView) view.findViewById(R.id.tv_10);
        TextView tv15 = (TextView) view.findViewById(R.id.tv_15);
        TextView tv20 = (TextView) view.findViewById(R.id.tv_20);
        final Calendar cal = Calendar.getInstance();
        final int minute = cal.get(Calendar.MINUTE);
        SimpleDateFormat dft = null;
        dft = new SimpleDateFormat("HH:mm", Locale.getDefault());
        tv5.setTag(dft.format(cal.getTime()));

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        //Xử lý lưu giờ và AM,PM
                        String s = hour + ":" + minute;
                        int hourTam = hour;
                        if (hourTam > 12)
                            hourTam = hourTam - 12;
                        //lưu giờ thực vào tag
                        tv5.setTag(s);
                        //lưu vết lại giờ
                        cal.set(Calendar.HOUR_OF_DAY, hour);
                        cal.set(Calendar.MINUTE, minute);

                    }
                };
                String s = tv5.getTag() + "";
                String strArr[] = s.split(":");
                int gio = Integer.parseInt(strArr[0]);
                int phut = Integer.parseInt(strArr[1]);
                TimePickerDialog time = new TimePickerDialog(
                        getActivity(),
                        callback, gio, phut, true);
                time.setTitle("Chọn giờ hoàn thành");
                time.show();
                if (minute == phut) {
                    stopedSong();
                }

            }
        });
        tv10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "10 phút", Toast.LENGTH_SHORT).show();
                TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        //Xử lý lưu giờ và AM,PM
                        String s = hour + ":" + minute;
                        int hourTam = hour;
                        if (hourTam > 12)
                            hourTam = hourTam - 12;
                        //lưu giờ thực vào tag
                        tv5.setTag(s);
                        //lưu vết lại giờ
                        cal.set(Calendar.HOUR_OF_DAY, hour);
                        cal.set(Calendar.MINUTE, minute);

                    }
                };
                String s = tv5.getTag() + "";
                String strArr[] = s.split(":");
                int gio = Integer.parseInt(strArr[0]);
                int phut = Integer.parseInt(strArr[1]);
                TimePickerDialog time = new TimePickerDialog(
                        getActivity(),
                        callback, gio, phut, true);
                time.setTitle("Chọn giờ hoàn thành");
                time.show();
                if (minute == phut) {
                    stopedSong();
                }


            }
        });
        tv15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "15 phút", Toast.LENGTH_SHORT).show();

                TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        //Xử lý lưu giờ và AM,PM
                        String s = hour + ":" + minute;
                        int hourTam = hour;
                        if (hourTam > 12)
                            hourTam = hourTam - 12;
                        //lưu giờ thực vào tag
                        tv5.setTag(s);
                        //lưu vết lại giờ
                        cal.set(Calendar.HOUR_OF_DAY, hour);
                        cal.set(Calendar.MINUTE, minute);

                    }
                };
                String s = tv5.getTag() + "";
                String strArr[] = s.split(":");
                int gio = Integer.parseInt(strArr[0]);
                int phut = Integer.parseInt(strArr[1]);
                TimePickerDialog time = new TimePickerDialog(
                        getActivity(),
                        callback, gio, phut, true);
                time.setTitle("Chọn giờ hoàn thành");
                time.show();
                if (minute == phut) {
                    stopedSong();
                }

            }
        });
        tv20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "20 phút", Toast.LENGTH_SHORT).show();

                TimePickerDialog.OnTimeSetListener callback = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        //Xử lý lưu giờ và AM,PM
                        String s = hour + ":" + minute;
                        int hourTam = hour;
                        if (hourTam > 12)
                            hourTam = hourTam - 12;
                        //lưu giờ thực vào tag
                        tv5.setTag(s);
                        //lưu vết lại giờ
                        cal.set(Calendar.HOUR_OF_DAY, hour);
                        cal.set(Calendar.MINUTE, minute);

                    }
                };
                String s = tv5.getTag() + "";
                String strArr[] = s.split(":");
                int gio = Integer.parseInt(strArr[0]);
                int phut = Integer.parseInt(strArr[1]);
                TimePickerDialog time = new TimePickerDialog(
                        getActivity(),
                        callback, gio, phut, true);
                time.setTitle("Chọn giờ hoàn thành");
                time.show();
                if (minute == phut) {
                    stopedSong();
                }

            }
        });
        builder.setView(view);
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void UnConnectService() {
        getActivity().unbindService(serviceConnection);
    }

    @Override
    public void onDestroy() {
        UnConnectService();
        super.onDestroy();

    }


    public void stopedSong() {
        songService.stopSong();
        imgPlay.setVisibility(View.VISIBLE);
        imgStop.setVisibility(View.GONE);


    }

    public void startedSongS() {
        songService.startSong(arrayList.get(pos).getData());
        imgPlay.setVisibility(View.GONE);
        imgStop.setVisibility(View.VISIBLE);

    }


    @Override
    public void onResume() {
        if (!mBufferBroadcastIsRegistered) {
            getActivity().registerReceiver(broadcastReceiver, new IntentFilter(
                    SongService.ACTION_BROASCAST
            ));
            mBufferBroadcastIsRegistered = true;
        }
        if (!mBroadcastIsRegistered) {
            getActivity().registerReceiver(broadcastReceiver, new IntentFilter(
                    SongService.ACTION_BROASCAST
            ));
            mBroadcastIsRegistered = true;
        }
        super.onResume();

    }

}
