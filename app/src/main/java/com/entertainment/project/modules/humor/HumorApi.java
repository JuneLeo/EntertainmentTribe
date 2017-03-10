package com.entertainment.project.modules.humor;

import android.text.TextUtils;

import com.entertainment.project.common.Constants;
import com.entertainment.project.common.utils.Util;
import com.entertainment.project.modules.humor.domain.HumorContentModel;
import com.entertainment.project.modules.humor.domain.HumorModel;
import com.entertainment.project.modules.humor.domain.HumorUserModel;
import com.entertainment.project.common.retrofit.RxUtils;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Sick on 2017/2/16.
 */
public class HumorApi {
    /**
     * 获取网络请求爬取的Document
     *
     * @param url
     * @return
     */
    public static Observable<Document> getHumorNetWork(String url) {
        return Observable.just(url).flatMap(new Func1<String, Observable<Document>>() {
            @Override
            public Observable<Document> call(String url) {

                Connection conn = Jsoup.connect(url);
                // 修改http包中的header,伪装成浏览器进行抓取
                conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/    20100101 Firefox/32.0");
                Document document = null;

                try {
                    document = conn.get();

                } catch (IOException e) {
                    return Observable.error(new RuntimeException("网络异常"));
                }
                return Observable.just(document);
            }
        });
    }

    /**
     * 获取糗事百科的列表
     *
     * @param url
     * @return
     */
    public static Observable<ArrayList<HumorModel>> getHumorByPage(String url) {
        return getHumorNetWork(url)
                .map(new Func1<Document, ArrayList<HumorModel>>() {
                    @Override
                    public ArrayList<HumorModel> call(Document document) {
                        ArrayList<HumorModel> jmList = new ArrayList<HumorModel>();
                        Elements els = document.select("div.author");

                        for (Element el : els) {
                            HumorModel jm = new HumorModel();
                            HumorUserModel humorUserModel = new HumorUserModel();
                            humorUserModel.setUserName(el.children().select("a h2").text());
                            humorUserModel.setUserImg(el.children().select("a img").attr("src"));
                            humorUserModel.setUserLevel(el.children().select("div.articleGender").text());
                            String userId = el.children().select("a").attr("href");
                            if (!TextUtils.isEmpty(userId)) {
                                humorUserModel.setUserId(Util.parseLong(userId.substring(userId.indexOf("/", 1) + 1, userId.lastIndexOf("/"))));
                            }
                            jm.setHumorUserModel(humorUserModel);
                            jm.setContent(el.parent().select(".contentHerf").text());
                            String contentId = el.parent().select(".contentHerf").attr("href");
                            if (!TextUtils.isEmpty(contentId)) {
                                jm.setId(Util.parseLong(contentId.substring(contentId.indexOf("/", 1) + 1)));
                            }
                            Elements elImg = el.parent().select(".thumb a img[src$=jpg]");
                            if (!elImg.isEmpty()) {
                                jm.setImg(elImg.get(0).attr("src"));
                            }
                            jm.setPraiseNum(el.parent().select("div.stats").select("span.stats-vote").text());
                            jm.setCommentNum(el.parent().select("div.stats").select("span.stats-comments").select("a").text());
                            Elements elComment = el.parent().select("a.indexGodCmt");
                            if (!elComment.isEmpty()) {
                                jm.setVisitor(elComment.select("span.cmt-name").text());
                                jm.setComment(elComment.select("div.main-text").text());
                            }
                            jmList.add(jm);
                        }

                        System.out.println("Test.run");

                        return jmList;
                    }
                }).compose(RxUtils.rxSchedulerHelper());
    }

    /**
     * 获取糗事百科详细信息
     *
     * @param url
     * @return
     */
    public static Observable<HumorContentModel> getHumorMainContentById(String url) {
        return getHumorNetWork(url)
                .map(new Func1<Document, HumorContentModel>() {
                    @Override
                    public HumorContentModel call(Document document) {
                        HumorContentModel model = new HumorContentModel();
                        Elements els = document.select("div.col1");
                        model.setContent(els.select("div.content").text());
                        Elements commentEls = document.select("div.col1").select(".comments-list").select(".comments-list-item").select(".comments-table");
                        ArrayList<HumorUserModel> humorUserModels = new ArrayList<HumorUserModel>();
                        for (Element commentEl : commentEls) {
                            HumorUserModel humorUserModel = new HumorUserModel();
                            humorUserModel.setModelType(Constants.ModelType.TYPE_MODEL_DATA_1);
                            String commentUserId = commentEl.children().select(".comments-table-head").select("a").attr("href");
                            if (!TextUtils.isEmpty(commentUserId)) {
                                humorUserModel.setUserId(Util.parseLong(commentUserId.substring(commentUserId.indexOf("/", 1) + 1, commentUserId.lastIndexOf("/"))));
                            }
                            humorUserModel.setUserImg(commentEl.children().select(".comments-table-head").select("a img").attr("src"));
                            humorUserModel.setUserName(commentEl.children().select(".comments-table-main").select(".main-name").select(".cmt-name").text());
                            humorUserModel.setUserLevel(commentEl.children().select(".comments-table-main").select(".main-name").select(".articleGender").text());
                            humorUserModel.setContentGoodNum(commentEl.children().select(".comments-table-main").select(".main-name").select(".likenum").text());
                            humorUserModel.setContent(commentEl.children().select(".comments-table-main").select(".main-text").text());
                            humorUserModels.add(humorUserModel);
                        }

                        Elements commentEls2 = document.select("div.col1").select(".comments-wrap").select("div.avatars");
                        for (Element element : commentEls2) {
                            HumorUserModel humorUserModel = new HumorUserModel();
                            humorUserModel.setModelType(Constants.ModelType.TYPE_MODEL_DATA_2);
                            String commentUserId = element.children().select("a").attr("href");
                            if (!TextUtils.isEmpty(commentUserId)) {
                                humorUserModel.setUserId(Util.parseLong(commentUserId.substring(commentUserId.indexOf("/", 1) + 1, commentUserId.lastIndexOf("/"))));
                            }
                            humorUserModel.setUserImg(element.children().select("a img").attr("src"));
                            humorUserModel.setUserName(element.parent().select("div.replay").select("a").attr("title"));
                            humorUserModel.setUserLevel(element.parent().select("div.replay").select(".articleCommentGender").text());
                            humorUserModel.setContent(element.parent().select("div.replay").select("span.body").text());
                            humorUserModel.setPosition(element.parent().select("div.report").text());
                            humorUserModels.add(humorUserModel);
                        }


                        model.setCommentators(humorUserModels);
                        return model;
                    }
                }).compose(RxUtils.rxSchedulerHelper());
    }


}
