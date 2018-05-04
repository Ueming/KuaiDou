package cn.hym.kuaidou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.hym.kuaidou.R;
import cn.hym.kuaidou.model.GiftInfo;
import cn.hym.kuaidou.utils.ImgUtils;


/**
 * Created by Administrator.
 */

public class GiftGridView extends GridView {

    private static final String TAG = GiftGridView.class.getSimpleName();
    private List<GiftInfo> giftInfoList = new ArrayList<GiftInfo>();

    private GridAdapter gridAdapter;

    public GiftGridView(Context context) {
        super(context);
        init();
    }

    public GiftGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GiftGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        setNumColumns(4);

        gridAdapter = new GridAdapter();
        setAdapter(gridAdapter);
    }

    public void setGiftInfoList(List<GiftInfo> giftInfos) {
        giftInfoList.clear();
        giftInfoList.addAll(giftInfos);
        gridAdapter.notifyDataSetChanged();
    }

    public int getGridViewHeight() {
        //获取高度：adapter item 的高度 * 行数
        View item = gridAdapter.getView(0, null, this);
        item.measure(0, 0);
        int height = item.getMeasuredHeight();
        return height * 2;
    }

    private GiftInfo selectGiftInfo;

    public void setSelectGiftInfo(GiftInfo selectGiftInfo) {
        this.selectGiftInfo = selectGiftInfo;
    }

    public void notifyDataSetChanged() {
        gridAdapter.notifyDataSetChanged();
    }


    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return giftInfoList.size();
        }

        @Override
        public Object getItem(int i) {
            return giftInfoList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            GiftHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.view_gift_item, viewGroup, false);
                holder = new GiftHolder(view);
                view.setTag(holder);

            } else {
                holder = (GiftHolder) view.getTag();
            }

            GiftInfo giftInfo = giftInfoList.get(i);
            holder.bindData(giftInfo);

            return view;
        }

        private class GiftHolder {

            private View view;
            private ImageView giftImg;
            private TextView giftExp;
            private TextView giftName;
            private ImageView giftSelect;

            public GiftHolder(View view) {
                this.view = view;
                giftImg = (ImageView) view.findViewById(R.id.gift_img);
                giftExp = (TextView) view.findViewById(R.id.gift_exp);
                giftName = (TextView) view.findViewById(R.id.gift_name);
                giftSelect = (ImageView) view.findViewById(R.id.gift_select);
            }

            public void bindData(final GiftInfo giftInfo) {

                ImgUtils.load(giftInfo.giftResId, giftImg);
                if (giftInfo != GiftInfo.Gift_Empty) {
                    giftExp.setText(giftInfo.expValue + "经验值");
                    giftName.setText(giftInfo.name);
                    if (giftInfo == selectGiftInfo) {
                        giftSelect.setImageResource(R.mipmap.gift_selected);
                    } else {
                        if (giftInfo.type == GiftInfo.Type.ContinueGift) {
                            giftSelect.setImageResource(R.mipmap.gift_repeat);
                        } else if (giftInfo.type == GiftInfo.Type.FullScreenGift) {
                            giftSelect.setImageResource(R.mipmap.gift_none);
                        }
                    }
                } else {
                    giftExp.setText("");
                    giftName.setText("");
                    giftSelect.setImageResource(R.mipmap.gift_none);
                }

                view.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (giftInfo == GiftInfo.Gift_Empty) {
                            return;
                        }

                        if (giftInfo == selectGiftInfo) {
                            if (mOnGiftItemClickListener != null) {
                                mOnGiftItemClickListener.onClick(null);
                            }
                        } else {
                            if (mOnGiftItemClickListener != null) {
                                mOnGiftItemClickListener.onClick(giftInfo);
                            }
                        }

                    }
                });
            }
        }
    }

    private OnGiftItemClickListener mOnGiftItemClickListener;

    public void setOnGiftItemClickListener(OnGiftItemClickListener l) {
        mOnGiftItemClickListener = l;
    }

    public interface OnGiftItemClickListener {
        void onClick(GiftInfo giftInfo);
    }
}
