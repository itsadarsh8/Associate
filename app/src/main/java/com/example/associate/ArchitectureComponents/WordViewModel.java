package com.example.associate.ArchitectureComponents;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.example.associate.Object.Word;
import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mWordRepository;


    public WordViewModel(Application application){
        super(application);
        mWordRepository= new WordRepository(application);

    }
    public LiveData<PagedList<Word>> getAllWords(){
        return mWordRepository.getAllWords();
    }
    public void insert(Word word){
        mWordRepository.insert(word);
    }
    public void deleteWord(Word word) {mWordRepository.deleteWord(word);}
    public void updateWord(Word word) {mWordRepository.updateWord(word);}
    public void deleteAll() {mWordRepository.deleteAll();}
}
