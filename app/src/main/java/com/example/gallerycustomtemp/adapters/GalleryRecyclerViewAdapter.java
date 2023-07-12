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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gallerycustomtemp.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

/**
 * @Author: naftalikomarovski
 * @Date: 2023/06/08
 */
public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryRecyclerViewAdapter.GalleryRecyclerViewHolder> {

    private static final String TAG = "Test_Code";

    private Context context;
    private List<String> images;
    protected PhotoListener photoListener;

    public GalleryRecyclerViewAdapter(Context context, List<String> images, PhotoListener photoListener) {
        this.context = context;
        this.images = images;
        this.photoListener = photoListener;
    }

    @NonNull
    @Override
    public GalleryRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_item, parent, false);
        return new GalleryRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryRecyclerViewHolder holder, int position) {
        holder.bind(images.get(position), position);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    class GalleryRecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView uriTextView;
        private View view;

        public GalleryRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_image_view);
            uriTextView = itemView.findViewById(R.id.uri_text_view);
            view = itemView;
        }

        void bind(String image, int position) {
//            Glide.with(context).load(image).load(imageView);
//            Glide.with(context).load(Uri.fromFile(new File(image))).load(imageView);
            Log.d(TAG, "bind: Position: " + position + " Uri: " + Uri.fromFile(new File(image)));
            Picasso.get().load(Uri.fromFile(new File(image))).into(imageView);

            uriTextView.setText(image);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    photoListener.onPhotoClick(image);
                }
            });
        }
    }



    public interface PhotoListener {
        void onPhotoClick(String path);
    }
}
