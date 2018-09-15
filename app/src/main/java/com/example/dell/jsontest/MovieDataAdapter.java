package com.example.dell.jsontest;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;

import java.util.List;

public class MovieDataAdapter extends RecyclerView.Adapter<MovieDataAdapter.ViewHolder>{
    private static final String TAG = "jjj";
    private Context context;
    private List<MovieData> movieData;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView movieTitle;
        ImageView movieImage;
        public ViewHolder(View view){
            super(view);
            movieTitle = view.findViewById(R.id.title_movie);
            movieImage = view.findViewById(R.id.image_movie);
        }
    }
    public MovieDataAdapter(List<MovieData> Json){
        movieData = Json;
}

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieData data = movieData.get(position);
        holder.movieTitle.setText(data.getTitle());
        Glide.with(context).load(data.getImage()).into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieData.size();
    }
}
