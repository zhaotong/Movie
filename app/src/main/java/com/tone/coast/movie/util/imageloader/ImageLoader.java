package com.tone.coast.movie.util.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.DrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;


public class ImageLoader {
    private static ImageLoader mInstance;

    public static ImageLoader getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoader.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoader();
                }
            }
        }
        return mInstance;
    }


    private ImageLoader() {
    }



    public RequestOptions getRequestOptions(int error, int place, int round) {
        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false)
                .centerCrop();
        if (error > 0) {
            options.error(error);
        }
        if (place > 0) {
            options.placeholder(place);
        }
        if (round > 0) {
            options.transform(new RoundedCorners(round));
        }
        return options;
    }

    public RequestOptions getRequestOptions(int error, int place) {
        return this.getRequestOptions(error, place, 0);
    }

    public RequestOptions getRequestOptions() {
        return this.getRequestOptions(0, 0, 0);
    }



    public <Y extends Target> void loadImage(Context context, Object url, RequestOptions options, Y target) {
        try {
            Glide.with(context)
                    .load(getUrl(url))
                    .apply(options)
                    .into(target);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadAsBitmapImage(Context context, Object url, ImageView imageView) {
        try {
            RequestOptions options = getRequestOptions();
            Glide.with(context).asBitmap().load(getUrl(url)).apply(options).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //加载原图大小 加载gif的时候，如果imageview设置的是warp，用这个方法
    public void loadImageWithOriginalSize(Context context, Object url, ImageView imageView) {
        try {
            RequestOptions options = getRequestOptions().override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
            loadImage(context, url, options, new GifDrawableImageViewTarget(imageView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadImage(Context context, Object url, ImageView imageView) {
        try {
            RequestOptions options = getRequestOptions();
            loadImage(context, url, options, new GifDrawableImageViewTarget(imageView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadImage(Context context, Object url, ImageView imageView, int error, int place) {
        try {
            RequestOptions options = getRequestOptions(error, place);
            loadImage(context, url, options, new GifDrawableImageViewTarget(imageView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadRoundImage(Context context, Object url, ImageView imageView, int error, int place) {
        loadRoundImage(context, url, imageView, error, place, 9);
    }


    public void loadRoundImage(Context context, Object url, ImageView imageView) {
        loadRoundImage(context, url, imageView, 0, 0, 9);
    }


    public void loadRoundImage(Context context, Object url, ImageView imageView, int error, int place, int round) {
        try {
            RequestOptions options = getRequestOptions(error, place, round);
            loadImage(context, url, options, new GifDrawableImageViewTarget(imageView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void loadCircleImage(Context context, Object url, ImageView imageView, int error, int place) {
        RequestOptions options = getRequestOptions(error, place, 0).circleCrop();
        loadImage(context, url, options, new GifDrawableImageViewTarget(imageView));
    }



    /**
     * 高斯模糊
     *
     * @param context
     * @param url
     * @param imageView
     */
    public void loadBlurImage(Context context, Object url, ImageView imageView) {

        try {
            RequestOptions options = getRequestOptions().transform(new BlurTransformation());

            Glide.with(context).asBitmap().load(getUrl(url)).apply(options).into(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    public Object getUrl(Object object) {
        if (object == null) {
            return "";
        }
        return object;
    }

    public void cleanMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    public static class GifDrawableImageViewTarget extends DrawableImageViewTarget {

        public GifDrawableImageViewTarget(ImageView view) {
            super(view);
        }

        @Override
        protected void setResource(@Nullable Drawable resource) {
            if (resource != null) {
                if (resource instanceof GifDrawable) {
                    GifDrawable gifDrawable = (GifDrawable) resource;
                    gifDrawable.setLoopCount(GifDrawable.LOOP_INTRINSIC);
                    view.setImageDrawable(gifDrawable);
                } else {
                    view.setImageDrawable(resource);
                }
            }
        }
    }


}
