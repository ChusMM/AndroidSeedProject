package com.iecisa.androidseed.mvc.heroes;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.mvc.BaseViewMvc;
import com.iecisa.androidseed.util.ImageLoader;
import com.iecisa.androidseed.util.ImageUtils;
import com.iecisa.androidseed.view.adapters.HeroesAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HeroesListViewMvcImpl extends BaseViewMvc<HeroesListViewMvc.Listener>
        implements HeroesListViewMvc {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_heroes)
    RecyclerView recyclerHeroes;

    private final ImageLoader imageLoader;
    private final ImageUtils imageUtils;

    public HeroesListViewMvcImpl(LayoutInflater inflater, ViewGroup container,
                                 ImageLoader imageLoader, ImageUtils imageUtils) {
        setRootView(inflater.inflate(R.layout.activity_hero_list, container, false));

        this.imageLoader = imageLoader;
        this.imageUtils = imageUtils;

        Unbinder unbinder = ButterKnife.bind(this, getRootView());
        setUnbinder(unbinder);

        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
    }

    @Override
    public void unbind() {
        if (getUnbinder() != null) {
            getUnbinder().unbind();
        }
    }

    @Override
    public void bindSwipeRefresh(SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    @Override
    public void onViewRefresh() {
        recyclerHeroes.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void bindHeroes(List<SuperHero> superHeroes, HeroesListViewMvc.Listener listener) {
        recyclerHeroes.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);


        HeroesAdapter heroesAdapter = new HeroesAdapter(superHeroes, listener, imageLoader, getContext());
        recyclerHeroes.setHasFixedSize(true);
        recyclerHeroes.setAdapter(heroesAdapter);
        recyclerHeroes.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
}
