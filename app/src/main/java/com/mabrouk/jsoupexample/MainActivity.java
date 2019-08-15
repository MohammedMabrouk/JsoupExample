package com.mabrouk.jsoupexample;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //new Content().execute();

        // images directory
        // MasterContentDiv WithBanner > MainContentDiv > MainPanel > Carousel > ul > li(s)

        // image details
        // src: ul > li.ContentText > div.container > h2.Title

        new ShowsAsync().execute();
    }

    private static class ShowsAsync extends AsyncTask<Void, Void, Void> {

        String pageTitle = "";
        Elements showsContainers;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document document = Jsoup.connect("http://www.natgeotv.com/ae/listings").get();

                pageTitle = document.title();

                showsContainers = document.select("div.MasterContentDiv.WithBanner > div.MainContentDiv > div.PageContent > ul#ScheduleList > li.ScheduleDayPeriodSeparator");

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.v("tagg", pageTitle);
            Log.v("tagg", showsContainers.size()+"");

            Elements showsElements;
            for(Element e : showsContainers){
                // get day period title
                Log.v("tagg", e.select("div.ScheduleDayPeriod > h3").text());
                Log.v("tagg", "=============================================");

                showsElements = e.select("ul.FloatLeft > li");

                for(Element el : showsElements){
                    //TODO: check if show has hyperlink (show details)
                    
                    // get show title
                    Log.v("tagg", el.select("ul > li.ScheduleDayTitle > a > span:nth-child(1)").text());
                    // get show sub-title
                    Log.v("tagg", el.select("ul > li.ScheduleDayTitle > a > span:nth-child(3)").text());
                    // get show time
                    Log.v("tagg", el.select("ul > li.ScheduleDayHour").text());
                    // get show link
                    Log.v("tagg", el.select("ul > li.ScheduleDayTitle > a").attr("href"));
                }
            }
        }
    }

    private static class Content extends AsyncTask<Void, Void, Void> {

        String pageTitle = "";
        Elements images;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                //Connect to the website

                Document document = Jsoup.connect("http://www.natgeotv.com/ae").get();

                pageTitle = document.title();

                images = document.select("div.MasterContentDiv.WithBanner > div.MainContentDiv > div.MainPanel > div.Carousel > ul > li");

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.v("tagg", pageTitle);
            Log.v("tagg", images.size()+"");

            for(Element e : images){
                // get image text
                Log.v("tagg", e.select("ul > li.ContentText > div.container > h2.Title").text());
                // get image link
                Log.v("tagg", e.select("ul > li.MainPanelImage > a > img").attr("src"));
                // get program link
                Log.v("tagg", e.select("ul > li.MainPanelImage > a").attr("href"));
            }
        }
    }

}
