package com.example.betano;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "scores")
public class Score {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private int value;

    public Score(int value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
