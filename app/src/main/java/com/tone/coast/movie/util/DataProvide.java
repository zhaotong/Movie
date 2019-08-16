package com.tone.coast.movie.util;


import com.tone.coast.movie.model.entity.BannerEntity;
import com.tone.coast.movie.model.entity.ChannelEntity;
import com.tone.coast.movie.model.entity.DataResult;
import com.tone.coast.movie.model.entity.HomeEntity;
import com.tone.coast.movie.model.entity.HomeItemEntity;
import com.tone.coast.movie.model.entity.MovieEntity;

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
                        Document document = Jsoup
                                .connect(BASE_URL)
                                .header("cache-control","no-cache")
                                .header("Postman-Token","63dcdb61-b927-4b6c-8d4b-d112482a802a")
//                                .header("accept-language","zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
//                                .header("Content-Type", "application/json;charset=UTF-8")
//                                .header("User-Agent", "Mozilla/5.0 (Linux; Android 8.0; Pixel 2 Build/OPD3.170816.012) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.100 Mobile Safari/537.36")
                                .timeout(10000)
                                .get();
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
                                    String movieImage = element.select("div[class=picsize] > img").attr("src");
                                    String label = element.select("div[class=picsize]  label[class=title]").text();
                                    String score = element.select("div[class=picsize]  label[class=score]").text();
                                    String status = element.select("div[class=picsize]  label[class=status]").text();

                                    MovieEntity movieEntity = new MovieEntity();
                                    movieEntity.name = movieName;
                                    movieEntity.url = BASE_URL + movieUrl;
                                    movieEntity.image = BASE_URL + movieImage;
                                    movieEntity.label=label;
                                    movieEntity.score=score;
                                    movieEntity.status=status;

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
                        return HttpExceptionApi.handleException(throwable);
                    }
                });
    }

}
