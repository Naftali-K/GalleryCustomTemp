package com.example.gallerycustomtemp.adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gallerycustomtemp.DataManager;
import com.example.gallerycustomtemp.R;
import com.example.gallerycustomtemp.models.MediaItem;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * @Author: naftalikomarovski
 * @Date: 2023/07/07
 *
 * https://github.com/bumptech/glide
 */
public class MediaChatGTPAdapter extends RecyclerView.Adapter<MediaChatGTPAdapter.MediaChatGTPViewHolder> {

    private static final String TAG = "Test_code";

    private Context context;
//    private List<String> mediaList;
    private List<MediaItem> mediaItemList;
    private NumberSelectedCallBack callBack;

//    public MediaChatGTPAdapter(Context context, List<String> mediaList) {
//        this.context = context;
//        this.mediaList = mediaList;
//    }

    private int maxSelected = 20;
    private int selected = 0;

    public MediaChatGTPAdapter(Context context, List<MediaItem> mediaItemList, NumberSelectedCallBack callBack) {
        this.context = context;
        this.mediaItemList = mediaItemList;
        this.callBack = callBack;
    }

    @NonNull
    @Override
    public MediaChatGTPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new MediaChatGTPViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaChatGTPViewHolder holder, int position) {
        holder.bind(mediaItemList.get(position), position);
    }

    @Override
    public int getItemCount() {
//        return mediaList.size();
        return mediaItemList.size();
    }



    class MediaChatGTPViewHolder extends RecyclerView.ViewHolder {

        private ConstraintLayout container;
        private ImageView imageImageView;
        private CardView videoFileCardView;
        private TextView selectedTextView, uriTextView, durationVideoTextView;
        public MediaChatGTPViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            selectedTextView = itemView.findViewById(R.id.selected_text_view);
            imageImageView = itemView.findViewById(R.id.image_image_view);
            videoFileCardView = itemView.findViewById(R.id.video_file_card_view);
            uriTextView = itemView.findViewById(R.id.uri_text_view);
            durationVideoTextView = itemView.findViewById(R.id.duration_video_text_view);
        }

        /* Option with only address of picture/video. */
//        void bind(String uri, int position) {
////            Picasso.get().load(uri).into(imageImageView);
//            Log.d(TAG, "bind: File Position: " + position + " File: " + uri);
//            Picasso.get().load(Uri.fromFile(new File(uri))).into(imageImageView);
//            uriTextView.setText(position + uri);
//        }

        void bind(MediaItem mediaItem, int position) {
            Log.d(TAG, "bind: Item is: " + mediaItem.getMediaType()
                    + " \nFile name: " + mediaItem.getFileName()
                    + " \nFile Uri: " + mediaItem.getFileUri()
                    + " \nFile Made: " + mediaItem.getCreationDate()
                    + "\nFile Duration: " + mediaItem.getDuration()
                    + "\nFile Duration in Seconds: " + mediaItem.getDurationInSeconds()
                    + "\nFile Duration mm:ss: " + mediaItem.getVideoDurationMinutes() + ":" + mediaItem.getVideoDurationSeconds());

            setSelectedTextView(mediaItem);

            if (mediaItem.getMediaType() == MediaItem.MediaType.PHOTO) {
                Picasso.get().load(mediaItem.getFileUri()).into(imageImageView);
            } else if (mediaItem.getMediaType() == MediaItem.MediaType.VIDEO){
                videoFileCardView.setVisibility(View.VISIBLE);
                Glide.with(context)
                        .load(mediaItem.getFileUri())
                        .into(imageImageView);

                durationVideoTextView.setVisibility(View.VISIBLE);
                durationVideoTextView.setText(mediaItem.getVideoDurationMinutes() + ":" + mediaItem.getVideoDurationSeconds());
            }

            uriTextView.setText(mediaItem.getFileName());

            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaItem.changeSelected();
                    addRemoveSelectedItem(mediaItem);
                    setSelectedTextView(mediaItem);

                    updateSelectionNumbers();
                }
            });
        }

        private void setSelectedTextView(MediaItem mediaItem) {
            if (mediaItem.isSelected()) {
                selectedTextView.setVisibility(View.VISIBLE);
                selectedTextView.setText(String.valueOf(getSelectedItemsPosition(mediaItem)));
            } else {
                selectedTextView.setVisibility(View.GONE);
            }
        }

        private void addRemoveSelectedItem(MediaItem item) {
            if (item.isSelected()) {
                DataManager.addSelectedMediaItemToList(item);
                selected++;
            } else {
                DataManager.removeSelectedMediaItemFromList(item);
                selected--;
            }

            callBack.selected(selected);
        }

        private int getSelectedItemsPosition(MediaItem mediaItem) {
            return DataManager.getSelectedMediaItemList().indexOf(mediaItem);
        }

        private void updateSelectionNumbers() {
            notifyDataSetChanged();
        }
    }

    public interface NumberSelectedCallBack {
        void selected(int selected);
    }
}
