package com.example.admin.ccb.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.admin.ccb.R;

/**
 * @Author xifangzheng
 * Created by zz on 2018/1/16 13:50.
 * 　　class explain:  用于获得验证码的按钮
 * 　　　　update:       upAuthor:      explain:
 */

@SuppressLint("AppCompatCustomView")
public class SendCodeTextView extends TextView {

    private CountDownTimer countDownTimer;

    public SendCodeTextView(Context context) {
        this(context, null);
    }

    public SendCodeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SendCodeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        sendCountDownTimer();
        return super.performClick();
    }

    public void sendCountDownTimer() {
//        setBackgroundResource(R.drawable.shape_bge0e0e0_radius3);
        setTextColor(getResources().getColor(R.color.color_e0e0e0));
        setClickable(false);
        if (countDownTimer != null) {
            countDownTimer = null;
        }
        countDownTimer = new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int s = (int) (millisUntilFinished / 1000);
                if (s < 10)
//                （ 60秒 ）
                    setText(" 0" + s + "秒后重发");
                else
                    setText(" " + s + "秒后重发");
            }

            @Override
            public void onFinish() {
                setText("获取验证码");
                countDownTimer.cancel();
                setClickable(true);
//                setBackgroundResource(R.drawable.shap_bgffffff_e0e0e0_radius3);
                setTextColor(getResources().getColor(R.color.color_292929));
                countDownTimer = null;
            }
        };
        countDownTimer.start();
    }
}
