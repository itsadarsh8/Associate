package com.example.associate.ArchitectureComponents;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.associate.Object.Word;

import java.util.List;

@Dao
public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    @Delete
    void deleteWord(Word word);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Update
    void update(Word word);

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    DataSource.Factory<Integer,Word> getAll();
}
