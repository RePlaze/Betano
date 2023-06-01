package com.example.betano;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ScoreDao {
    @Insert
    Completable insert(Score score);

    @Query("SELECT * FROM scores ORDER BY value DESC")
    Flowable<List<Score>> getAllScores();
}
