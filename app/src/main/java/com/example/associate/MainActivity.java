package com.example.associate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.ResolutionDimension;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.associate.Adapter.WordListAdapter;
import com.example.associate.Adapter.WordPageListAdapter;
import com.example.associate.ArchitectureComponents.WordViewModel;
import com.example.associate.Object.Word;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    private WordViewModel mWordViewModel;

    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPDATE_WORD_ACTIVITY_REQUEST_CODE = 2;
    private final String LOG_VALUE = MainActivity.class.getSimpleName();
    public static final String UPDATE_INTENT = "com.example.associate.MainActivity.UPDATEINTENT";
    public static final String UPDATE_ID = "com.example.associate.MainActivity.UPDATE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
      //  final WordListAdapter adapter = new WordListAdapter(this);
        final WordPageListAdapter pageListAdapter=new WordPageListAdapter(new DiffUtil.ItemCallback<Word>() {
            @Override
            public boolean areItemsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return oldItem.getId()==newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Word oldItem, @NonNull Word newItem) {
                return oldItem.equals(newItem);
            }
        });


        recyclerView.setAdapter(pageListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewWordActivity.class);
                startActivityForResult(intent,NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });

        mWordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        mWordViewModel.getAllWords().observe(this, new Observer<PagedList<Word>>() {
            @Override
            public void onChanged(PagedList<Word> words) {
                pageListAdapter.submitList(words);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Word myWord = pageListAdapter.getWordAtPosition(position);
                        Toast.makeText(MainActivity.this, "Deleting " +
                                myWord.getWord(), Toast.LENGTH_SHORT).show();

                        // Delete the word
                        mWordViewModel.deleteWord(myWord);
                    }
                });
        helper.attachToRecyclerView(recyclerView);

        pageListAdapter.setOnItemClickListener(new WordPageListAdapter.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Word word = pageListAdapter.getWordAtPosition(position);
                Intent updateIntent = new Intent(MainActivity.this, NewWordActivity.class);
                updateIntent.putExtra(UPDATE_INTENT, word.getWord());
                updateIntent.putExtra(UPDATE_ID,word.getId());
                startActivityForResult(updateIntent,UPDATE_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id=item.getItemId();
        switch(item_id){
            case R.id.delete_all:mWordViewModel.deleteAll();
            break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Word word = new Word(data.getStringExtra(NewWordActivity.EXTRA_REPLY));
            // Save the data
            mWordViewModel.insert(word);
            Toast.makeText(
                    this, "Saved", Toast.LENGTH_SHORT).show();
        }
        else if(requestCode==UPDATE_WORD_ACTIVITY_REQUEST_CODE && resultCode==RESULT_OK){
            String word=data.getStringExtra(NewWordActivity.EXTRA_REPLY);
            int id=data.getIntExtra(NewWordActivity.EXTRA_REPLY_ID,-1);

            if(id!=-1){
                Word update_word=new Word(id,word);
                mWordViewModel.updateWord(update_word);
                Toast.makeText(this,"Updating...",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this,"Unable to update...",
                        Toast.LENGTH_SHORT).show();
            }

        }

        else {
            Toast.makeText(
                    this, "Nothing to save", Toast.LENGTH_LONG).show();
        }
    }
}