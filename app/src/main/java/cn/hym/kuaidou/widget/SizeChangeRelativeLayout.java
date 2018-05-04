package cn.hym.kuaidou.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by Administrator.
 */

public class SizeChangeRelativeLayout extends RelativeLayout {

    public SizeChangeRelativeLayout(Context context) {
        super(context);
    }

    public SizeChangeRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public SizeChangeRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mOnSizeChangeListener == null) {
            return;
        }
        if (h > oldh) {
            //画面变长，键盘隐藏
            mOnSizeChangeListener.onLarge();
        } else {
            //画面变短，键盘显示
            mOnSizeChangeListener.onSmall();
        }
    }

    private OnSizeChangeListener mOnSizeChangeListener;

    public void setOnSizeChangeListener(OnSizeChangeListener l) {
        mOnSizeChangeListener = l;
    }

    public interface OnSizeChangeListener {
        public void onLarge();

        public void onSmall();
    }
}
