package com.example.gallerycustomtemp.models;

import android.net.Uri;

/**
 * @Author: naftalikomarovski
 * @Date: 2023/07/07
 */
public class MediaItem {
    private String fileName;
    private Uri fileUri;
    private long creationDate;
    private MediaType mediaType;

    private long duration;
    private int durationInSeconds;
    private int videoDurationMinutes;
    private int videoDurationSeconds;
    private boolean selected;

    public MediaItem(String fileName, Uri fileUri, long creationDate, MediaType mediaType) {
        this.fileName = fileName;
        this.fileUri = fileUri;
        this.creationDate = creationDate;
        this.mediaType = mediaType;
    }

    public String getFileName() {
        return fileName;
    }

    public Uri getFileUri() {
        return fileUri;
    }

    public long getCreationDate() {
        return creationDate;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public int getDurationInSeconds() {
        return durationInSeconds;
    }

    public void setDurationInSeconds(int durationInSeconds) {
        this.durationInSeconds = durationInSeconds;
    }

    public int getVideoDurationMinutes() {
        return videoDurationMinutes;
    }

    public void setVideoDurationMinutes(int videoDurationMinutes) {
        this.videoDurationMinutes = videoDurationMinutes;
    }

    public int getVideoDurationSeconds() {
        return videoDurationSeconds;
    }

    public void setVideoDurationSeconds(int videoDurationSeconds) {
        this.videoDurationSeconds = videoDurationSeconds;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public void changeSelected() {
        this.selected = !this.selected;
    }

    public enum MediaType {
        PHOTO,
        VIDEO
    }
}
