package com.bignerdranch.glass.nerd2048;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GameAdapter extends BaseAdapter {

    private Mode mMode;
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
        mMode = Mode.NUMBER;
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

    public int getItemIdInt(int position) {
        return ((int) getItemId(position));
    }

    private enum Mode {
        NUMBER,
        NERD
    }

    public Mode getMode() {
        return mMode;
    }

    public void switchMode() {
        if (mMode == Mode.NUMBER) {
            mMode = Mode.NERD;
            convertToNerdMode();
        } else {
            mMode = Mode.NUMBER;
            convertToNumberMode();
        }
    }

    private void convertToNerdMode() {
        for (int i = 0; i < mImageArray.length; i++) {
            int drawable = getItemIdInt(i);
            switch (drawable) {
                case R.drawable.image_8192:
                    mImageArray[i] = R.drawable.image_8192_nerd;
                    break;
                case R.drawable.image_4096:
                    mImageArray[i] = R.drawable.image_4096_nerd;
                    break;
                case R.drawable.image_2048:
                    mImageArray[i] = R.drawable.image_2048_nerd;
                    break;
                case R.drawable.image_1024:
                    mImageArray[i] = R.drawable.image_1024_nerd;
                    break;
                case R.drawable.image_512:
                    mImageArray[i] = R.drawable.image_512_nerd;
                    break;
                case R.drawable.image_256:
                    mImageArray[i] = R.drawable.image_256_nerd;
                    break;
                case R.drawable.image_128:
                    mImageArray[i] = R.drawable.image_128_nerd;
                    break;
                case R.drawable.image_64:
                    mImageArray[i] = R.drawable.image_64_nerd;
                    break;
                case R.drawable.image_32:
                    mImageArray[i] = R.drawable.image_32_nerd;
                    break;
                case R.drawable.image_16:
                    mImageArray[i] = R.drawable.image_16_nerd;
                    break;
                case R.drawable.image_8:
                    mImageArray[i] = R.drawable.image_8_nerd;
                    break;
                case R.drawable.image_4:
                    mImageArray[i] = R.drawable.image_4_nerd;
                    break;
                case R.drawable.image_2:
                    mImageArray[i] = R.drawable.image_2_nerd;
                    break;
                default:
                    mImageArray[i] = R.drawable.image_none;
                    break;
            }
        }
    }

    private void convertToNumberMode() {
        for (int i = 0; i < mImageArray.length; i++) {
            int drawable = getItemIdInt(i);
            switch (drawable) {
                case R.drawable.image_8192:
                    mImageArray[i] = R.drawable.image_8192;
                    break;
                case R.drawable.image_4096:
                    mImageArray[i] = R.drawable.image_4096;
                    break;
                case R.drawable.image_2048:
                    mImageArray[i] = R.drawable.image_2048;
                    break;
                case R.drawable.image_1024:
                    mImageArray[i] = R.drawable.image_1024;
                    break;
                case R.drawable.image_512:
                    mImageArray[i] = R.drawable.image_512;
                    break;
                case R.drawable.image_256:
                    mImageArray[i] = R.drawable.image_256;
                    break;
                case R.drawable.image_128:
                    mImageArray[i] = R.drawable.image_128;
                    break;
                case R.drawable.image_64:
                    mImageArray[i] = R.drawable.image_64;
                    break;
                case R.drawable.image_32:
                    mImageArray[i] = R.drawable.image_32;
                    break;
                case R.drawable.image_16:
                    mImageArray[i] = R.drawable.image_16;
                    break;
                case R.drawable.image_8:
                    mImageArray[i] = R.drawable.image_8;
                    break;
                case R.drawable.image_4:
                    mImageArray[i] = R.drawable.image_4;
                    break;
                case R.drawable.image_2:
                    mImageArray[i] = R.drawable.image_2;
                    break;
                default:
                    mImageArray[i] = R.drawable.image_none;
                    break;
            }
        }
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