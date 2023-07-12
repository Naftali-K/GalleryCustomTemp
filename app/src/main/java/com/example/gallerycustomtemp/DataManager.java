package com.example.gallerycustomtemp;

import com.example.gallerycustomtemp.models.MediaItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: naftalikomarovski
 * @Date: 2023/07/11
 */
public class DataManager {
    private static List<MediaItem> selectedMediaItemList = new ArrayList<>();

    public static List<MediaItem> getSelectedMediaItemList() {
        return selectedMediaItemList;
    }

    public static void setSelectedMediaItemList(List<MediaItem> selectedMediaItemList) {
        DataManager.selectedMediaItemList = selectedMediaItemList;
    }

    public static void addSelectedMediaItemToList(MediaItem mediaItem) {
        selectedMediaItemList.add(mediaItem);
    }

    public static void removeSelectedMediaItemFromList(MediaItem mediaItem) {
        selectedMediaItemList.remove(mediaItem);
    }
}
