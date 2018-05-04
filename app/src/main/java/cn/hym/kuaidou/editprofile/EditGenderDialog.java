package cn.hym.kuaidou.editprofile;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;

import cn.hym.kuaidou.R;
import cn.hym.kuaidou.widget.TransParentDialog;


public class EditGenderDialog extends TransParentDialog {
    private RadioButton maleView;
    private RadioButton femaleView;

    public EditGenderDialog(Activity activity) {
        super(activity);
        //sd
        View mainView = LayoutInflater.from(activity).inflate(R.layout.dialog_edit_gender, null, false);

        maleView = (RadioButton) mainView.findViewById(R.id.male);
        femaleView = (RadioButton) mainView.findViewById(R.id.female);
        mainView.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isMaleChecked = maleView.isChecked();
                if (onChangeGenderListener != null) {
                    onChangeGenderListener.onChangeGender(isMaleChecked);
                }
                hide();
            }
        });
        setContentView(mainView);
        setWidthAndHeight(activity.getWindow().getDecorView().getWidth() * 80 / 100, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public void show(boolean isMale) {
        maleView.setChecked(isMale);
        femaleView.setChecked(!isMale);
        show();
    }

    private OnChangeGenderListener onChangeGenderListener;

    public void setOnChangeGenderListener(OnChangeGenderListener l) {
        onChangeGenderListener = l;
    }

    public interface OnChangeGenderListener {
        void onChangeGender(boolean isMale);
    }
}

