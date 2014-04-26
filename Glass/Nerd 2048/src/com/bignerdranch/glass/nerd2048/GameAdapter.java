package com.bignerdranch.glass.nerd2048;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GameAdapter extends BaseAdapter {

    private static final String TAG = "GameAdapter";

    private Mode mMode;
    private Context mContext;

    private int[] mImageArray;

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

    public void showAllImages() {
        if (mMode == Mode.NUMBER) {
            mImageArray[0] = R.drawable.image_2;
            mImageArray[1] = R.drawable.image_4;
            mImageArray[2] = R.drawable.image_8;
            mImageArray[3] = R.drawable.image_16;
            mImageArray[4] = R.drawable.image_32;
            mImageArray[5] = R.drawable.image_64;
            mImageArray[6] = R.drawable.image_128;
            mImageArray[7] = R.drawable.image_256;
            mImageArray[8] = R.drawable.image_512;
            mImageArray[9] = R.drawable.image_1024;
            mImageArray[10] = R.drawable.image_2048;
            mImageArray[11] = R.drawable.image_4096;
            mImageArray[12] = R.drawable.image_8192;
            mImageArray[13] = R.drawable.image_none;
            mImageArray[14] = R.drawable.image_none;
            mImageArray[15] = R.drawable.image_none;
        } else {
            mImageArray[0] = R.drawable.image_2_nerd;
            mImageArray[1] = R.drawable.image_4_nerd;
            mImageArray[2] = R.drawable.image_8_nerd;
            mImageArray[3] = R.drawable.image_16_nerd;
            mImageArray[4] = R.drawable.image_32_nerd;
            mImageArray[5] = R.drawable.image_64_nerd;
            mImageArray[6] = R.drawable.image_128_nerd;
            mImageArray[7] = R.drawable.image_256_nerd;
            mImageArray[8] = R.drawable.image_512_nerd;
            mImageArray[9] = R.drawable.image_1024_nerd;
            mImageArray[10] = R.drawable.image_2048_nerd;
            mImageArray[11] = R.drawable.image_4096_nerd;
            mImageArray[12] = R.drawable.image_8192_nerd;
            mImageArray[13] = R.drawable.image_none_nerd;
            mImageArray[14] = R.drawable.image_none_nerd;
            mImageArray[15] = R.drawable.image_none_nerd;
        }
        notifyDataSetChanged();
    }

    public GameAdapter(Context c) {
        this(c, Mode.NUMBER);
    }

    public GameAdapter(Context c, Mode mode) {
        mContext = c;
        mMode = mode;
        setupArray();
    }

    private void setupArray() {
        if (mMode == Mode.NUMBER) {
            mImageArray = new int[]{
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
        } else {
            mImageArray = new int[]{
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd,
                    R.drawable.image_none_nerd
            };
        }

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

    public enum Mode {
        NUMBER,
        NERD
    }

    public Mode getMode() {
        return mMode;
    }

    public int getDrawable_None() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_none;
        }
        return R.drawable.image_none_nerd;
    }

    public int getDrawable_2() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_2;
        }
        return R.drawable.image_2_nerd;
    }

    public int getDrawable_4() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_4;
        }
        return R.drawable.image_4_nerd;
    }

    public int getDrawable_8() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_8;
        }
        return R.drawable.image_8_nerd;
    }

    public int getDrawable_16() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_16;
        }
        return R.drawable.image_16_nerd;
    }

    public int getDrawable_32() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_32;
        }
        return R.drawable.image_32_nerd;
    }

    public int getDrawable_64() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_64;
        }
        return R.drawable.image_64_nerd;
    }

    public int getDrawable_128() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_128;
        }
        return R.drawable.image_128_nerd;
    }

    public int getDrawable_256() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_256;
        }
        return R.drawable.image_256_nerd;
    }

    public int getDrawable_512() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_512;
        }
        return R.drawable.image_512_nerd;
    }

    public int getDrawable_1024() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_1024;
        }
        return R.drawable.image_1024_nerd;
    }

    public int getDrawable_2048() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_2048;
        }
        return R.drawable.image_2048_nerd;
    }

    public int getDrawable_4096() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_4096;
        }
        return R.drawable.image_4096_nerd;
    }

    public int getDrawable_8192() {
        if (mMode == Mode.NUMBER) {
            return R.drawable.image_8192;
        }
        return R.drawable.image_8192_nerd;
    }

    public void setMode(Mode mode) {
        mMode = mode;
    }

    public void convertMode() {
        if (mMode == Mode.NUMBER) {
            convertToNumberMode();
        } else {
            convertToNerdMode();
        }
        notifyDataSetChanged();
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
                    mImageArray[i] = R.drawable.image_none_nerd;
                    break;
            }
        }
    }

    private void convertToNumberMode() {
        for (int i = 0; i < mImageArray.length; i++) {
            int drawable = getItemIdInt(i);
            switch (drawable) {
                case R.drawable.image_8192_nerd:
                    mImageArray[i] = R.drawable.image_8192;
                    break;
                case R.drawable.image_4096_nerd:
                    mImageArray[i] = R.drawable.image_4096;
                    break;
                case R.drawable.image_2048_nerd:
                    mImageArray[i] = R.drawable.image_2048;
                    break;
                case R.drawable.image_1024_nerd:
                    mImageArray[i] = R.drawable.image_1024;
                    break;
                case R.drawable.image_512_nerd:
                    mImageArray[i] = R.drawable.image_512;
                    break;
                case R.drawable.image_256_nerd:
                    mImageArray[i] = R.drawable.image_256;
                    break;
                case R.drawable.image_128_nerd:
                    mImageArray[i] = R.drawable.image_128;
                    break;
                case R.drawable.image_64_nerd:
                    mImageArray[i] = R.drawable.image_64;
                    break;
                case R.drawable.image_32_nerd:
                    mImageArray[i] = R.drawable.image_32;
                    break;
                case R.drawable.image_16_nerd:
                    mImageArray[i] = R.drawable.image_16;
                    break;
                case R.drawable.image_8_nerd:
                    mImageArray[i] = R.drawable.image_8;
                    break;
                case R.drawable.image_4_nerd:
                    mImageArray[i] = R.drawable.image_4;
                    break;
                case R.drawable.image_2_nerd:
                    mImageArray[i] = R.drawable.image_2;
                    break;
                default:
                    mImageArray[i] = R.drawable.image_none;
                    break;
            }
        }
    }

    public int getNumberFromDrawable(int drawable) {
        if (mMode == Mode.NUMBER) {
            return getNumberFromDrawable_Number(drawable);
        } else {
            return getNumberFromDrawable_Nerd(drawable);
        }
    }

    private int getNumberFromDrawable_Number(int drawable) {
        switch (drawable) {
            case R.drawable.image_8192:
                return 8192;
            case R.drawable.image_4096:
                return 4096;
            case R.drawable.image_2048:
                return 2048;
            case R.drawable.image_1024:
                return 1024;
            case R.drawable.image_512:
                return 512;
            case R.drawable.image_256:
                return 256;
            case R.drawable.image_128:
                return 128;
            case R.drawable.image_64:
                return 64;
            case R.drawable.image_32:
                return 32;
            case R.drawable.image_16:
                return 16;
            case R.drawable.image_8:
                return 8;
            case R.drawable.image_4:
                return 4;
            case R.drawable.image_2:
                return 2;
            default:
                return 0;
        }
    }

    private int getNumberFromDrawable_Nerd(int drawable) {
        switch (drawable) {
            case R.drawable.image_8192_nerd:
                return 8192;
            case R.drawable.image_4096_nerd:
                return 4096;
            case R.drawable.image_2048_nerd:
                return 2048;
            case R.drawable.image_1024_nerd:
                return 1024;
            case R.drawable.image_512_nerd:
                return 512;
            case R.drawable.image_256_nerd:
                return 256;
            case R.drawable.image_128_nerd:
                return 128;
            case R.drawable.image_64_nerd:
                return 64;
            case R.drawable.image_32_nerd:
                return 32;
            case R.drawable.image_16_nerd:
                return 16;
            case R.drawable.image_8_nerd:
                return 8;
            case R.drawable.image_4_nerd:
                return 4;
            case R.drawable.image_2_nerd:
                return 2;
            default:
                return 0;
        }
    }

    public int getNextFromDrawable(int drawable) {
        if (mMode == Mode.NUMBER) {
            return getNextFromDrawable_Number(drawable);
        } else {
            return getNextFromDrawable_Nerd(drawable);
        }
    }

    private int getNextFromDrawable_Number(int drawable) {
        switch (drawable) {
            case R.drawable.image_8192:
                return R.drawable.image_8192;
            case R.drawable.image_4096:
                return R.drawable.image_8192;
            case R.drawable.image_2048:
                return R.drawable.image_4096;
            case R.drawable.image_1024:
                return R.drawable.image_2048;
            case R.drawable.image_512:
                return R.drawable.image_1024;
            case R.drawable.image_256:
                return R.drawable.image_512;
            case R.drawable.image_128:
                return R.drawable.image_256;
            case R.drawable.image_64:
                return R.drawable.image_128;
            case R.drawable.image_32:
                return R.drawable.image_64;
            case R.drawable.image_16:
                return R.drawable.image_32;
            case R.drawable.image_8:
                return R.drawable.image_16;
            case R.drawable.image_4:
                return R.drawable.image_8;
            case R.drawable.image_2:
                return R.drawable.image_4;
            default:
                return R.drawable.image_none;
        }
    }

    private int getNextFromDrawable_Nerd(int drawable) {
        switch (drawable) {
            case R.drawable.image_8192_nerd:
                return R.drawable.image_8192_nerd;
            case R.drawable.image_4096_nerd:
                return R.drawable.image_8192_nerd;
            case R.drawable.image_2048_nerd:
                return R.drawable.image_4096_nerd;
            case R.drawable.image_1024_nerd:
                return R.drawable.image_2048_nerd;
            case R.drawable.image_512_nerd:
                return R.drawable.image_1024_nerd;
            case R.drawable.image_256_nerd:
                return R.drawable.image_512_nerd;
            case R.drawable.image_128_nerd:
                return R.drawable.image_256_nerd;
            case R.drawable.image_64_nerd:
                return R.drawable.image_128_nerd;
            case R.drawable.image_32_nerd:
                return R.drawable.image_64_nerd;
            case R.drawable.image_16_nerd:
                return R.drawable.image_32_nerd;
            case R.drawable.image_8_nerd:
                return R.drawable.image_16_nerd;
            case R.drawable.image_4_nerd:
                return R.drawable.image_8_nerd;
            case R.drawable.image_2_nerd:
                return R.drawable.image_4_nerd;
            default:
                return R.drawable.image_none_nerd;
        }
    }

}