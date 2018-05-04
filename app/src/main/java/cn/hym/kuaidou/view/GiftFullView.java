package cn.hym.kuaidou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tencent.TIMUserProfile;

import java.util.LinkedList;
import java.util.List;

import cn.hym.kuaidou.model.GiftInfo;

/**
 * Created by Administrator on 2018/4/27.
 */

public class GiftFullView extends RelativeLayout {
    private PorcheView mPorcheView;
    private boolean isAvaliable = false;

    private class GiftUserInfo{
        GiftInfo giftInfo;
        TIMUserProfile userProfile;
    }

    private List<GiftUserInfo> giftUserInfoList = new LinkedList<GiftUserInfo>();

    public GiftFullView(Context context) {
        super(context);
        init();
    }

    public GiftFullView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GiftFullView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        isAvaliable = true;
    }

    public void showGift(GiftInfo giftInfo, TIMUserProfile userProfile) {
        if (giftInfo == null || giftInfo.type != GiftInfo.Type.FullScreenGift) {
            return;
        }

        if(isAvaliable) {
            isAvaliable = false;
            if (giftInfo.giftId == GiftInfo.Gift_BaoShiJie.giftId) {
                showPorcheView(userProfile);
            }else {
                //其他的全屏礼物
            }
        }else{
            GiftUserInfo giftUserInfo = new GiftUserInfo();
            giftUserInfo.giftInfo = giftInfo;
            giftUserInfo.userProfile = userProfile;

            giftUserInfoList.add(giftUserInfo);
        }
    }

    private void showPorcheView(final TIMUserProfile userProfile) {
        if (mPorcheView == null) {
            mPorcheView = new PorcheView(getContext());

            RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT);
            addView(mPorcheView, rlp);

            mPorcheView.setOnAvaliableListener(new PorcheView.OnAvaliableListener() {
                @Override
                public void onAvaliable() {
                    isAvaliable = true;
                    int size = giftUserInfoList.size();
                    if(size > 0){
                        GiftUserInfo giftUserInfo= giftUserInfoList.remove(0);
                        GiftInfo giftInfo = giftUserInfo.giftInfo;
                        TIMUserProfile userProfile1 = giftUserInfo.userProfile;
                        showGift(giftInfo,userProfile1);
                    }
                }
            });
        }


        mPorcheView.show(userProfile);
    }

}
