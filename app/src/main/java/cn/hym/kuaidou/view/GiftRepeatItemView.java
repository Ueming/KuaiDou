package cn.hym.kuaidou.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tencent.TIMUserProfile;

import cn.hym.kuaidou.R;
import cn.hym.kuaidou.model.GiftInfo;
import cn.hym.kuaidou.utils.ImgUtils;

/**
 * Created by Administrator on 2018/4/27.
 */

public class GiftRepeatItemView extends LinearLayout{

    private ImageView user_header;
    private TextView user_name;
    private TextView gift_name;
    private ImageView gift_img;
    private TextView gift_num;

    private Animation viewInAnim;
    private Animation imgViewInAnim;
    private Animation textScaleAnim;

    public GiftRepeatItemView(Context context) {
        super(context);
        init();
    }

    public GiftRepeatItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GiftRepeatItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_gift_repeat_item, this, true);

        findAllViews();
        initAnim();
    }

    private void initAnim() {

        viewInAnim = AnimationUtils.loadAnimation(getContext(),R.anim.repeat_gift_view_in);
        imgViewInAnim = AnimationUtils.loadAnimation(getContext(), R.anim.repeat_gift_img_view_in);
        textScaleAnim = AnimationUtils.loadAnimation(getContext(), R.anim.repeat_gift_num_scale);

        viewInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                setVisibility(VISIBLE);
                gift_img.setVisibility(INVISIBLE);
                gift_num.setVisibility(INVISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        //在view显示完成之后，再进行img的动画
                        gift_img.startAnimation(imgViewInAnim);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        imgViewInAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

                gift_img.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                post(new Runnable() {
                    @Override
                    public void run() {
                        //在view显示完成之后，再进行NumImg的动画
                        gift_num.startAnimation(textScaleAnim);
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        textScaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                gift_num.setText("x" + totalNum);
                gift_num.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (leftNum > 0) {
                    leftNum--;
                    totalNum++;
                    post(new Runnable() {
                        @Override
                        public void run() {
                            //在view显示完成之后，再进行img的动画
                            gift_num.startAnimation(textScaleAnim);
                        }
                    });
                } else {
                    setVisibility(INVISIBLE);
                    if (listener != null) {
                        listener.onAvaliable();
                    }
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void findAllViews() {

        user_header = (ImageView) findViewById(R.id.user_header);
        user_name = (TextView) findViewById(R.id.user_name);
        gift_name = (TextView) findViewById(R.id.gift_name);
        gift_img = (ImageView) findViewById(R.id.gift_img);
        gift_num = (TextView) findViewById(R.id.gift_num);

    }

    public boolean isAvaliable(GiftInfo giftInfo, String repeatId, TIMUserProfile profile) {
        boolean sameGift = giftId == giftInfo.giftId;
        boolean sameRepeat = this.repeatId.equals(repeatId);
        boolean sameUser = this.userId.equals(profile.getIdentifier());
        boolean canContinue = giftInfo.type == GiftInfo.Type.ContinueGift;
        return sameGift && sameRepeat && sameUser && canContinue;
    }

    private int giftId = -1;
    private String userId = "";
    private String repeatId = "";

    private int leftNum = 0;
    private int totalNum = 0;

    public void showGift(GiftInfo giftInfo, String repeatId, TIMUserProfile profile){

        giftId = giftInfo.giftId;
        userId = profile.getIdentifier();
        this.repeatId = repeatId;

        if(getVisibility() == INVISIBLE){
            totalNum = 1;
            //所有动画结束之后
            String avatarUrl = profile.getFaceUrl();

            if (TextUtils.isEmpty(avatarUrl)) {
                ImgUtils.loadRound(R.mipmap.default_avatar, user_header);
            } else {
                ImgUtils.loadRound(avatarUrl, user_header);
            }
            String nickName = profile.getNickName();
            if (TextUtils.isEmpty(nickName)) {
                nickName = profile.getIdentifier();
            }
            user_name.setText(nickName);
            gift_name.setText("送出一个" + giftInfo.name);
            ImgUtils.load(giftInfo.giftResId, gift_img);
            gift_num.setText("x" + 1);

            post(new Runnable() {
                @Override
                public void run() {
                    startAnimation(viewInAnim);
                }
            });

        }else {
            //需要记录下还需要显示多少次礼物。也就是数字的变化。
            leftNum++;
        }
    }

    public interface OnGiftItemAvaliableListener {
        void onAvaliable();
    }

    private OnGiftItemAvaliableListener listener;

    public void setOnGiftItemAvaliableListener(OnGiftItemAvaliableListener l) {
        listener = l;
    }

}
