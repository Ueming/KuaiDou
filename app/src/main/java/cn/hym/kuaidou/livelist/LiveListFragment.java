package cn.hym.kuaidou.livelist;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.hym.kuaidou.R;
import cn.hym.kuaidou.hostlive.GetLiveListRequest;
import cn.hym.kuaidou.model.RoomInfo;
import cn.hym.kuaidou.utils.ImgUtils;
import cn.hym.kuaidou.utils.request.BaseRequest;
import cn.hym.kuaidou.watcher.WatcherActivity;

/**
 * Created by Administrator.
 */

public class LiveListFragment extends Fragment {

    private ListView mLiveListView;
    private LiveListAdapter mLiveListAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_live_list, container, false);

        findAllViews(view);
        requestLiveList();

        return view;
    }

    private void findAllViews(View view) {

        Toolbar titlebar = (Toolbar) view.findViewById(R.id.titlebar);
        titlebar.setTitle("热播列表");
        titlebar.setTitleTextColor(Color.WHITE);
        ((AppCompatActivity) getActivity()).setSupportActionBar(titlebar);

        mLiveListView = (ListView) view.findViewById(R.id.live_list);
        mLiveListAdapter = new LiveListAdapter(getContext());
        mLiveListView.setAdapter(mLiveListAdapter);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout_list);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //请求服务器，获取直播列表
                requestLiveList();
            }
        });
    }

    private class LiveListAdapter extends BaseAdapter {

        private Context mContext;
        private List<RoomInfo> liveRooms = new ArrayList<RoomInfo>();

        public LiveListAdapter(Context context) {
            this.mContext = context;
        }

        public void removeAllRoomInfos() {
            liveRooms.clear();
        }

        public void addRoomInfos(List<RoomInfo> roomInfos) {
            if (roomInfos != null) {
                liveRooms.clear();
                liveRooms.addAll(roomInfos);
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return liveRooms.size();
        }

        @Override
        public RoomInfo getItem(int position) {
            return liveRooms.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RoomHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_live_list, null);
                holder = new RoomHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (RoomHolder) convertView.getTag();
            }

            holder.bindData(liveRooms.get(position));

            return convertView;
        }


        private class RoomHolder {

            View itemView;
            TextView liveTitle;
            ImageView liveCover;
            ImageView hostAvatar;
            TextView hostName;
            TextView watchNums;

            public RoomHolder(View view) {
                itemView = view;
                liveTitle = (TextView) view.findViewById(R.id.live_title);
                liveCover = (ImageView) view.findViewById(R.id.live_cover);
                hostName = (TextView) view.findViewById(R.id.host_name);
                hostAvatar = (ImageView) view.findViewById(R.id.host_avatar);
                watchNums = (TextView) view.findViewById(R.id.watch_nums);
            }

            public void bindData(final RoomInfo roomInfo) {

                String userName = roomInfo.userName;
                if (TextUtils.isEmpty(userName)) {
                    userName = roomInfo.userId;
                }
                hostName.setText(userName);

                String liveTitleStr = roomInfo.liveTitle;
                if (TextUtils.isEmpty(liveTitleStr)) {
                    this.liveTitle.setText(userName + "的直播");
                } else {
                    this.liveTitle.setText(liveTitleStr);
                }
                String url = roomInfo.liveCover;
                if (TextUtils.isEmpty(url)) {
                    ImgUtils.load(R.mipmap.default_cover, liveCover);
                } else {
                    ImgUtils.load(url, liveCover);
                }

                String avatar = roomInfo.userAvatar;
                if (TextUtils.isEmpty(avatar)) {
                    ImgUtils.loadRound(R.mipmap.default_avatar, hostAvatar);
                } else {
                    ImgUtils.loadRound(avatar, hostAvatar);
                }

                int watchers = roomInfo.watcherNums;
                String watchText = watchers + "人\r\n正在看";
                watchNums.setText(watchText);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent();
                        intent.setClass(mContext, WatcherActivity.class);
                        intent.putExtra("roomId", roomInfo.roomId);
                        intent.putExtra("hostId", roomInfo.userId);
                        startActivity(intent);
                    }
                });
            }
        }
    }
    private void requestLiveList() {
        //请求前20个数据
        GetLiveListRequest liveListRequest = new GetLiveListRequest();
        liveListRequest.setOnResultListener(new BaseRequest.OnResultListener<List<RoomInfo>>() {
            @Override
            public void onSuccess(List<RoomInfo> roomInfos) {

                mLiveListAdapter.removeAllRoomInfos();//下拉刷新，先移除掉之前的room信息
                mLiveListAdapter.addRoomInfos(roomInfos);//再添加新的信息
                mSwipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFail(int code, String msg) {
                Toast.makeText(getActivity(), "请求列表失败：" + msg, Toast.LENGTH_SHORT).show();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        GetLiveListRequest.LiveListParam param = new GetLiveListRequest.LiveListParam();
        param.pageIndex = 0;//从0开始，也就是第一页。
        String url = liveListRequest.getUrl(param);
        liveListRequest.request(url);
    }
}