package com.vnoders.spotify_el8alaba;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.graphics.SweepGradient;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;
import androidx.palette.graphics.Palette.Swatch;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This class contains utility methods related to gradient generation from bitmaps asynchronously
 * using {@link Palette} class
 * <p>
 * Gradient is generated as a {@link SweepGradient} using two colors generated from {@link Palette}
 * class
 */
public class GradientUtils {


    // Types of Gradients to generate
    public static final int GRADIENT_SWEEP = 0;
    public static final int GRADIENT_LINEAR = 1;
    // Take dominant color from the image and the other is always black
    public static final int GRADIENT_LINEAR_BLACK = 2;
    // does not create a gradient it just return solid color only
    public static final int SOLID_DOMINANT_COLOR = 4;

    /**
     * @param bitmap Bitmap image to generate the gradient colors from
     * @param view   The view which we will change its background by the generated gradient
     */
    public static void generate(Bitmap bitmap, View view) {
        generate(bitmap, view, GRADIENT_SWEEP);
    }

    /**
     * @param bitmap       Bitmap image to generate the gradient colors from
     * @param view         The view which we will change its background by the generated gradient
     * @param gradientType type of the generated gradient
     */
    public static void generate(Bitmap bitmap, View view, int gradientType) {
        GradientAsyncTask task = new GradientAsyncTask(view, gradientType);
        task.execute(bitmap);
    }


    /**
     * @param resId Drawable image resource id to get the bitmap from it to generate the gradient
     *              colors from
     * @param view  The view which we will change its background by the generated gradient
     */
    public static void generate(@DrawableRes int resId, View view) {
        generate(resId, view, GRADIENT_SWEEP);
    }

    /**
     * @param resId        Drawable image resource id to get the bitmap from it to generate the
     *                     gradient colors from
     * @param view         The view which we will change its background by the generated gradient
     * @param gradientType type of the generated gradient
     */
    public static void generate(@DrawableRes int resId, View view, int gradientType) {
        Bitmap bitmap = BitmapFactory.decodeResource(view.getResources(), resId);
        generate(bitmap, view, gradientType);
    }


    /**
     * @param image ImageView to get the bitmap from it to generate the gradient colors from
     * @param view  The view which we will change its background by the generated gradient
     */
    public static void generate(ImageView image, View view) {
        generate(image, view, GRADIENT_SWEEP);
    }

    /**
     * @param image        ImageView to get the bitmap from it to generate the gradient colors from
     * @param view         The view which we will change its background by the generated gradient
     * @param gradientType type of the generated gradient
     */
    public static void generate(ImageView image, View view, int gradientType) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        generate(bitmap, view, gradientType);
    }

    /**
     * @param imageUrl Url to get the bitmap from it to generate the gradient colors
     * @param view     The view which we will change its background by the generated gradient
     */
    public static void generate(String imageUrl, View view) {
        generate(imageUrl, view, GRADIENT_SWEEP);
    }

    /**
     * @param imageUrl     Url to get the bitmap from it to generate the gradient colors
     * @param view         The view which we will change its background by the generated gradient
     * @param gradientType type of the generated gradient
     */
    public static void generate(String imageUrl, View view, int gradientType) {
        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
                generate(bitmap, view, gradientType);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
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

        int gradientType;

        int viewHeight;

        /**
         * @param view The view which we sets its background by the generated gradient
         */
        GradientAsyncTask(View view, int gradientType) {
            this.view = new WeakReference<>(view);
            this.gradientType = gradientType;
        }

        @Override
        protected void onPreExecute() {
            // Calculate the view's height
            // using this method instead of getHeight because view may be not fully instantiated
            // therefore the returned height is always zero
            view.get().measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            viewHeight = view.get().getMeasuredHeight();
        }

        @Override
        @Nullable
        protected Drawable doInBackground(Bitmap... bitmaps) {
            Palette palette = Palette.from(bitmaps[0]).generate();

            ShapeDrawable mDrawable = new ShapeDrawable(new RectShape());
            Shader shader;

            List<Swatch> swatches = new ArrayList<>(palette.getSwatches());

            Collections.sort(swatches, new Comparator<Swatch>() {
                @Override
                public int compare(Swatch s1, Swatch s2) {
                    return Integer.compare(s2.getPopulation(), s1.getPopulation());
                }
            });

            @ColorInt int dominantColor = palette.getDominantColor(Color.MAGENTA);
            @ColorInt int secondaryColor = swatches.get(swatches.size() / 4).getRgb();

            switch (gradientType) {
                case GRADIENT_LINEAR_BLACK:
                    shader = new LinearGradient(0, 0, 0, viewHeight,
                            secondaryColor, Color.BLACK, TileMode.CLAMP);
                    break;

                case GRADIENT_LINEAR:
                    shader = new LinearGradient(0, 0, 0, viewHeight,
                            dominantColor, secondaryColor, TileMode.CLAMP);
                    break;

                case SOLID_DOMINANT_COLOR:
                    shader = new LinearGradient(0, 0, 0, viewHeight,
                            dominantColor, dominantColor, TileMode.CLAMP);
                    break;

                default:
                case GRADIENT_SWEEP:
                    shader = new SweepGradient(0, 0, dominantColor, secondaryColor);
                    break;

            }

            mDrawable.getPaint().setShader(shader);
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
