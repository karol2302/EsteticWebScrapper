package com.example.esteticscrapper;

import android.os.Handler;
import android.os.Message;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Map;


enum Site {
    Estetik,
    EstetycznaHurtownia
}

public class ScrapperThread implements Runnable {
    // Document docSoup;
    String urlPost;
    Site site;
    String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:65.0) Gecko/20100101 Firefox/65.0";
    Map<String, String> postData;
    Handler handler;


    ScrapperThread(Site s, String Url, Map<String, String> postData, Handler handler) {
        //   this.docSoup = docSoup;
        this.urlPost = Url;
        this.site = s;
        this.postData = postData;
        this.handler = handler;
    }

    public void run() {

        try {
            Document docSoup = Jsoup.connect(this.urlPost + "/pl/s").ignoreContentType(true).userAgent(USER_AGENT).data(postData).post();


            switch (this.site) {
                case Estetik:
                    Elements el = docSoup.select(
                            "div.boxhead"
                    ).select("h1");
                    //System.out.println(el.text().toString());
                    Elements el2 = docSoup.select("div.product-inner-wrap");
                    int i = 0;
                    for (Element elem : el2) {
                        LinkData subD = new LinkData(elem.select("span.productname").text()
                                + "<br/><em>" + elem.select("div.product-inner-wrap").select("div.price.f-row").text()
                                + "</em>",
                                urlPost + elem.select("a.prodname.f-row").select("a[href]").attr("href"),
                                urlPost + elem.select("a.prodimage.f-row").select("img[src]").attr("data-src")
                        );
                        Message msg = handler.obtainMessage(1, (LinkData) subD);
                        handler.sendMessage(msg);

                    }
                    break;

                case EstetycznaHurtownia:

                    Elements e = docSoup.select(
                            "div.boxhead"
                    ).select("h1");
                    //System.out.println(e.text().toString());
                    Elements el3 = docSoup.select("div.product-inner-wrap");
                    for (Element elem : el3) {

                        LinkData subD = new LinkData(
                                "<font color='#000088'>" +
                                elem.select("span.productname").text()
                                + "<br/><em>" + elem.select("div.product-inner-wrap").select("div.price.f-row").text()
                                + "</em></font>",
                                urlPost + elem.select("a.prodname.f-row").select("a[href]").attr("href"),
                                urlPost + elem.select("a.prodimage.f-row").select("img[src]").attr("data-src")
                        );
                        Message msg = handler.obtainMessage(1, (LinkData) subD);
                        handler.sendMessage(msg);

                    }

                    break;
            }
        } catch
        (Exception ex) {
            ex.printStackTrace();

        }
    }
}
