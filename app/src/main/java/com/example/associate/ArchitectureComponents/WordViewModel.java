package com.example.associate.ArchitectureComponents;
import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.associate.Object.Word;
import java.util.List;

public class WordViewModel extends AndroidViewModel {

    private WordRepository mWordRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(Application application){
        super(application);
        mWordRepository= new WordRepository(application);
        mAllWords=mWordRepository.getAllWords();
    }
    public LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }
    public void insert(Word word){
        mWordRepository.insert(word);
    }
    public void deleteWord(Word word) {mWordRepository.deleteWord(word);}
}
