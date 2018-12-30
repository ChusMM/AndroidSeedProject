package com.iecisa.androidseed.view.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.mvc.heroes.HeroesListViewMvc;
import com.iecisa.androidseed.util.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeroHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.hero_pic)
    ImageView heroPic;

    @BindView(R.id.hero_name)
    TextView heroName;

    private final HeroesListViewMvc.Listener listener;
    private final ImageLoader imageLoader;

    public HeroHolder(View itemView, HeroesListViewMvc.Listener listener, ImageLoader imageLoader) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        this.listener = listener;
        this.imageLoader = imageLoader;
    }

    public void bindHero(final SuperHero hero) {

        imageLoader.loadFromUrlBy43AspectRatio(hero.getPhoto(), heroPic, R.drawable.placeholder);

        heroName.setText(hero.getName());

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onHeroClicked(hero);
            }
        });
    }
}
