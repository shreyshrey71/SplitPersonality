package com.android.splitpersonality.Splash_Screen;

import androidx.appcompat.widget.AppCompatTextView;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class typewriter extends TextView {
    private CharSequence mText;
    private int index;
    private  long delay=100;
    public typewriter(Context context) {
        super(context);
    }

    public typewriter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }
    private Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, index++));
            if (index <= mText.length()) {
                mHandler.postDelayed(characterAdder, delay);
            }
        }
    };
    public void animateText(CharSequence txt) {
        mText = txt;
        index = 0;
        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, delay);
    }
    public void setCharacterDelay(long m) {
        delay = m;
    }
}