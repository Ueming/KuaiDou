package cn.hym.kuaidou.model;

/**
 * Created by Administrator.
 */

public class ChatMsgInfo {

    public static final int MSGTYPE_LIST = 0;
    public static final int MSGTYPE_DANMU = 1;

    private int msgType = MSGTYPE_LIST;
    private String text;//聊天的内容
    private String senderId;//发送者的id
    private String avatar;//发送者的头像
    private String senderName;//发送者名字

    //创建列表消息
    public static ChatMsgInfo createListInfo(String text, String userId, String avatar) {
        ChatMsgInfo chatMsgInfo = new ChatMsgInfo();
        chatMsgInfo.msgType = MSGTYPE_LIST;
        chatMsgInfo.text = text;
        chatMsgInfo.senderId = userId;
        chatMsgInfo.avatar = avatar;
        chatMsgInfo.senderName = "";

        return chatMsgInfo;
    }

    public static ChatMsgInfo createDanmuInfo(String text, String userId, String avatar, String name) {
        ChatMsgInfo chatMsgInfo = new ChatMsgInfo();
        chatMsgInfo.msgType = MSGTYPE_LIST;
        chatMsgInfo.text = text;
        chatMsgInfo.senderId = userId;
        chatMsgInfo.avatar = avatar;
        chatMsgInfo.senderName = name;

        return chatMsgInfo;
    }

    public String getContent() {
        return text;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getAvatar() {
        return avatar;
    }

    public int getMsgType() {
        return msgType;
    }

    public String getSenderName() {
        return senderName;
    }


}
