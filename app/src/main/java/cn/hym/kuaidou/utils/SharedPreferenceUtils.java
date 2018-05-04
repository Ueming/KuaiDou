package cn.hym.kuaidou.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * SharedPreference工具类
 */
public class SharedPreferenceUtils {

    private static final String COOKIE = "cookie";
    private static final String SESSION_ID = "session_id";
    private static final String FIRST_INSTALL = "first_install";
    private static final String USER = "user";
    private static final String PLATFORM = "platform";
    private static final String PASSWORD = "password";
    private static final String USERNAME = "username";

    //------新浪微博AccessToken----------------------------------------------
//    private static final String PREFERENCES_NAME = "com_weibo_sdk_android";
//    private static final String KEY_UID = "uid";
//    private static final String KEY_ACCESS_TOKEN = "access_token";
//    private static final String KEY_EXPIRES_IN = "expires_in";
//    private static final String KEY_REFRESH_TOKEN = "refresh_token";

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void clearData(Context context) {

        SharedPreferences sp = getSharedPreferences(context);
        //获取要保留的数据
        String account = sp.getString(USERNAME, "");
        //清除所有数据
        sp.edit().clear().apply();
        //重新设置要保留的数据
        sp.edit().putBoolean(FIRST_INSTALL, false)
                .putString(USERNAME, account)
                .apply();
    }

    public static void setCookie(Context context, String cookie) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(COOKIE, cookie).apply();
    }

    public static String getCookie(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(COOKIE, "");
    }

    public static void setSessionId(Context context, String cookie) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(SESSION_ID, cookie).apply();
    }

    public static String getSessionId(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(SESSION_ID, "");
    }


    //设置登录平台
//    public static void setPlatform(Context context, int platform) {
//        SharedPreferences sp = getSharedPreferences(context);
//        sp.edit().putInt(PLATFORM, platform).apply();
//    }

    public static int getPlatform(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getInt(PLATFORM, 0);
    }

//    public static void setDefaultAddress(Context context, AddressBean address) {
//        SharedPreferences sp = getSharedPreferences(context);
//        if (address == null) {
//            sp.edit().putString(DEFAULT_ADDRESS, "").apply();
//        } else {
//            sp.edit().putString(DEFAULT_ADDRESS, new Gson().toJson(address)).apply();
//        }
//    }

//    public static AddressBean getDefaultAddress(Context context) {
//        SharedPreferences sp = getSharedPreferences(context);
//        String address = sp.getString(DEFAULT_ADDRESS, "");
//        if (TextUtils.isEmpty(address))
//            return null;
//        return new Gson().fromJson(address, AddressBean.class);
//    }

//    //新浪微博AccessToken
//    public static void setAccessToken(Context context, Oauth2AccessToken token) {
//        if (null == context || null == token) {
//            return;
//        }
//        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
//        SharedPreferences.Editor editor = pref.edit();
//        editor.putString(KEY_UID, token.getUid());
//        editor.putString(KEY_ACCESS_TOKEN, token.getToken());
//        editor.putString(KEY_REFRESH_TOKEN, token.getRefreshToken());
//        editor.putLong(KEY_EXPIRES_IN, token.getExpiresTime());
//        editor.apply();
//    }

//    public static Oauth2AccessToken getAccessToken(Context context) {
//        if (null == context) {
//            return null;
//        }
//        Oauth2AccessToken token = new Oauth2AccessToken();
//        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
//        token.setUid(pref.getString(KEY_UID, ""));
//        token.setToken(pref.getString(KEY_ACCESS_TOKEN, ""));
//        token.setRefreshToken(pref.getString(KEY_REFRESH_TOKEN, ""));
//        token.setExpiresTime(pref.getLong(KEY_EXPIRES_IN, 0));
//        return token;
//    }

    public static void setFirstInstall(Context context, boolean firstInstall) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putBoolean(FIRST_INSTALL, firstInstall).apply();
    }

    public static boolean isFirstInstall(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getBoolean(FIRST_INSTALL, true);
    }
    private static final String SHARE_PREFS_NAME = "linkfun";
    private static SharedPreferences mSharedPreferences;

    public static void putBoolean(Context ctx, String key, boolean value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        mSharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context ctx, String key,
                                     boolean defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        return mSharedPreferences.getBoolean(key, defaultValue);
    }

    public static void putString(Context ctx, String key, String value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        mSharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(Context ctx, String key, String defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        return mSharedPreferences.getString(key, defaultValue);
    }
    public static void setUserName(Context context, String password) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(USERNAME, password).apply();
    }

    public static String getUserName(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(USERNAME, "");
    }
    public static void setPassword(Context context, String password) {
        SharedPreferences sp = getSharedPreferences(context);
        sp.edit().putString(PASSWORD, password).apply();
    }

    public static String getPassword(Context context) {
        SharedPreferences sp = getSharedPreferences(context);
        return sp.getString(PASSWORD, "");
    }

    public static void putInt(Context ctx, String key, int value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putInt(key, value).commit();
    }

    public static long getLong(Context ctx, String key, long defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        return mSharedPreferences.getLong(key, defaultValue);
    }
    public static void putLong(Context ctx, String key, long value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }
        mSharedPreferences.edit().putLong(key, value).commit();
    }

    public static int getInt(Context ctx, String key, int defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        return mSharedPreferences.getInt(key, defaultValue);
    }
    public static void putFloat(Context ctx, String key, float value) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        mSharedPreferences.edit().putFloat(key, value).commit();
    }

    public static float getFloat(Context ctx, String key, float defaultValue) {
        if (mSharedPreferences == null) {
            mSharedPreferences = ctx.getSharedPreferences(SHARE_PREFS_NAME,
                    Context.MODE_PRIVATE);
        }

        return mSharedPreferences.getFloat(key, defaultValue);
    }
}
