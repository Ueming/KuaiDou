package cn.hym.kuaidou.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.TIMFriendshipManager;
import com.tencent.TIMUserProfile;
import com.tencent.TIMValueCallBack;

import java.util.ArrayList;
import java.util.List;

import cn.hym.kuaidou.R;
import cn.hym.kuaidou.model.ChatMsgInfo;
import cn.hym.kuaidou.utils.ImgUtils;
import cn.hym.kuaidou.widget.UserInfoDialog;

/**
 * Created by Administrator on 2018/4/26.
 */

public class ChatMsgListView extends RelativeLayout {

    private ListView mChatMsgListView;
    private ChatMsgAdapter mChatMsgAdapter;

    public ChatMsgListView(Context context) {
        super(context);
        init();
    }

    public ChatMsgListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChatMsgListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_chat_msg_list, this, true);

        findAllViews();
    }

    private void findAllViews() {
        mChatMsgListView = (ListView) findViewById(R.id.chat_msg_list);
        mChatMsgAdapter = new ChatMsgAdapter();
        mChatMsgListView.setAdapter(mChatMsgAdapter);
        mChatMsgListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ChatMsgInfo msgInfo = mChatMsgAdapter.getItem(position);
                showUserInfoDialog(msgInfo.getSenderId());
            }
        });
    }

    private void showUserInfoDialog(String senderId) {
        List<String> ids = new ArrayList<String>();
        ids.add(senderId);
        TIMFriendshipManager.getInstance().getUsersProfile(ids, new TIMValueCallBack<List<TIMUserProfile>>() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess(List<TIMUserProfile> timUserProfiles) {
                Context context = ChatMsgListView.this.getContext();
                if(context instanceof  Activity) {
                    UserInfoDialog userInfoDialog = new UserInfoDialog((Activity) context, timUserProfiles.get(0));
                    userInfoDialog.show();
                }
            }
        });
    }

    public void addMsgInfo(ChatMsgInfo info) {
        if (info != null) {
            mChatMsgAdapter.addMsgInfo(info);
            mChatMsgListView.smoothScrollToPosition(mChatMsgAdapter.getCount());
        }
    }

    public void addMsgInfos(List<ChatMsgInfo> infos) {
        if (infos != null) {
            mChatMsgAdapter.addMsgInfos(infos);
            mChatMsgListView.smoothScrollToPosition(mChatMsgAdapter.getCount());
        }
    }

    private class ChatMsgAdapter extends BaseAdapter {

        private List<ChatMsgInfo> mChatMsgInfos = new ArrayList<ChatMsgInfo>();

        public void addMsgInfo(ChatMsgInfo info) {
            if (info != null) {
                mChatMsgInfos.add(info);
                notifyDataSetChanged();
            }
        }

        public void addMsgInfos(List<ChatMsgInfo> infos) {
            if (infos != null) {
                mChatMsgInfos.addAll(infos);
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return mChatMsgInfos.size();
        }

        @Override
        public ChatMsgInfo getItem(int i) {
            return mChatMsgInfos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ChatMsgHolder holder = null;
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.view_chat_msg_list_item, null);
                holder = new ChatMsgHolder(view);
                view.setTag(holder);
            } else {
                holder = (ChatMsgHolder) view.getTag();
            }

            holder.bindData(mChatMsgInfos.get(i));

            return view;
        }
    }

    private class ChatMsgHolder {

        private ImageView avatar;
        private TextView content;

        private ChatMsgInfo chatMsgInfo;

        public ChatMsgHolder(View itemView) {

            avatar = (ImageView) itemView.findViewById(R.id.sender_avatar);
            content = (TextView) itemView.findViewById(R.id.chat_content);
        }

        public void bindData(ChatMsgInfo info) {
            chatMsgInfo = info;

            String avatarUrl = info.getAvatar();
            if (TextUtils.isEmpty(avatarUrl)) {
                ImgUtils.loadRound(R.mipmap.default_avatar, avatar);
            } else {
                ImgUtils.loadRound(avatarUrl, avatar);
            }
            content.setText(info.getContent());
        }
    }

}
