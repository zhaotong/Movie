package com.tone.coast.movie.ui.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.tone.coast.movie.R;
import com.tone.coast.movie.model.entity.BannerEntity;
import com.tone.coast.movie.model.entity.ListItemEntity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import me.drakeet.multitype.ItemViewBinder;

public class ViewBinderBanner extends ItemViewBinder<ListItemEntity, BannerHolder> {

    @NonNull
    @Override
    protected BannerHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.layout_banner_item, parent, false);
        return new BannerHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull BannerHolder holder, @NonNull ListItemEntity item) {

        Banner banner = holder.getView(R.id.banner);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        List<BannerEntity> bannerList = (List<BannerEntity>) item.data;
        List<String> urls = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (BannerEntity entity : bannerList) {
            urls.add(entity.image);
            titles.add(entity.name);
        }

        banner.setImages(urls);
        banner.setBannerTitles(titles);

        banner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                com.tone.coast.movie.util.imageloader.ImageLoader.getInstance().loadImage(context, path, imageView);
            }
        });

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {

            }
        });

        banner.start();

    }
}
