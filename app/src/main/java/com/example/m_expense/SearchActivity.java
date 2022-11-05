package com.example.m_expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.m_expense.database.DB_Handler;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    EditText editTextTextPersonName;
    RecyclerView recyclerView;
    adapter[] adapters = new adapter[1];
    ArrayList<tripModel> list = new ArrayList<tripModel>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        editTextTextPersonName = findViewById(R.id.editTextTextPersonName);
        recyclerView = findViewById(R.id.recyclerView);

        //search for trip each time a letter is entered in the text bar
        editTextTextPersonName.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                setup(s);
            }
        });
    }

    private void setup(CharSequence s) {
        DB_Handler prcndbHandler = new DB_Handler(this);
        list = (ArrayList<tripModel>) prcndbHandler.search(s.toString());

        adapters[0] = new adapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapters[0]);
    }
}