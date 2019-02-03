package com.iecisa.androidseed.view.adapters;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.domain.HeroAttribute;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jesús Manuel Muñoz Mazuecos
 * on 4/1/19.
 * email: jmanuel_munoz@iecisa.com
 */

public class HeroSpecViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    TextView title;

    @BindView(R.id.description)
    TextView description;

    public HeroSpecViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindSpec(HeroAttribute heroAttribute) {
        title.setText(heroAttribute.getAttribute());
        description.setText(heroAttribute.getDescription());
    }
}
