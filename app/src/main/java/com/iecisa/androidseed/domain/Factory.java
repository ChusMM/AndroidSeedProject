package com.iecisa.androidseed.domain;

import com.iecisa.androidseed.api.HeroListWrapper;
import com.iecisa.androidseed.api.HeroWrapper;

import java.util.ArrayList;
import java.util.List;

public class Factory {

    public static List<SuperHero> superHeroesFromHeroListWrapper(HeroListWrapper heroListWrapper) {
        List<SuperHero> superHeroes = new ArrayList<>();

        if (heroListWrapper != null && heroListWrapper.superheroes != null)  {
            for (HeroWrapper heroWrapper : heroListWrapper.superheroes) {
                SuperHero superHero = new SuperHero(heroWrapper.name, heroWrapper.photo);
                superHeroes.add(superHero);
            }
        }

        return superHeroes;
    }
}
