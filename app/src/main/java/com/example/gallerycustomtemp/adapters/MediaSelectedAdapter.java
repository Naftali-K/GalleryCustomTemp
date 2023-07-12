package com.example.gallerycustomtemp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gallerycustomtemp.R;
import com.example.gallerycustomtemp.models.MediaItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * @Author: naftalikomarovski
 * @Date: 2023/07/11
 */
public class MediaSelectedAdapter extends RecyclerView.Adapter<MediaSelectedAdapter.MediaSelectedViewHolder> {

    private Context context;
    private List<MediaItem> itemList;

    public MediaSelectedAdapter(Context context, List<MediaItem> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public MediaSelectedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new MediaSelectedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MediaSelectedViewHolder holder, int position) {
        holder.bind(itemList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class MediaSelectedViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageImageView;
        private TextView durationVideoTextView, uriTextView;
        private CardView videoFileCardView;

        public MediaSelectedViewHolder(@NonNull View itemView) {
            super(itemView);
            imageImageView = itemView.findViewById(R.id.image_image_view);
            durationVideoTextView = itemView.findViewById(R.id.duration_video_text_view);
            uriTextView = itemView.findViewById(R.id.uri_text_view);
            videoFileCardView = itemView.findViewById(R.id.video_file_card_view);
        }

        void bind(MediaItem mediaItem, int position) {
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
        }
    }
}
