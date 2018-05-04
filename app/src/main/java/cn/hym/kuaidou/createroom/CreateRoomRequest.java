package cn.hym.kuaidou.createroom;

import java.io.IOException;

import cn.hym.kuaidou.ResponseObject;
import cn.hym.kuaidou.utils.request.BaseRequest;

/**
 * Created by Administrator on 2018/4/25.
 */

public class CreateRoomRequest extends BaseRequest{

    private static final String Action = "http://kuaidou.butterfly.mopaasapp.com/roomServlet?action=create";

    private static final String RequestParamKey_UserId = "userId";
    private static final String RequestParamKey_UserAvatar = "userAvatar";
    private static final String RequestParamKey_UserName = "userName";
    private static final String RequestParamKey_LiveTitle = "liveTitle";
    private static final String RequestParamKey_LiveCover = "liveCover";

    public static class CreateRoomParam {
        public String userId;
        public String userAvatar;
        public String userName;
        public String liveTitle;
        public String liveCover;
    }

    public String getUrl(CreateRoomParam param) {
        return Action
                + "&" + RequestParamKey_UserId + "=" + param.userId
                + "&" + RequestParamKey_UserAvatar + "=" + param.userAvatar
                + "&" + RequestParamKey_UserName + "=" + param.userName
                + "&" + RequestParamKey_LiveTitle + "=" + param.liveTitle
                + "&" + RequestParamKey_LiveCover + "=" +param.liveCover
                ;
    }

    @Override
    protected void onFail(IOException e) {
        sendFailMsg(-100, e.getMessage());
    }


    @Override
    protected void onResponseFail(int code) {
        sendFailMsg(code, "服务出现异常");
    }

    @Override
    protected void onResponseSuccess(String body) {
        RoomInfoResponseObj responseObject = gson.fromJson(body, RoomInfoResponseObj.class);
        if (responseObject == null) {
            sendFailMsg(-101, "数据格式错误");
            return;
        }
        if (responseObject.code.equals(ResponseObject.CODE_SUCCESS)) {
            sendSuccMsg(responseObject.data);
        } else if (responseObject.code.equals(ResponseObject.CODE_FAIL)) {
            sendFailMsg(Integer.valueOf(responseObject.errCode), responseObject.errMsg);
        }
    }

}
