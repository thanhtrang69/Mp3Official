package com.example.trang.mp3official.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.trang.mp3official.R;
import com.example.trang.mp3official.img.Image;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Trang on 4/18/2017.
 */

public class PhotoSongFragment extends Fragment {
    private View view;
    private CircleImageView imageView;
    private Animation animation;
    private List<Image> imageList;
    private TextView textView;

    public PhotoSongFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_photo_song, container, false);
        initData();
        initView();

        return view;
    }

    private void initData() {
        imageList = new ArrayList<Image>();
        imageList.add(new Image(R.drawable.bi, " Bích Phương"));
        imageList.add(new Image(R.drawable.chidan, " Chi Dân"));
        imageList.add(new Image(R.drawable.huong, " Hồ Quỳnh Hương"));
        imageList.add(new Image(R.drawable.trung, " Hồ Việt Trung"));
        imageList.add(new Image(R.drawable.trang, " Lương Minh Trang"));
        imageList.add(new Image(R.drawable.dinhvu, " Nguyễn Đình Vũ"));
        imageList.add(new Image(R.drawable.khacviet, " Khắc Việt"));
        imageList.add(new Image(R.drawable.soda, " Dj SoDa"));
        imageList.add(new Image(R.drawable.phuong, " Khánh Phương"));
        imageList.add(new Image(R.drawable.soobin, " Soobin Hoàng Sơn"));
        imageList.add(new Image(R.drawable.hieu, " Hồ Quang Hiếu"));
        imageList.add(new Image(R.drawable.huy, " Ngô Kiến Huy"));
        imageList.add(new Image(R.drawable.miule, " Miu Lê"));
        imageList.add(new Image(R.drawable.thi, " Bảo Thi"));
        imageList.add(new Image(R.drawable.phong, " Châu Khải Phong"));
        imageList.add(new Image(R.drawable.hang, " Minh Hằng"));
        imageList.add(new Image(R.drawable.tien, " Thủy Tiên"));
        imageList.add(new Image(R.drawable.tiencookies, " Tiên Cookies"));
    }

    private void initView() {
        Random random = new Random();
//        Bitmap bitmap = BitmapFactory.decodeFile();
//        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
//        circularImageView.setImageBitmap(circularBitmap);
        int po = random.nextInt(imageList.size());
        ImageView circularImageView = (ImageView) view.findViewById(R.id.imageView);
        textView = (TextView) view.findViewById(R.id.tv_name_Songer);
        circularImageView.setImageResource(imageList.get(po).getImg());
        textView.setText(imageList.get(po).getName());
        animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_img_song);
        imageView = (CircleImageView) view.findViewById(R.id.imageView);
        imageView.startAnimation(animation);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation.cancel();
            }
        });

    }


}

