package com.bignerdranch.glass.nerd2048;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
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
            imageView.setLayoutParams(new GridView.LayoutParams(80, 80, Gravity.CENTER));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setBackgroundColor(mContext.getResources().getColor(R.color.black));
            imageView.setPadding(5, 5, 5, 5);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mImageArray[position]);
        return imageView;
    }

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public void actionLeft() {
        Log.i("ImageAdapter", "left");
    }

    public void actionRight() {
        Log.i("ImageAdapter", "right");
    }

    public void actionUp() {
        Log.i("ImageAdapter", "up");
    }

    public void actionDown() {
        Log.i("ImageAdapter", "down");
    }

    @Override
    public int getCount() {
        return mImageArray.length;
    }

    @Override
    public ImageView getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return mImageArray[position];
    }

    private Integer[] mImageArray = {
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none,
            R.drawable.image_none
    };

}