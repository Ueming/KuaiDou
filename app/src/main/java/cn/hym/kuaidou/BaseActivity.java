package cn.hym.kuaidou;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

import cn.hym.kuaidou.utils.SystemBarTintManager;

/**
 * Created by Administrator on 2018/5/2.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 改状态栏的颜色，布局文件最顶层父控件要加上一下两行代码
     * android:clipToPadding="true"
     * android:fitsSystemWindows="true"
     * 建议在setContentView之前调用
     *
     * @param res color的资源id
     */
    protected void initStatusBar(int res) {
        Log.e("TAG","透明状态栏");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarAlpha(50);
            tintManager.setStatusBarTintResource(res);
        }
    }
    protected void setStatusBar(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
}
