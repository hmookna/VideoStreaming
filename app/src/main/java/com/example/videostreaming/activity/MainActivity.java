package com.example.videostreaming.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.videostreaming.R;
import com.example.videostreaming.Video;
import com.example.videostreaming.VideosAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private VideosAdapter adapter;
    private List<Video> videosList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerVieww();

    }

    private void recyclerVieww(){
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        videosList = new ArrayList<>();
        adapter = new VideosAdapter(this, videosList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView.addOnItemTouchListener(new VideosAdapter.RecyclerTouchListener(getApplicationContext(), recyclerView, new VideosAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {

            //    Video vdo = videosList.get(position);
                Intent intent = new Intent(MainActivity.this, testClass.class);
            //    intent.putExtra("vidUrl", adapter.getVidUrl());
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    //  Adding few video for testing cardview
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        Video a = new Video("True Romance", covers[0] , "http://www.html5videoplayer.net/videos/toystory.mp4");
        videosList.add(a);

        a = new Video("Xscpae", covers[1], "http://techslides.com/demos/sample-videos/small.mp4");
        videosList.add(a);

        a = new Video("Maroon 5",  covers[2], "http://www.html5videoplayer.net/videos/toystory.mp4");
        videosList.add(a);

        a = new Video("Born to Die", covers[3] , "http://techslides.com/demos/sample-videos/small.mp4");
        videosList.add(a);

        a = new Video("Honeymoon",  covers[4], "");
        videosList.add(a);

        a = new Video("I Need a Doctor",covers[5], "");
        videosList.add(a);

        a = new Video("Loud", covers[6], "");
        videosList.add(a);

        a = new Video("Legend",  covers[7], "");
        videosList.add(a);

        a = new Video("Hello",  covers[8], "");
        videosList.add(a);

        a = new Video("Greatest Hits", covers[9], "");
        videosList.add(a);

        adapter.notifyDataSetChanged();
    }

}
