package cn.hym.kuaidou.widget;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import cn.hym.kuaidou.R;

/**
 * Created by Administrator on 2018/4/28.
 */

public class HostControlDialog extends TransParentNoDimDialog {


    private TextView mBeautyView;
    private TextView mFlashView;
    private TextView mVoiceView;


    private int dialogWidth;
    private int dialogHeight;


    public HostControlDialog(Activity activity) {
        super(activity);

        View mainView = LayoutInflater.from(activity).inflate(R.layout.dialog_host_control, null, false);

        mBeautyView = (TextView) mainView.findViewById(R.id.beauty);
        mBeautyView.setOnClickListener(clickListener);
        mFlashView = (TextView) mainView.findViewById(R.id.flash_light);
        mFlashView.setOnClickListener(clickListener);
        mVoiceView = (TextView) mainView.findViewById(R.id.voice);
        mVoiceView.setOnClickListener(clickListener);

        mainView.findViewById(R.id.camera).setOnClickListener(clickListener);

        setContentView(mainView);

        int width = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int height = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        mainView.measure(width, height);
        dialogWidth = mainView.getMeasuredWidth();
        dialogHeight = mainView.getMeasuredHeight();
        setWidthAndHeight(dialogWidth, dialogHeight);

    }

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(listener != null) {
                if (v.getId() == R.id.beauty) {
                    listener.onBeautyClick();
                }else if(v.getId() == R.id.flash_light){
                    listener.onFlashClick();
                }else if(v.getId() == R.id.voice){
                    listener.onVoiceClick();
                }else if(v.getId() == R.id.camera){
                    listener.onCameraClick();
                }
            }
            hide();
        }
    };

    private OnControlClickListener listener;
    public void setOnControlClickListener(OnControlClickListener l){
        listener = l;
    }

    public interface OnControlClickListener{
        public void onBeautyClick();
        public void onFlashClick();
        public void onVoiceClick();
        public void onCameraClick();
    }

    public void updateView(boolean beautyOn, boolean flashOn, boolean voiceOn) {
        if (beautyOn) {
            mBeautyView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_beauty_on, 0, 0, 0);
            mBeautyView.setText("关美颜");
        } else {
            mBeautyView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_beauty_off, 0, 0, 0);
            mBeautyView.setText("开美颜");
        }

        if (flashOn) {
            mFlashView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_flashlight_on, 0, 0, 0);
            mFlashView.setText("关闪光");
        } else {
            mFlashView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_flashlight_off, 0, 0, 0);
            mFlashView.setText("开闪光");
        }

        if (voiceOn) {
            mVoiceView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_mic_on, 0, 0, 0);
            mVoiceView.setText("关声音");
        } else {
            mVoiceView.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.icon_mic_off, 0, 0, 0);
            mVoiceView.setText("开声音");
        }

    }

    public void show(View view) {
        //view在屏幕上的位置。
        int[] outLocation = new int[2];
        view.getLocationOnScreen(outLocation);

        Window window = dialog.getWindow();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        WindowManager.LayoutParams params = window.getAttributes();
        params.x = outLocation[0] - (dialogWidth - view.getWidth()) / 2;
        params.y = outLocation[1] - dialogHeight - view.getHeight();
        params.alpha = 0.7f;
        window.setAttributes(params);
        show();
    }
}
