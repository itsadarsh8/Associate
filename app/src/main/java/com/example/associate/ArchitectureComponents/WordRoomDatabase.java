package com.example.associate.ArchitectureComponents;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.associate.Object.Word;

@Database(entities = {Word.class},version = 1,exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();
    private static WordRoomDatabase INSTANCE;

    public static synchronized WordRoomDatabase getDatabase(final Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                    WordRoomDatabase.class,"word_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return  INSTANCE;
    }


}
