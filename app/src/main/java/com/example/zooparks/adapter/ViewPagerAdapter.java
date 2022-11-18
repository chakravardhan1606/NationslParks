package com.example.zooparks.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zooparks.R;
import com.example.zooparks.model.Images;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends RecyclerView.Adapter<ViewPagerAdapter.ImageSlider> {


    private List<Images> imageList;
    public ViewPagerAdapter(List<Images> imageList) {
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageSlider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_pager_row,parent,false);

        return new ImageSlider(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ImageSlider holder, int position) {

        Picasso.get().load(imageList.get(position).getUrl()).fit().placeholder(android.R.drawable.stat_notify_error)
                .into(holder.imageView);

    }


    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ImageSlider extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public ImageSlider(@NonNull View itemView) {

            super(itemView);
            imageView=itemView.findViewById(R.id.view_pager_imageview);
        }
    }
}
