package cn.hym.kuaidou;

import android.app.Application;
import android.content.Context;

import com.tencent.TIMManager;
import com.tencent.TIMUserProfile;
import com.tencent.ilivesdk.ILiveSDK;
import com.tencent.livesdk.ILVLiveConfig;
import com.tencent.livesdk.ILVLiveManager;

import java.util.ArrayList;
import java.util.List;

import cn.hym.kuaidou.editprofile.CustomProfile;
import cn.hym.kuaidou.utils.QnUploadHelper;

/**
 * Created by Administrator on 2018/4/25.
 */

public class KuaiDouApplication extends Application{
    private static KuaiDouApplication app;
    private ILVLiveConfig mLiveConfig;
    private static Context appContext;

    private TIMUserProfile mSelfProfile;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appContext = getApplicationContext();
        //iLiveSDK初始化
        ILiveSDK.getInstance().initSdk(getApplicationContext(), 1400086123, 25785);
        List<String> customInfos = new ArrayList<String>();
        customInfos.add(CustomProfile.CUSTOM_GET);
        customInfos.add(CustomProfile.CUSTOM_SEND);
        customInfos.add(CustomProfile.CUSTOM_LEVEL);
        customInfos.add(CustomProfile.CUSTOM_RENZHENG);
        //设置资料字段
        TIMManager.getInstance().initFriendshipSettings(CustomProfile.allBaseInfo,null);

        //初始化直播场景
        mLiveConfig = new ILVLiveConfig();
        ILVLiveManager.getInstance().init(mLiveConfig);
        //初始化七牛云
        QnUploadHelper.init("fywLTKHt3JUahQrTPSFrKRt27FjWTBV6Yn8CQFWe",
                "00nzSVpO5yURyMxpPkOP_9shEtnGYDbGJxMavzdL",
                "http://oe0i3jf0i.bkt.clouddn.com/",
                "imooc");
    }

    public static Context getContext() {
        return appContext;
    }

    public static KuaiDouApplication getApplication() {
        return app;
    }

    public void setSelfProfile(TIMUserProfile userProfile) {
        mSelfProfile = userProfile;
    }

    public TIMUserProfile getSelfProfile() {
        return mSelfProfile;
    }
    public ILVLiveConfig getLiveConfig() {
        return mLiveConfig;
    }

}
