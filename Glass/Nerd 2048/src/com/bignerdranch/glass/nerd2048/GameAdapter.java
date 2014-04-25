package com.bignerdranch.glass.nerd2048;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GameAdapter extends BaseAdapter {

    private Context mContext;

    public View getView(int position, View convertView, ViewGroup parent) {
        return getImage(position, convertView, parent);
    }

    private ImageView getImage(int position, View convertView, ViewGroup parent) {
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

    public void setImage(int position, int image) {
        mImageArray[position] = image;
        notifyDataSetChanged();
    }

    public void clearImages() {
        for (int i = 0; i < mImageArray.length; i++) {
            mImageArray[i] = R.drawable.image_none;
        }
        notifyDataSetChanged();
    }

    public GameAdapter(Context c) {
        mContext = c;
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

    private int[] mImageArray = {
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