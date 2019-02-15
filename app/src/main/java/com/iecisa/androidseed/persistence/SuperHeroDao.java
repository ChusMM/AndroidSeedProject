package com.iecisa.androidseed.persistence;

import com.iecisa.androidseed.domain.SuperHero;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface SuperHeroDao {
    @Query("SELECT * FROM super_heroes")
    List<SuperHero> getAll();

    @Query("SELECT * FROM super_heroes WHERE uid IN (:heroIds)")
    List<SuperHero> loadAllByIds(int[] heroIds);

    @Query("SELECT * FROM super_heroes WHERE name LIKE :first LIMIT 1")
    SuperHero findByName(String first);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<SuperHero> superHeroes);

    @Delete
    void delete(SuperHero superHero);
}
