package com.iecisa.androidseed.view.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.mvc.heroes.HeroesListViewMvc;
import com.iecisa.androidseed.util.ImageLoader;

import java.util.List;

public class HeroesAdapter extends RecyclerView.Adapter<HeroViewHolder> {
    private static final String TAG = HeroesAdapter.class.getSimpleName();

    private List<SuperHero> heroes;

    private final ImageLoader imageLoader;
    private final Context context;
    private final HeroesListViewMvc.Listener listener;

    public HeroesAdapter(List<SuperHero> heroes,
                         HeroesListViewMvc.Listener listener,
                         Context context,
                         ImageLoader imageLoader) {
        this.heroes = heroes;
        this.listener = listener;
        this.context = context;
        this.imageLoader = imageLoader;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_hero, parent, false);

        return new HeroViewHolder(view, imageLoader, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
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
