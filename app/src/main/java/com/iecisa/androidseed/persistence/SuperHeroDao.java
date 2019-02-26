package com.iecisa.androidseed.persistence;


import com.iecisa.androidseed.domain.SuperHero;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface SuperHeroDao {
    @Query("SELECT * FROM super_heroes")
    Observable<List<SuperHero>> getAll();

    @Query("SELECT * FROM super_heroes WHERE uid IN (:heroIds)")
    List<SuperHero> loadAllByIds(int[] heroIds);

    @Query("SELECT * FROM super_heroes WHERE name LIKE :first LIMIT 1")
    SuperHero findByName(String first);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<SuperHero> superHeroes);

    @Query("DELETE FROM super_heroes")
    Single<Integer> deleteAll();
}
