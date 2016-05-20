package com.spica_travel.taku.gsapp;

import android.content.Context;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

//import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private ImageLoader imageLoader;
    private Context context;

    //List of superHeroes
    List<FavoriteRecord> favoriteRecords;

    public CardAdapter(List<FavoriteRecord> favoriteRecord, Context context){
        super();
        //Getting all the superheroes
        this.favoriteRecords = favoriteRecord;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        FavoriteRecord favoriteRecord =  favoriteRecords.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(favoriteRecord.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        holder.imageView.setImageUrl(favoriteRecord.getImageUrl(), imageLoader);
        holder.textViewTitle.setText(favoriteRecord.getTitle());
        holder.textViewAuthor.setText(String.valueOf(favoriteRecord.getAuthor()));
        holder.textViewComment.setText(favoriteRecord.getComment());
    }

    @Override
    public int getItemCount() {
        return favoriteRecords.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public NetworkImageView imageView;
        public TextView textViewTitle;
        public TextView textViewAuthor;
        public TextView textViewComment;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.image1);
            textViewTitle = (TextView) itemView.findViewById(R.id.title1);
            textViewAuthor= (TextView) itemView.findViewById(R.id.author1);
            textViewComment= (TextView) itemView.findViewById(R.id.text1);
        }
    }
}
