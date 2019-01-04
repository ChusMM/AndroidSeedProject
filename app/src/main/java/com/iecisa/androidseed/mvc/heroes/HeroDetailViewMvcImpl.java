package com.iecisa.androidseed.mvc.heroes;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.domain.HeroAttribute;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.mvc.BaseViewMvc;
import com.iecisa.androidseed.util.ImageLoader;
import com.iecisa.androidseed.util.ImageUtils;
import com.iecisa.androidseed.view.adapters.HeroSpecsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HeroDetailViewMvcImpl extends BaseViewMvc<HeroDetailViewMvc.Listener>
        implements HeroDetailViewMvc {

    @BindView(R.id.hero_pic)
    ImageView heroPic;

    @BindView(R.id.hero_specs)
    RecyclerView heroSpecs;

    private final ImageLoader mImageLoader;
    private final ImageUtils mImageUtils;

    public HeroDetailViewMvcImpl(LayoutInflater inflater, ViewGroup container,
                                 ImageLoader imageLoader, final ImageUtils imageUtils) {
        setRootView(inflater.inflate(R.layout.activity_hero_detail, container));

        super.bindView(this);

        mImageLoader = imageLoader;
        mImageUtils = imageUtils;

        for (final HeroDetailViewMvc.Listener listener : getListeners()) {
            if (listener == this) {
                heroPic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onPictureClicked();
                    }
                });
                break;
            }
        }
    }

    @Override
    public void bindHeroDetail(SuperHero hero) {
        mImageLoader.loadFromUrlBy43AspectRatio(hero.getPhoto(), heroPic, R.drawable.placeholder);

        List<HeroAttribute> specList = new ArrayList<>();

        specList.add(new HeroAttribute(getString(R.string.name), hero.getName()));
        specList.add(new HeroAttribute(getString(R.string.real_name), hero.getRealName()));
        specList.add(new HeroAttribute(getString(R.string.height), hero.getHeight()));
        specList.add(new HeroAttribute(getString(R.string.power), hero.getPower()));
        specList.add(new HeroAttribute(getString(R.string.abilities), hero.getAbilities()));
        specList.add(new HeroAttribute(getString(R.string.groups), hero.getGroups()));

        HeroSpecsAdapter adapter = new HeroSpecsAdapter(specList, getContext());
        heroSpecs.setLayoutManager(new LinearLayoutManager(getContext()));
        heroSpecs.setAdapter(adapter);
    }
}
