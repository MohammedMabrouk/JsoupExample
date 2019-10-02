package com.mabrouk.jsoupexample;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GetSliderContentAsync.sliderImagesListener {

    SliderAdapter sliderAdapter;
    RecyclerView sliderRecyclerView, sliderRecyclerView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sliderRecyclerView = findViewById(R.id.slider_recycler_view);
        sliderRecyclerView.setLayoutManager(new CenterZoomLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sliderRecyclerView.setHasFixedSize(true);

        sliderRecyclerView2 = findViewById(R.id.slider_recycler_view2);
        sliderRecyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        sliderRecyclerView2.setHasFixedSize(true);

        new GetSliderContentAsync(this).execute();

//        new getAllProgramsAsync().execute();
    }

    @Override
    public void onSliderImagesFetched(final List<String> imagesUrlList, List<String> imagesTitlesList) {
        sliderAdapter = new SliderAdapter(imagesUrlList, imagesTitlesList, this);
        sliderRecyclerView.setAdapter(sliderAdapter);

        sliderRecyclerView2.setAdapter(sliderAdapter);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(sliderRecyclerView);
//        carouselView = findViewById(R.id.carouselView);
//        carouselView.setPageCount(imagesTitlesList.size());
//        carouselView.setImageListener(new ImageListener() {
//            @Override
//            public void setImageForPosition(int i, ImageView imageView) {
////                Picasso.get().load(imagesUrlList.get(i))
//////                        .error(context.getResources().getDrawable(R.drawable.car_placeholder))
////                        .into(imageView);
//                imageView.setImageResource(R.drawable.ic_launcher_background);
//            }
//        });

//        carouselView.setPageCount(imagesTitlesList.size());
//        carouselView.setImageListener(imageListener);


//        carouselView.setImageListener(imageListener);
    }

    public class CenterZoomLayoutManager extends LinearLayoutManager {

        private final float mShrinkAmount = 0.15f;
        private final float mShrinkDistance = 0.9f;

        public CenterZoomLayoutManager(Context context) {
            super(context);
        }

        public CenterZoomLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        @Override
        public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
            int orientation = getOrientation();
            if (orientation == VERTICAL) {
                int scrolled = super.scrollVerticallyBy(dy, recycler, state);
                float midpoint = getHeight() / 2.f;
                float d0 = 0.f;
                float d1 = mShrinkDistance * midpoint;
                float s0 = 1.f;
                float s1 = 1.f - mShrinkAmount;
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    float childMidpoint =
                            (getDecoratedBottom(child) + getDecoratedTop(child)) / 2.f;
                    float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                    float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                    child.setScaleX(scale);
                    child.setScaleY(scale);
                }
                return scrolled;
            } else {
                return 0;
            }
        }

        @Override
        public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
            int orientation = getOrientation();
            if (orientation == HORIZONTAL) {
                int scrolled = super.scrollHorizontallyBy(dx, recycler, state);

                float midpoint = getWidth() / 2.f;
                float d0 = 0.f;
                float d1 = mShrinkDistance * midpoint;
                float s0 = 1.f;
                float s1 = 1.f - mShrinkAmount;
                for (int i = 0; i < getChildCount(); i++) {
                    View child = getChildAt(i);
                    float childMidpoint =
                            (getDecoratedRight(child) + getDecoratedLeft(child)) / 2.f;
                    float d = Math.min(d1, Math.abs(midpoint - childMidpoint));
                    float scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0);
                    child.setScaleX(scale);
                    child.setScaleY(scale);
                }
                return scrolled;
            } else {
                return 0;
            }

        }
    }

    private static class getProgramDetailsAsync extends AsyncTask<Void, Void, Void> {
        Document document;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                document = Jsoup.connect("http://www.natgeotv.com/ae/animal-fight-club/about").get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.v("tagg", document.title());

            // get show title
            Log.v("tagg", document.select("div.MasterContentDiv.WithBanner > div.MainContentDiv > div.ShowsMasterHeader > ul.ShowTextArea > li:nth-child(1) > h1").text());

            // get show time
            Log.v("tagg", document.select("div.MasterContentDiv.WithBanner > div.MainContentDiv > div.ShowsMasterHeader > ul.ShowTextArea > li#Schedule > span").text());


        }
    }

    private static class getAllProgramsAsync extends AsyncTask<Void, Void, Void> {

        String pageTitle = "";
        Elements showsContainers;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                Document document = Jsoup.connect("http://www.natgeotv.com/ae/listings/ngc/190819").get();

                pageTitle = document.title();

                showsContainers = document.select("div.MasterContentDiv.WithBanner > div.MainContentDiv > div.PageContent > ul#ScheduleList > li.ScheduleDayPeriodSeparator");

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.v("tagg", pageTitle);
            Log.v("tagg", showsContainers.size() + "");

            Elements showsElements;
            for (Element e : showsContainers) {
                // get day period title
                Log.v("tagg", e.select("div.ScheduleDayPeriod > h3").text());
                Log.v("tagg", "=============================================");

                showsElements = e.select("ul.FloatLeft > li");

                for (Element el : showsElements) {
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
}
