package com.iecisa.androidseed.mvc.heroes;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iecisa.androidseed.R;
import com.iecisa.androidseed.domain.SuperHero;
import com.iecisa.androidseed.mvc.BaseViewMvc;
import com.iecisa.androidseed.util.ImageLoader;
import com.iecisa.androidseed.view.adapters.HeroesAdapter;

import java.util.List;

import butterknife.BindView;

public class HeroesListViewMvcImpl extends BaseViewMvc<HeroesListViewMvc.Listener>
        implements HeroesListViewMvc {

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.recycler_heroes)
    RecyclerView recyclerHeroes;

    private final ImageLoader mImageLoader;

    public HeroesListViewMvcImpl(LayoutInflater inflater, ViewGroup container, ImageLoader imageLoader) {
        setRootView(inflater.inflate(R.layout.activity_hero_list, container, false));
        super.bindView(this);

        mImageLoader = imageLoader;

        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
    }

    @Override
    public void bindSwipeRefresh(SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout.setOnRefreshListener(listener);
    }

    @Override
    public void onViewStartRefreshing() {
        recyclerHeroes.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void bindHeroes(List<SuperHero> superHeroes,
                           HeroesListViewMvc.Listener listener) {
        recyclerHeroes.setVisibility(View.VISIBLE);
        swipeRefreshLayout.setRefreshing(false);

        HeroesAdapter heroesAdapter = new HeroesAdapter(superHeroes, listener, getContext(), mImageLoader);
        recyclerHeroes.setHasFixedSize(true);
        recyclerHeroes.setAdapter(heroesAdapter);
        recyclerHeroes.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
}
