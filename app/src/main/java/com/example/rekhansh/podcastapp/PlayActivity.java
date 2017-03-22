package com.example.rekhansh.podcastapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import static android.R.drawable.ic_media_pause;

public class PlayActivity extends AppCompatActivity {

    ImageView image;
    TextView desc;
    TextView pubDate;
    TextView duration;
    ImageView playPause;
    Podcasts podcast;
    SeekBar seekBar;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        podcast = (Podcasts) getIntent().getSerializableExtra("DETAILS");

        image = (ImageView) findViewById(R.id.imageViewDisp);
        playPause = (ImageView) findViewById(R.id.imageViewDispp);
        desc = (TextView) findViewById(R.id.textViewDescp);
        pubDate = (TextView) findViewById(R.id.textViewPubDate);
        duration = (TextView) findViewById(R.id.textViewDuration);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBar.setEnabled(false);

        Picasso.with(this).load(podcast.getImgURL()).into(image);
        desc.setText(podcast.getDesc());
        pubDate.setText(podcast.getPostedOn());
        duration.setText(podcast.getDuration() + "");

        playPause.setImageResource(R.drawable.circledplay);

        playPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();

                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    seekBar.setMax(podcast.getDuration());
                    Log.d("DEMO", "Max duration = " + mediaPlayer.getDuration() + "");
                    Log.d("DEMO", "Podcast duration = " + podcast.getDuration() + "");

                    playPause.setImageResource(ic_media_pause);

                    try {
                        mediaPlayer.setDataSource(podcast.getAudioURL());
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try {
                        mediaPlayer.prepare();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    mediaPlayer.start();
                    final Handler mHandler = new Handler();
                    PlayActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mediaPlayer != null) {
                                int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                                seekBar.setProgress(mCurrentPosition);
                                Log.d("DEMO", mCurrentPosition + "");
                            }
                            mHandler.postDelayed(this, 1000);
                        }
                    });

                } else {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        playPause.setImageResource(R.drawable.circledplay);
                    } else {
                        mediaPlayer.start();
                        playPause.setImageResource(ic_media_pause);
                    }
                }
            }
        });
    }
}
