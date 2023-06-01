package com.example.betano;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Score.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "score_database";
    private static AppDatabase instance;

    public abstract ScoreDao scoreDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            // Your implementation for onCreate
                        }

                        @Override
                        public void onOpen(SupportSQLiteDatabase db) {
                            super.onOpen(db);
                            // Your implementation for onOpen
                        }
                    })
                    .build();
        }
        return instance;
    }
}
