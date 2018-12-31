package com.iecisa.androidseed.view.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.mvc.heroes.HeroesListViewMvc;
import com.iecisa.androidseed.util.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeroViewHolder extends BaseViewHolder {

    @BindView(R.id.hero_pic)
    ImageView heroPic;

    @BindView(R.id.hero_name)
    TextView heroName;

    private final HeroesListViewMvc.Listener listener;

    public HeroViewHolder(View itemView, ImageLoader imageLoader, HeroesListViewMvc.Listener listener) {
        super(itemView, imageLoader);
        ButterKnife.bind(this, itemView);

        this.listener = listener;
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
