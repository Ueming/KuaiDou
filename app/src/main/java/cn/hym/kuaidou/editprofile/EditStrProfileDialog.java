package cn.hym.kuaidou.editprofile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import cn.hym.kuaidou.R;
import cn.hym.kuaidou.widget.TransParentDialog;


/**
 * Created by Administrator on 2017/4/3.
 */

public class EditStrProfileDialog extends TransParentDialog {
    private TextView titleView;
    private EditText contentView;

    public EditStrProfileDialog(Activity activity) {
        super(activity);
        View mainView = LayoutInflater.from(activity).inflate(R.layout.dialog_edit_str_profile, null, false);
        titleView = (TextView) mainView.findViewById(R.id.title);
        contentView = (EditText) mainView.findViewById(R.id.content);
        setContentView(mainView);

        setWidthAndHeight(activity.getWindow().getDecorView().getWidth() * 80 / 100, WindowManager.LayoutParams.WRAP_CONTENT);
        mainView.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String content = contentView.getText().toString();
                if (onOKListener != null) {
                    onOKListener.onOk(mTitle, content);
                }

                hide();
            }
        });
    }

    private String mTitle;

    public void show(String title, int resId, String defaultContent) {
        mTitle = title;
        titleView.setText("请输入" + title);

        contentView.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0);
        contentView.setText(defaultContent);
        show();
    }

    private OnOKListener onOKListener;

    public void setOnOKListener(OnOKListener l) {
        onOKListener = l;
    }

    public interface OnOKListener {
        void onOk(String title, String content);
    }
}
