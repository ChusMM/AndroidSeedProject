package com.iecisa.androidseed.view.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
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
