package cn.hym.kuaidou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import cn.hym.kuaidou.R;

/**
 * Created by Administrator on 2018/4/26.
 */

public class BottomControlView extends RelativeLayout{

    private ImageView optionView;
    private ImageView giftView;

    public BottomControlView(Context context) {
        super(context);
        init();
    }

    public BottomControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BottomControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_bottom_control, this, true);
        findAllViews();
    }

    public void setIsHost(boolean isHost) {
        if (isHost) {
            giftView.setVisibility(INVISIBLE);
            optionView.setVisibility(VISIBLE);
        } else {
            optionView.setVisibility(INVISIBLE);
            giftView.setVisibility(VISIBLE);
        }
    }

    private void findAllViews() {
        findViewById(R.id.chat).setOnClickListener(clickListener);
        findViewById(R.id.close).setOnClickListener(clickListener);
        giftView = (ImageView) findViewById(R.id.gift);
        giftView.setOnClickListener(clickListener);
        optionView = (ImageView) findViewById(R.id.option);
        optionView.setOnClickListener(clickListener);
    }

    private OnClickListener clickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.chat) {
                // 显示聊天操作栏
                if (mOnControlListener != null) {
                    mOnControlListener.onChatClick();
                }
            } else if (view.getId() == R.id.close) {
                // 关闭直播
                if (mOnControlListener != null) {
                    mOnControlListener.onCloseClick();
                }
            } else if (view.getId() == R.id.gift) {
                // 显示礼物选择九宫格
                if (mOnControlListener != null) {
                    mOnControlListener.onGiftClick();
                }
            } else if (view.getId() == R.id.option) {
                if (mOnControlListener != null) {
                    mOnControlListener.onOptionClick(view);
                }
            }

        }
    };

    private OnControlListener mOnControlListener;

    public void setOnControlListener(OnControlListener l) {
        mOnControlListener = l;
    }

    public interface OnControlListener {
        public void onChatClick();

        public void onCloseClick();

        public void onGiftClick();

        public void onOptionClick(View view);
    }
}
