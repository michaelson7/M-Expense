package com.example.m_expense;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.m_expense.database.DB_Handler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button tripBtn,upload;
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
        upload = findViewById(R.id.button3);

        setup();
        tripBtn.setOnClickListener(v -> {
            //open screen that allows you to create trip
            Intent myIntent = new Intent(this, TripExpenses.class);
            myIntent.putExtra("hasData", false);
            startActivity(myIntent);
        });
        upload.setOnClickListener(v -> {
            try {
                uploadData();
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

    private void uploadData() throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JSONObject postData = new JSONObject();
        JSONArray array=new JSONArray();

        for (int i = 0; i < list.size(); i++)
        {
            tripModel model = list.get(i);
            JSONObject obj=new JSONObject();
            obj.put("name", model.getTitle());
            obj.put("description", model.getDescription());
            array.put(obj);
        }

        try {
            postData.put("userId", "wm123");
            postData.put("detailList",array);
            Log.e("POST DATA", postData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AppLoadingDialog appLoadingDialog = new AppLoadingDialog(MainActivity.this);
        appLoadingDialog.showDialog("uploading...");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "https://mocki.io/v1/c90e8bc9-9962-40bb-9e8c-027bc55aa10e", postData, response -> {
            Log.i("TAG", "validation: " + response.toString());
            appLoadingDialog.hideDialog();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.serializeNulls();
            Gson gson = gsonBuilder.create();
            try {
                String errorMessage = response.getString("message");
                Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                appLoadingDialog.hideDialog();
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }, error -> {
            appLoadingDialog.hideDialog();
            Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show();
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5000, 2, 1));
        jsonObjectRequest.setShouldCache(false);
        requestQueue.add(jsonObjectRequest);
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