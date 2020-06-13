package com.example.associate.ArchitectureComponents;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.associate.Object.Word;

import java.util.List;

public class WordRepository {
    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    WordRepository(Application application){
        WordRoomDatabase db=WordRoomDatabase.getDatabase(application);
        mWordDao=db.wordDao();
        mAllWords=mWordDao.getAll();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public void insert(Word word){
        new insertAsyncTask(mWordDao).execute(word);
    }
    public void deleteWord(Word word)  {
        new deleteWordAsyncTask(mWordDao).execute(word);
    }

    public void deleteAll(){
        new deleteAllWordAsyncTask(mWordDao).execute();
    }
    public void updateWord(Word word){
        new updateWordAsyncTask(mWordDao).execute(word);
    }
    private static class insertAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao mAsyncWordDao;

        public insertAsyncTask(WordDao asyncWordDao) {
            mAsyncWordDao = asyncWordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncWordDao.insert(words[0]);
            return null;
        }
    }
    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private WordDao mAsyncTaskDao;

        deleteWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }
    private static class updateWordAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao mAsyncWordDao;

        public updateWordAsyncTask(WordDao asyncWordDao) {
            mAsyncWordDao = asyncWordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncWordDao.update(words[0]);
            return null;
        }
    }
    private static class deleteAllWordAsyncTask extends AsyncTask<Word,Void,Void>{
        private WordDao mAsyncWordDao;

        public deleteAllWordAsyncTask(WordDao asyncWordDao) {
            mAsyncWordDao = asyncWordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncWordDao.deleteAll();
            return null;
        }
    }
}
