package com.example.rekhansh.podcastapp;

import java.io.Serializable;

/**
 * Created by Rekhansh on 3/8/2017.
 */

public class Podcasts implements Serializable {
    private String imgURL;
    private String episodeTitle;
    private String postedOn;
    private String audioURL;
    private String desc;
    private int duration;

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getEpisodeTitle() {
        return episodeTitle;
    }

    public void setEpisodeTitle(String episodeTitle) {
        this.episodeTitle = episodeTitle;
    }

    public String getPostedOn() {
        return postedOn;
    }

    public void setPostedOn(String postedOn) {
        this.postedOn = postedOn;
    }

    public String getAudioURL() {
        return audioURL;
    }

    public void setAudioURL(String audioURL) {
        this.audioURL = audioURL;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Podcasts{" +
                "imgURL='" + imgURL + '\'' +
                ", episodeTitle='" + episodeTitle + '\'' +
                ", postedOn='" + postedOn + '\'' +
                ", audioURL='" + audioURL + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
