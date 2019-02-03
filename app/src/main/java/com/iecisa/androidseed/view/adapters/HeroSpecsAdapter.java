package com.iecisa.androidseed.view.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.domain.HeroAttribute;

import java.util.List;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 4/1/19.
 * email: jmanuel_munoz@iecisa.com
 */

public class HeroSpecsAdapter extends RecyclerView.Adapter<HeroSpecViewHolder>{
    private static final String TAG = HeroesAdapter.class.getSimpleName();

    private List<HeroAttribute> attributes;
    private Context context;

    public HeroSpecsAdapter(List<HeroAttribute> attributes, Context context) {
        this.attributes = attributes;
        this.context = context;
    }

    @NonNull
    @Override
    public HeroSpecViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_attr_item, parent, false);
        return new HeroSpecViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroSpecViewHolder holder, int position) {
        try {
            holder.bindSpec(attributes.get(position));
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            Log.e(TAG, e.toString());
        }
    }

    @Override
    public int getItemCount() {
        try {
            return this.attributes.size();
        } catch (NullPointerException e) {
            Log.e(TAG, e.toString());
            return 0;
        }
    }
}
