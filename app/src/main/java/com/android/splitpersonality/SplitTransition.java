package com.android.splitpersonality;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;

public class SplitTransition {
    public static Bitmap bmp = null;
    public static Bitmap mBmp1, mBmp2;
    private static int[] mLoc1;
    private static int[] mLoc2;
    private static ImageView mTopImage;
    private static ImageView mBottomImage;
    private static AnimatorSet mSetAnim;

    public static void startActivity(Activity currActivity, Intent intent, int splitYCoord) {

        View root = currActivity.getWindow().getDecorView().findViewById(android.R.id.content);
        root.setDrawingCacheEnabled(true);
        bmp = root.getDrawingCache();

        splitYCoord = (splitYCoord != -1 ? splitYCoord : bmp.getHeight() / 2);

        if (splitYCoord > bmp.getHeight())
            throw new IllegalArgumentException("Split Y coordinate [" + splitYCoord + "] exceeds the activity's height [" + bmp.getHeight() + "]");
        mBmp1 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), splitYCoord);
        mBmp2 = Bitmap.createBitmap(bmp, 0, splitYCoord, bmp.getWidth(), bmp.getHeight() - splitYCoord);
        mLoc1 = new int[]{0, root.getTop()};
        mLoc2 = new int[]{0, root.getTop() + splitYCoord};

        currActivity.startActivity(intent);
        currActivity.overridePendingTransition(0, 0);
    }

    public static void startActivity(Activity currActivity, Intent intent) {
        startActivity(currActivity, intent, -1);
    }

    public static void animate(final Activity destActivity, final int duration, final TimeInterpolator interpolator) {

        new Handler().post(new Runnable() {

            @Override
            public void run() {
                mSetAnim = new AnimatorSet();
                mTopImage.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                mBottomImage.setLayerType(View.LAYER_TYPE_HARDWARE, null);
                mSetAnim.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        clean(destActivity);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        clean(destActivity);
                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                Animator anim1 = ObjectAnimator.ofFloat(mTopImage, "translationX", mTopImage.getWidth() * -1);
                Animator anim2 = ObjectAnimator.ofFloat(mBottomImage, "translationX", mBottomImage.getWidth());

                if (interpolator != null) {
                    anim1.setInterpolator(interpolator);
                    anim2.setInterpolator(interpolator);
                }

                mSetAnim.setDuration(duration);
                mSetAnim.playTogether(anim1, anim2);
                mSetAnim.start();
            }
        });
    }

    public static void animate(final Activity destActivity, final int duration) {
        animate(destActivity, duration, new DecelerateInterpolator());
    }

    private static void clean(Activity activity) {
        if (mTopImage != null) {
            mTopImage.setLayerType(View.LAYER_TYPE_NONE, null);
            try {
                activity.getWindowManager().removeViewImmediate(mBottomImage);
            } catch (Exception ignored) {
            }
        }
        if (mBottomImage != null) {
            mBottomImage.setLayerType(View.LAYER_TYPE_NONE, null);
            try {
                activity.getWindowManager().removeViewImmediate(mTopImage);
            } catch (Exception ignored) {
            }
        }

        bmp = null;
    }

    public static void prepareAnimation(final Activity destActivity) {
        mTopImage = createImageView(destActivity, bmp, mLoc1);
        mBottomImage = createImageView(destActivity, bmp, mLoc2);
    }

    private static ImageView createImageView(Activity destActivity, Bitmap bmp, int loc[]) {
        ImageView imageView = new ImageView(destActivity);
        imageView.setImageBitmap(bmp);


        WindowManager.LayoutParams windowParams = new WindowManager.LayoutParams();
        windowParams.gravity = Gravity.LEFT;
        windowParams.x = loc[0];
        windowParams.y = loc[1];
        windowParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        windowParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        windowParams.flags =
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        windowParams.format = PixelFormat.TRANSLUCENT;
        windowParams.windowAnimations = 0;
        destActivity.getWindowManager().addView(imageView, windowParams);

        return imageView;
    }
}

