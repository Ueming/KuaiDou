package cn.hym.kuaidou.widget;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.tencent.livesdk.ILVCustomCmd;
import com.tencent.livesdk.ILVText;

import java.util.ArrayList;
import java.util.List;

import cn.hym.kuaidou.R;
import cn.hym.kuaidou.model.Constants;
import cn.hym.kuaidou.model.GiftCmdInfo;
import cn.hym.kuaidou.model.GiftInfo;
import cn.hym.kuaidou.view.GiftGridView;

/**
 * Created by Administrator on 2018/4/28.
 */

public class GiftSelectDialog extends TransParentNoDimDialog {
    private ViewPager giftPager;
    private GiftPagerAdapter giftAdapter;
    private ImageView indicatorOne;
    private ImageView indicatorTwo;
    private Button sendBtn;
    public GiftInfo selectGiftInfo = null;
    private List<GiftGridView> pageViews = new ArrayList<GiftGridView>();
    private static List<GiftInfo> giftInfos = new ArrayList<GiftInfo>();

    static {
        giftInfos.add(GiftInfo.Gift_BingGun);
        giftInfos.add(GiftInfo.Gift_BingJiLing);
        giftInfos.add(GiftInfo.Gift_MeiGui);
        giftInfos.add(GiftInfo.Gift_PiJiu);
        giftInfos.add(GiftInfo.Gift_HongJiu);
        giftInfos.add(GiftInfo.Gift_Hongbao);
        giftInfos.add(GiftInfo.Gift_ZuanShi);
        giftInfos.add(GiftInfo.Gift_BaoXiang);
        giftInfos.add(GiftInfo.Gift_BaoShiJie);
    }

    public GiftSelectDialog(Activity activity) {
        super(activity);
        View view = LayoutInflater.from(activity).inflate(R.layout.dialog_gift_select, null, false);
        setContentView(view);
        setWidthAndHeight(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        findAllViews(view);
    }

    private void findAllViews(View view) {

        giftPager = (ViewPager) view.findViewById(R.id.gift_pager);
        indicatorOne = (ImageView) view.findViewById(R.id.indicator_one);
        indicatorTwo = (ImageView) view.findViewById(R.id.indicator_two);
        sendBtn = (Button) view.findViewById(R.id.send);
        giftAdapter = new GiftPagerAdapter();
        giftPager.setAdapter(giftAdapter);
        giftPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    indicatorOne.setImageResource(R.mipmap.ind_s);
                    indicatorTwo.setImageResource(R.mipmap.ind_uns);
                } else if (position == 1) {
                    indicatorOne.setImageResource(R.mipmap.ind_uns);
                    indicatorTwo.setImageResource(R.mipmap.ind_s);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        indicatorOne.setImageResource(R.mipmap.ind_s);
        indicatorTwo.setImageResource(R.mipmap.ind_uns);
        sendBtn.setVisibility(View.INVISIBLE);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //礼物的发送事件。selectGiftInfo。
                if (TextUtils.isEmpty(repeatId)) {
                    repeatId = System.currentTimeMillis() + "";
                }
                if(onGiftSendListener != null){
                    ILVCustomCmd giftCmd = new ILVCustomCmd();
                    giftCmd.setType(ILVText.ILVTextType.eGroupMsg);
                    giftCmd.setCmd(Constants.CMD_CHAT_GIFT);
                    GiftCmdInfo giftCmdInfo = new GiftCmdInfo();
                    giftCmdInfo.giftId = selectGiftInfo.giftId;
                    giftCmdInfo.repeatId = repeatId;
                    giftCmd.setParam(new Gson().toJson(giftCmdInfo));
                    onGiftSendListener.onGiftSendClick(giftCmd);
                    if(selectGiftInfo.type == GiftInfo.Type.ContinueGift){
                        restartTimer();
                    }
                }
            }
        });
    }

    private Handler sendRepeatTimer = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int what = msg.what;
            if (WHAT_UPDATE_TIME == what) {
                sendBtn.setText("发送(" + leftTime + "s)");
                sendRepeatTimer.sendEmptyMessageDelayed(WHAT_MINUTES_TIME, 200);
            } else if (WHAT_MINUTES_TIME == what) {
                leftTime--;
                if (leftTime > 0) {
                    sendBtn.setText("发送(" + leftTime + "s)");
                    sendRepeatTimer.sendEmptyMessageDelayed(WHAT_MINUTES_TIME, 200);
                } else {
                    sendBtn.setText("发送");
                    repeatId = "";
                }
            }

        }
    };

    private static final int WHAT_UPDATE_TIME = 0;
    private static final int WHAT_MINUTES_TIME = 1;
    private int leftTime = 10;
    private String repeatId = "";

    private class GiftPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 创建item view
            final GiftGridView itemView = new GiftGridView(activity);
            itemView.setOnGiftItemClickListener(new GiftGridView.OnGiftItemClickListener() {
                @Override
                public void onClick(GiftInfo giftInfo) {
                    stopTimer();
                    repeatId = "";
                    selectGiftInfo = giftInfo;
                    if (selectGiftInfo != null) {
                        sendBtn.setVisibility(View.VISIBLE);
                    } else {
                        sendBtn.setVisibility(View.INVISIBLE);
                    }
                    for (GiftGridView giftGridView : pageViews) {
                        giftGridView.setSelectGiftInfo(selectGiftInfo);
                        giftGridView.notifyDataSetChanged();
                    }
                }
            });
            //确定当前页面所展示的gift的list
            int endIndex = (position + 1) * 8;
            int emptyNum = 0;
            //最后一页的边界处理
            if (endIndex > giftInfos.size()) {
                emptyNum = endIndex - giftInfos.size();
                endIndex = giftInfos.size();
            }
            List<GiftInfo> targetInfos = giftInfos.subList(position * 8, endIndex);
            //超出边界的，用空填充。保证每个页面都有item
            for (int i = 0; i < emptyNum; i++) {
                targetInfos.add(GiftInfo.Gift_Empty);
            }
            itemView.setGiftInfoList(targetInfos);
            int gridViewHeight = itemView.getGridViewHeight();
            container.addView(itemView);
            pageViews.add(itemView);
            ViewGroup.LayoutParams layoutParams = container.getLayoutParams();
            layoutParams.height = gridViewHeight;
            container.setLayoutParams(layoutParams);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            pageViews.remove(object);
        }
    }

    @Override
    public void show() {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        dialog.getWindow().setAttributes(lp);
        super.show();
    }
    private OnGiftSendListener onGiftSendListener;

    public interface OnGiftSendListener {
        void onGiftSendClick(ILVCustomCmd customCmd);
    }

    public void setGiftSendListener(OnGiftSendListener l) {
        onGiftSendListener = l;
    }

    private void restartTimer() {
        stopTimer();
        sendRepeatTimer.sendEmptyMessage(WHAT_UPDATE_TIME);
    }

    private void stopTimer() {
        sendRepeatTimer.removeMessages(WHAT_UPDATE_TIME);
        sendRepeatTimer.removeMessages(WHAT_MINUTES_TIME);
        sendBtn.setText("发送");
        leftTime = 10;
    }
}
