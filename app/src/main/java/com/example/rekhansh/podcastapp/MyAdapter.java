package com.example.rekhansh.podcastapp;

import android.content.Context;
import android.content.Intent;
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
 * Created by Rekhansh on 3/8/2017.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private static ArrayList<Podcasts> mDataset;
    static Context mContext;
    MediaPlayer mediaPlayer;
    ImageView mImgPause;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView mCv;
        TextView mTitle;
        TextView mPosted;
        ImageView mImage;
        ImageView mPlay;

        public ViewHolder(View v) {
            super(v);
            mCv = (CardView) v.findViewById(R.id.cardView);
            mTitle = (TextView) v.findViewById(R.id.textViewTitle);
            mPosted = (TextView) v.findViewById(R.id.textViewPosted);
            mImage = (ImageView) v.findViewById(R.id.imageView);
            mPlay = (ImageView) v.findViewById(R.id.imageViewPlay);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(mContext, PlayActivity.class);
                    i.putExtra("DETAILS", mDataset.get(getAdapterPosition()));
                    mContext.startActivity(i);
                }
            });
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<Podcasts> mDataset, ImageView imgPuase) {
        this.mDataset = mDataset;
        this.mImgPause = imgPuase;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        mContext = parent.getContext();

        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTitle.setText(mDataset.get(position).getEpisodeTitle());
        Picasso.with(mContext).load(mDataset.get(position).getImgURL()).into(holder.mImage);
        holder.mPosted.setText("posted: " + (mDataset.get(position).getPostedOn()).substring(0, 16));
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        holder.mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }

                mImgPause.setImageResource(ic_media_pause);

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

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}


