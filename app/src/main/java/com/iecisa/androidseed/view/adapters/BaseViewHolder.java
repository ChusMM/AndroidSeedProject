package com.iecisa.androidseed.view.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.iecisa.androidseed.util.ImageLoader;

public class BaseViewHolder extends RecyclerView.ViewHolder {

    protected ImageLoader imageLoader;

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public BaseViewHolder(@NonNull View itemView, ImageLoader imageLoader) {
        super(itemView);
        this.imageLoader = imageLoader;
    }
}
