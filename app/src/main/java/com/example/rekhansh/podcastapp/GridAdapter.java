package com.example.rekhansh.podcastapp;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

import static android.R.drawable.ic_media_pause;

/**
 * Created by Rekhansh on 3/12/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {
    private ArrayList<Podcasts> mDataset;
    Context mContext;
    MediaPlayer mediaPlayer;
    ImageView mImgPuase;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCv;
        TextView mTitle;
        ImageView mImage;
        ImageView mLayoverImage;

        public ViewHolder(View v) {
            super(v);
            mCv = (CardView) v.findViewById(R.id.cardView);
            mTitle = (TextView) v.findViewById(R.id.textViewTitle);
            mImage = (ImageView) v.findViewById(R.id.imageView);
            mLayoverImage = (ImageView) v.findViewById(R.id.imageViewLayover);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public GridAdapter(ArrayList<Podcasts> mDataset, ImageView imgPuase) {
        this.mDataset = mDataset;
        this.mImgPuase = imgPuase;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout_grid_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        mContext = parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTitle.setText(mDataset.get(position).getEpisodeTitle());
        Picasso.with(mContext).load(mDataset.get(position).getImgURL()).into(holder.mImage);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        holder.mLayoverImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                mImgPuase.setImageResource(ic_media_pause);

                try {
                    mediaPlayer.setDataSource(mDataset.get(position).getAudioURL());
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
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
