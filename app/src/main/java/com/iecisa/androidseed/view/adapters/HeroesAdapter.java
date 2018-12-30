package com.iecisa.androidseed.view.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.mvc.heroes.HeroesListViewMvc;
import com.iecisa.androidseed.util.ImageLoader;

import java.util.List;

public class HeroesAdapter extends RecyclerView.Adapter<HeroHolder> {
    private static final String TAG = HeroesAdapter.class.getSimpleName();

    private List<SuperHero> heroes;
    private Context context;
    private HeroesListViewMvc.Listener listener;
    private final ImageLoader imageLoader;

    public HeroesAdapter(List<SuperHero> heroes, HeroesListViewMvc.Listener listener,
                         ImageLoader imageLoader, Context context) {
        this.heroes = heroes;
        this.context = context;
        this.listener = listener;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public HeroHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_hero, parent, false);
        return new HeroHolder(view, listener, imageLoader);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroHolder holder, int position) {
        try {
            holder.bindHero(heroes.get(position));
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public int getItemCount() {
        try {
            return this.heroes.size();
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
            return 0;
        }
    }
}
