package com.bignerdranch.glass.nerd2048;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    private Integer[] mThumbIds = {
            R.drawable.image_2,
            R.drawable.image_4,
            R.drawable.image_8,
            R.drawable.image_16,
            R.drawable.image_32,
            R.drawable.image_64,
            R.drawable.image_128,
            R.drawable.image_256,
            R.drawable.image_512,
            R.drawable.image_1024,
            R.drawable.image_2048,
            R.drawable.image_4096,
            R.drawable.image_8192
    };
}