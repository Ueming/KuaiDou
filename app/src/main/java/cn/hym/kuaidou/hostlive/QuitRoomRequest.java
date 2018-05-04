package cn.hym.kuaidou.hostlive;

import java.io.IOException;

import cn.hym.kuaidou.utils.request.BaseRequest;

/**
 * Created by Administrator.
 */

public class QuitRoomRequest extends BaseRequest {
    private static final String Action = "http://kuaidou.butterfly.mopaasapp.com/roomServlet?action=quit";

    private static final String RequestParamKey_RoomId = "roomId";
    private static final String RequestParamKey_UserId = "userId";

    public String getUrl(String roomId, String userId) {
        return Action
                + "&" + RequestParamKey_RoomId + "=" + roomId
                + "&" + RequestParamKey_UserId + "=" + userId
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

    }
}
