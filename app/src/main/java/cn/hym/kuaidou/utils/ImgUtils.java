package cn.hym.kuaidou.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;

import cn.hym.kuaidou.KuaiDouApplication;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by Administrator on 2017/4/3.
 */

public class ImgUtils {

    public static void load(String url, ImageView targetView) {
        Glide.with(KuaiDouApplication.getContext())
                .load(url)
                .into(targetView);
    }

    public static void load(int resId, ImageView targetView) {
        Glide.with(KuaiDouApplication.getContext())
                .load(resId)
                .into(targetView);
    }

    public static void loadRound(String url, ImageView targetView) {
        Glide.with(KuaiDouApplication.getContext())
                .load(url)
                .bitmapTransform(new CropCircleTransformation(KuaiDouApplication.getContext()))
                .into(targetView);
    }

    public static void loadRound(int resId, ImageView targetView) {
        Glide.with(KuaiDouApplication.getContext())
                .load(resId)
                .bitmapTransform(new CropCircleTransformation(KuaiDouApplication.getContext()))
                .into(targetView);
    }
}
