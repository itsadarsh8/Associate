package com.example.associate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.associate.ArchitectureComponents.WordViewModel;
import com.example.associate.Object.Word;

public class NewWordActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY =
            "com.example.android.roomwordssample.REPLY";

    private  EditText mEditWordView;
    private WordViewModel mWordViewModel;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_word);
        mEditWordView = findViewById(R.id.edit_word);
        Button button = findViewById(R.id.button_save);
        mWordViewModel=new WordViewModel(getApplication());
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Word word=new Word(mEditWordView.getText().toString());
                mWordViewModel.insert(word);
                finish();
            }

        });

    }
}