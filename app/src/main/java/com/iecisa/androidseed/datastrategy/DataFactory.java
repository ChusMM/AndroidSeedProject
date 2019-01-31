package com.iecisa.androidseed.datastrategy;

import com.iecisa.androidseed.api.HeroListWrapper;
import com.iecisa.androidseed.api.HeroWrapper;
import com.iecisa.androidseed.domain.SuperHero;

import java.util.ArrayList;
import java.util.List;

public class DataFactory {

    public static List<SuperHero> superHeroesFromHeroListWrapper(HeroListWrapper heroListWrapper) {
        List<SuperHero> superHeroes = new ArrayList<>();

        if (heroListWrapper != null && heroListWrapper.superheroes != null)  {
            for (HeroWrapper heroWrapper : heroListWrapper.superheroes) {
                SuperHero superHero = DataFactory.superHeroFromHeroWrapper(heroWrapper);
                superHeroes.add(superHero);
            }
        }

        return superHeroes;
    }

    public static SuperHero superHeroFromHeroWrapper(HeroWrapper heroWrapper) {
        return new SuperHero(heroWrapper.name, heroWrapper.photo, heroWrapper.realName,
                heroWrapper.height, heroWrapper.power, heroWrapper.abilities, heroWrapper.groups);

    }
}
