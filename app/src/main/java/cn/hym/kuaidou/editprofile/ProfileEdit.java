package cn.hym.kuaidou.editprofile;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.hym.kuaidou.R;

/**
 * Created by Administrator on 2017/4/2.
 */

public class ProfileEdit extends LinearLayout {

    private ImageView mIconView;
    private TextView mKeyView;
    private TextView mValueView;
    private ImageView mRightArrowView;

    public ProfileEdit(Context context) {
        super(context);
        init();
    }

    public ProfileEdit(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProfileEdit(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_profile_edit, this
                , true);
        findAllViews();
    }

    private void findAllViews() {
        mIconView = (ImageView) findViewById(R.id.profile_icon);
        mKeyView = (TextView) findViewById(R.id.profile_key);
        mValueView = (TextView) findViewById(R.id.profile_value);
        mRightArrowView = (ImageView) findViewById(R.id.right_arrow);
    }

    public void set(int iconResId, String key, String value) {
        mIconView.setImageResource(iconResId);
        mKeyView.setText(key);
        mValueView.setText(value);
    }

    public void updateValue(String value) {
        mValueView.setText(value);
    }

    public String getValue() {
        return mValueView.getText().toString();
    }

    protected void disableEdit() {
        mRightArrowView.setVisibility(GONE);
    }
}
