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
import com.iecisa.androidseed.view.adapters.HeroesAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HeroesListViewMvcImpl extends BaseViewMvc<HeroesListViewMvc.Listener>
        implements HeroesListViewMvc {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_heroes)
    RecyclerView recyclerHeroes;

    public HeroesListViewMvcImpl(LayoutInflater inflater, ViewGroup container) {
        setRootView(inflater.inflate(R.layout.activity_hero_list, container, false));

        ButterKnife.bind(this, getRootView());
    }

    @Override
    public void onRefresh() {
        recyclerHeroes.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void bindHeroes(List<SuperHero> superHeroes, HeroesListViewMvc.Listener listener) {
        recyclerHeroes.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);


        HeroesAdapter heroesAdapter = new HeroesAdapter(superHeroes, listener, getContext());
        recyclerHeroes.setHasFixedSize(true);
        recyclerHeroes.setAdapter(heroesAdapter);
        recyclerHeroes.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
}
