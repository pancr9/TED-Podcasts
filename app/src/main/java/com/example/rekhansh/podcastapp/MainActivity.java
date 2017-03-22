package com.example.rekhansh.podcastapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.drawable.ic_media_pause;
import static com.example.rekhansh.podcastapp.R.mipmap.ic_launcher;

public class MainActivity extends AppCompatActivity implements GetPodcastsAsync.PodcastData {

    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    GridLayoutManager mGridLayoutManager;
    MyAdapter mAdapter;
    GridAdapter gridAdapter;
    ProgressDialog pd;
    ArrayList<Podcasts> myDataset;
    SeekBar seekBar;
    ImageView imgPause;

    boolean isLinearLayout = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("TED Podcasts");
        String baseUrl = "https://www.npr.org/rss/podcast.php?id=510298";
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setEnabled(false);
        imgPause = (ImageView) findViewById(R.id.imageView2);
        imgPause.setImageBitmap(null);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerPodcastDisplay);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mGridLayoutManager = new GridLayoutManager(this, 3);

        myDataset = new ArrayList<Podcasts>();

        pd = new ProgressDialog(this);
        pd.setMessage("Loading Episodes...");
        pd.setCancelable(true);

        if (isConnectedOnline()) {
            pd.show();
            new GetPodcastsAsync(MainActivity.this).execute(baseUrl);
        }

        imgPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAdapter != null && mAdapter.mediaPlayer != null) {
                    if (mAdapter.mediaPlayer.isPlaying()) {
                        mAdapter.mediaPlayer.pause();
                        imgPause.setImageResource(R.drawable.circledplay);
                    } else {
                        mAdapter.mediaPlayer.start();
                        imgPause.setImageResource(ic_media_pause);
                    }
                }

                if (gridAdapter != null && gridAdapter.mediaPlayer != null) {
                    if (gridAdapter.mediaPlayer.isPlaying()) {
                        gridAdapter.mediaPlayer.pause();
                        imgPause.setImageResource(R.drawable.circledplay);
                    } else {
                        gridAdapter.mediaPlayer.start();
                        imgPause.setImageResource(ic_media_pause);
                    }
                }
            }
        });
    }

    @Override
    public void setUpData(ArrayList<Podcasts> x) {
        pd.dismiss();
        if (x != null) {

            myDataset = x;
            isLinearLayout = true;
            // use a linear layout manager
            mRecyclerView.setLayoutManager(mLayoutManager);
            // specify an adapter
            mAdapter = new MyAdapter(myDataset, imgPause);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refreshLayout:
                if (isLinearLayout) {
                    gridAdapter = new GridAdapter(myDataset, imgPause);
                    mRecyclerView.setLayoutManager(mGridLayoutManager);
                    mRecyclerView.setAdapter(gridAdapter);

                    if (gridAdapter.mediaPlayer != null) {
                        gridAdapter.mediaPlayer.stop();
                    }
                    if (mAdapter.mediaPlayer != null) {
                        mAdapter.mediaPlayer.stop();
                    }
                    imgPause.setImageBitmap(null);

                    isLinearLayout = false;

                } else {
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setAdapter(mAdapter);

                    if (gridAdapter.mediaPlayer != null) {
                        gridAdapter.mediaPlayer.stop();
                    }
                    if (mAdapter.mediaPlayer != null) {
                        mAdapter.mediaPlayer.stop();
                    }
                    imgPause.setImageBitmap(null);

                    isLinearLayout = true;
                }
                return true;
            case R.id.itemExit:
                Toast.makeText(this, "Press back to exit this application.", Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private boolean isConnectedOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            Toast.makeText(this, "No internet connection.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
