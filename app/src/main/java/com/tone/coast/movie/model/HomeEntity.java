package com.tone.coast.movie.model;

import java.io.Serializable;
import java.util.List;

public class HomeEntity implements Serializable {
    public List<ChannelEntity> channels;
    public List<BannerEntity> banners;
    public List<HomeItemEntity> items;
}
