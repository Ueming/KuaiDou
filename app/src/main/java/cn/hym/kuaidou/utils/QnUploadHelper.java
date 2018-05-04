package cn.hym.kuaidou.utils;

import android.util.Log;


import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.utils.UrlSafeBase64;

import org.json.JSONObject;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Administrator on 2017/4/3.
 */

public class QnUploadHelper {
    private static final String TAG = QnUploadHelper.class.getSimpleName();
    //七牛后台的key
    private static String AccessKey;
    //七牛后台的secret
    private static String SecretKey;

    private static String Domain;
    private static String BucketName;

    private static final String MAC_NAME = "HmacSHA1";
    private static final String ENCODING = "UTF-8";

    private static Configuration configuration;

    private static long delayTimes = 3029414400l; //有效时间

    public static void init(String accessKey, String secretKey, String domain, String bucketName) {
        AccessKey = accessKey;
        SecretKey = secretKey;
        Domain = domain;
        BucketName = bucketName;

        configuration = new Configuration.Builder().build();
    }

    /**
     * 上传
     *
     //     * @param bucketName bucketName的名字
     * @param path   上传文件的路径地址
     */
    public static void uploadPic(final String path, final String keys, final UploadCallBack callBack) {
        try {
            // 1:第一种方式 构造上传策略
            JSONObject _json = new JSONObject();
            _json.put("deadline", delayTimes);
            _json.put("scope", BucketName);
            String _encodedPutPolicy = UrlSafeBase64.encodeToString(_json
                    .toString().getBytes());
            byte[] _sign = HmacSHA1Encrypt(_encodedPutPolicy, SecretKey);
            String _encodedSign = UrlSafeBase64.encodeToString(_sign);
            final String _uploadToken = AccessKey + ':' + _encodedSign + ':'
                    + _encodedPutPolicy;
            UploadManager uploadManager = new UploadManager(configuration);

            uploadManager.put(path, keys, _uploadToken,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info,
                                             JSONObject response) {
                            Log.d(TAG,"response = " + response);
                            if (info.isOK()) {
                                String picUrl = Domain + keys;
                                callBack.success(picUrl);
                            } else
                                callBack.fail(key, info);
                        }
                    }, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     *
     * @param encryptText 被签名的字符串
     * @param encryptKey  密钥
     * @return
     * @throws Exception
     */
    public static byte[] HmacSHA1Encrypt(String encryptText, String encryptKey)
            throws Exception {
        byte[] data = encryptKey.getBytes(ENCODING);
        // 根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
        javax.crypto.SecretKey secretKey = new SecretKeySpec(data, MAC_NAME);
        // 生成一个指定 Mac 算法 的 Mac 对象
        Mac mac = Mac.getInstance(MAC_NAME);
        // 用给定密钥初始化 Mac 对象
        mac.init(secretKey);
        byte[] text = encryptText.getBytes(ENCODING);
        // 完成 Mac 操作
        return mac.doFinal(text);
    }

    public interface UploadCallBack {
        void success(String url);

        void fail(String key, ResponseInfo info);
    }

}
