package com.tone.coast.movie.util;


import com.tone.coast.movie.model.BannerEntity;
import com.tone.coast.movie.model.ChannelEntity;
import com.tone.coast.movie.model.DataResult;
import com.tone.coast.movie.model.HomeEntity;
import com.tone.coast.movie.model.HomeItemEntity;
import com.tone.coast.movie.model.MovieEntity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

public class DataProvide {

    public static final String BASE_URL = "https://m.kankanwu.com";

    public static Observable<DataResult<HomeEntity>> getHome() {
        return Observable
                .create(new ObservableOnSubscribe<DataResult<HomeEntity>>() {
                    @Override
                    public void subscribe(ObservableEmitter<DataResult<HomeEntity>> emitter) throws Exception {
                        Document document = Jsoup.connect(BASE_URL).timeout(5000).post();
                        HomeEntity homeEntity = new HomeEntity();

                        Elements banners = document.select("ul[class=focusList] > li");
                        List<BannerEntity> bannerList = new ArrayList<>();
                        for (Element element : banners) {
                            BannerEntity banner = new BannerEntity();
                            String name = element.getElementsByClass("sTxt").text();
                            String image = element.select("img").attr("src");
                            String url = element.select("a").attr("href");

                            banner.image = BASE_URL + image;
                            banner.name = name;
                            banner.url = BASE_URL + url;
                            bannerList.add(banner);
                        }
                        homeEntity.banners = bannerList;


                        List<ChannelEntity> channelList = new ArrayList<>();
                        Elements channels = document.select("p[class=headerChannelList] > a");
                        for (Element element : channels) {
                            ChannelEntity channel = new ChannelEntity();
                            String url = element.select("a").attr("href");
                            String name = element.select("a").text();
                            channel.name = name;
                            channel.url = BASE_URL + url;
                            channelList.add(channel);
                        }
                        homeEntity.channels = channelList;


                        List<HomeItemEntity> itemList = new ArrayList<>();
                        try {
                            Elements itemNames = document.select("div[class=modo_title top] > h2");
                            Elements items = document.select("div[class=all_tab]  ul[class=list_tab_img]");

                            for (int i = 0; i < items.size(); i++) {
                                Element elementName = itemNames.get(i);

                                Element elementItem = items.get(i);

                                HomeItemEntity entity = new HomeItemEntity();

                                String name = elementName.select("a").text();
                                entity.name = name;

                                List<MovieEntity> movies = new ArrayList<>();
                                Elements subs = elementItem.select("li");
                                for (Element element : subs) {
                                    String movieName = element.select("a").attr("title");
                                    String movieUrl = element.select("a").attr("href");
                                    MovieEntity movieEntity = new MovieEntity();
                                    movieEntity.name = movieName;
                                    movieEntity.image = BASE_URL + movieUrl;
                                    movies.add(movieEntity);
                                }
                                entity.movies = movies;

                                itemList.add(entity);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        homeEntity.items = itemList;
                        DataResult<HomeEntity> result = new DataResult<>();
                        result.data = homeEntity;
                        result.code = 200;
                        result.msg = "拉取成功";
                        emitter.onNext(result);
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<DataResult<HomeEntity>>>() {
                    @Override
                    public ObservableSource<DataResult<HomeEntity>> apply(Throwable throwable) throws Exception {
                        DataResult<HomeEntity> result = new DataResult<>();
                        result.data = null;
                        result.code = 400;
                        result.msg = "拉取失败";
                        return Observable.just(result);
                    }
                });
    }

}
