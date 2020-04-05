package com.vnoders.spotify_el8alaba;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import java.lang.ref.WeakReference;

/**
 * This class contains utility methods related to gradient generation from bitmaps asynchronously
 * using {@link Palette} class
 * <p>
 * Gradient is generated as a {@link SweepGradient} using two colors generated from {@link Palette}
 * class
 */
public class GradientUtils {


    /**
     * @param bitmap Bitmap image to generate the gradient colors from
     * @param view   The view which we will change its background by the generated gradient
     */
    public static void generate(Bitmap bitmap, View view) {
        GradientAsyncTask task = new GradientAsyncTask(view);
        task.execute(bitmap);
    }


    /**
     * @param resId Drawable image resource id to get the bitmap from it to generate the gradient
     *              colors from
     * @param view  The view which we will change its background by the generated gradient
     */
    public static void generate(@DrawableRes int resId, View view) {
        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), resId);
        generate(bitmap, view);
    }


    /**
     * @param image ImageView to get the bitmap from it to generate the gradient colors from
     * @param view  The view which we will change its background by the generated gradient
     */
    public static void generate(ImageView image, View view) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        generate(bitmap, view);
    }


    /**
     * Generates a gradient from bitmap image and sets this gradient as a background to the passed
     * view
     */
    private static class GradientAsyncTask extends AsyncTask<Bitmap, Void, Drawable> {

        /**
         * This is the view we will change its background by the generated gradient Used {@link
         * WeakReference} to avoid memory leak
         */
        private WeakReference<View> view;


        /**
         * @param view The view which we sets its background by the generated gradient
         */
        GradientAsyncTask(View view) {
            this.view = new WeakReference<>(view);
        }


        @Override
        @Nullable
        protected Drawable doInBackground(Bitmap... bitmaps) {
            Palette palette = Palette.from(bitmaps[0]).generate();

            ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
            mDrawable.getPaint().setShader
                    (new SweepGradient(0, 0,
                            palette.getDominantColor(0),
                            palette.getVibrantColor(0)));

            return mDrawable;
        }

        @Override
        protected void onPostExecute(@Nullable Drawable background) {
            if (view.get() != null) {
                view.get().setBackground(background);
            }
        }
    }

}
