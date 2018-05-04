package cn.hym.kuaidou.editprofile;

import com.tencent.TIMFriendshipManager;

/**
 * Created by Administrator on 2018/4/25.
 */

public class CustomProfile {
    //自定义字段
    private static final String PREFIX = "Tag_Profile_Custom_";
    public static final String CUSTOM_RENZHENG = PREFIX + "renzhen";
    public static final String CUSTOM_LEVEL = PREFIX + "level";
    public static final String CUSTOM_GET = PREFIX + "getNums";
    public static final String CUSTOM_SEND = PREFIX + "sendNums";

    //腾讯基础字段
    public static final long allBaseInfo =
            TIMFriendshipManager.TIM_PROFILE_FLAG_BIRTHDAY |
                    TIMFriendshipManager.TIM_PROFILE_FLAG_FACE_URL |
                    TIMFriendshipManager.TIM_PROFILE_FLAG_GENDER |
                    TIMFriendshipManager.TIM_PROFILE_FLAG_LANGUAGE |
                    TIMFriendshipManager.TIM_PROFILE_FLAG_LOCATION |
                    TIMFriendshipManager.TIM_PROFILE_FLAG_NICK |
                    TIMFriendshipManager.TIM_PROFILE_FLAG_SELF_SIGNATURE |
                    TIMFriendshipManager.TIM_PROFILE_FLAG_REMARK |
                    TIMFriendshipManager.TIM_PROFILE_FLAG_GROUP;
}
