package com.example.m_expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.m_expense.database.DB_Handler;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button tripBtn;
    RecyclerView recyclerView;
    adapter[] adapters = new adapter[1];
    ArrayList<tripModel> list = new ArrayList<tripModel>();
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tripBtn = findViewById(R.id.tripBtn);
        recyclerView = findViewById(R.id.recyclerView);

        setup();
        tripBtn.setOnClickListener(v -> {
            //open screen that allows you to create trip
            Intent myIntent = new Intent(this, TripExpenses.class);
            myIntent.putExtra("hasData", false);
            startActivity(myIntent);
        });
    }

    private void setup() {
        DB_Handler prcndbHandler = new DB_Handler(this);
        list = (ArrayList<tripModel>) prcndbHandler.getAllTrips();

        //get and display all trips
        adapters[0] = new adapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapters[0]);
    }

    //display search option on home screen navigation bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
         searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                Intent myIntent = new Intent(this, SearchActivity.class);
                myIntent.putExtra("hasData", false);
                startActivity(myIntent);
                return super.onOptionsItemSelected(item);
        }
    }
}