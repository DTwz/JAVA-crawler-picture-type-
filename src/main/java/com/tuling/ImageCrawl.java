package com.tuling;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;import java.io.ByteArrayInputStream;


public class ImageCrawl {
    private static String url = "https://www.nipic.com/topic/show_27202_1.html";
    public static void main(String[] args) throws IOException {
        //apacheHttpClient();
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("li.new-search-works-item");
        for(int i=0;i<elements.size();i++){
            Elements imgElement = elements.get(i).select("a > img");
            Connection.Response response = Jsoup.connect("http:" + imgElement.attr("src"))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36")
                    .ignoreContentType(true).execute();
            String name = imgElement.attr("alt");
            ByteArrayInputStream stream = new ByteArrayInputStream(response.bodyAsBytes());
            FileUtils.copyInputStreamToFile(stream,new File("D://fileitem//"+name+".png"));
        }

    }

    private static void apacheHttpClient()  {
        HttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.104 Safari/537.36");

        HttpResponse  response = null;
        try{
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            System.out.println(EntityUtils.toString(entity));
        }catch (Exception e){

        }finally {

        }

    }
}
