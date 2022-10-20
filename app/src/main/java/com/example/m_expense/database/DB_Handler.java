package com.example.m_expense.database;

import static java.lang.String.valueOf;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.m_expense.tripModel;

import java.util.ArrayList;
import java.util.List;

public class DB_Handler extends SQLiteOpenHelper {
    private Context mContext;
    public static final int DATABASE_VERSION_FRA = 1;
    public static final String DATABASE_NAME_FRA = "m-expressDB";

    public static final String tripTable = "tripTable";
    public static final String id = "id";
    public static final String title = "title";
    public static final String date = "date";
    public static final String name = "name";
    public static final String destination = "destination";
    public static final String description = "description";
    public static final String risk = "risk";
    public static final String imageLink = "imageLink";
    public static final String expenseType = "expenseType";
    public static final String expenseAmount = "expenseAmount";
    public static final String expenseTime = "expenseTime";
    public static final String expenseComments = "expenseComments";

    public DB_Handler(Context context) {
        super(context, DATABASE_NAME_FRA, null, DATABASE_VERSION_FRA);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CreateDB = "CREATE TABLE " + tripTable + "("
                + id + " INTEGER PRIMARY KEY,"
                + title + " TEXT NOT NULL UNIQUE,"
                + date + " TEXT NOT NULL,"
                + name + " TEXT,"
                + destination + " TEXT,"
                + description + " TEXT,"
                + risk + " TEXT NOT NULL,"
                + imageLink + " TEXT,"
                + expenseType + " TEXT,"
                + expenseAmount + " TEXT,"
                + expenseTime + " TEXT,"
                + expenseComments + " TEXT"+ ")";
        db.execSQL(CreateDB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + tripTable);
    }

    public void addData(tripModel model) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        
     //   values.put(id, model.getId());
        values.put(title, model.getTitle());
        values.put(date, model.getDate());
        values.put(name, model.getName());
        values.put(description, model.getDescription());
        values.put(destination, model.getDestination());
        values.put(risk, model.getRisk());
        values.put(imageLink, model.getImageLink());
        values.put(expenseType, model.getExpenseType());
        values.put(expenseAmount, model.getExpenseAmount());
        values.put(expenseTime, model.getExpenseTime());
        values.put(expenseComments, model.getExpenseComments());
        // Inserting Row
        db.insert(tripTable, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    @SuppressLint("Range")
    public List<tripModel> getAllTrips() {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM  " + tripTable;

        Cursor cursor = db.rawQuery(selectQuery, null);

        List<tripModel> cropModelList = new ArrayList<>();
        ;
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            tripModel cropModel = new tripModel(
                    cursor.getString(cursor.getColumnIndex(title)),
                    cursor.getString(cursor.getColumnIndex(date)),
                    cursor.getString(cursor.getColumnIndex(name)),
                    cursor.getString(cursor.getColumnIndex(destination)),
                    cursor.getString(cursor.getColumnIndex(risk)),
                    cursor.getString(cursor.getColumnIndex(description)),
                    cursor.getString(cursor.getColumnIndex(imageLink)),
                    cursor.getString(cursor.getColumnIndex(expenseType)),
                    cursor.getString(cursor.getColumnIndex(expenseAmount)),
                    cursor.getString(cursor.getColumnIndex(expenseTime)),
                    cursor.getString(cursor.getColumnIndex(expenseComments)),
                    cursor.getString(cursor.getColumnIndex(id)
            ));
            cropModelList.add(cropModel);
        }
        return cropModelList;
    }

    public int tripDelete(String ids) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(tripTable, id + "=" + ids, null) ;
    }

public void updateTrip(tripModel model){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();

    values.put(id, model.getId());
    values.put(title, model.getTitle());
    values.put(date, model.getDate());
    values.put(name, model.getName());
    values.put(description, model.getDescription());
    values.put(destination, model.getDestination());
    values.put(risk, model.getRisk());
    values.put(imageLink, model.getImageLink());
    values.put(expenseType, model.getExpenseType());
    values.put(expenseAmount, model.getExpenseAmount());
    values.put(expenseTime, model.getExpenseTime());
    values.put(expenseComments, model.getExpenseComments());
    db.update(tripTable, values, id + " = ?",
            new String[]{valueOf(model.getId())});
}

    @SuppressLint("Range")
    public List<tripModel> search(String searchTerm) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + tripTable +
                " WHERE  "  +title + "  like '%" + searchTerm + "%'" +
                " ORDER BY  " + id+
                "Limit 100";

        Cursor cursor = db.rawQuery(selectQuery, null);

        List<tripModel> idtModelList = new ArrayList<>();
        ;
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);

            tripModel cropModel = new tripModel(
                    cursor.getString(cursor.getColumnIndex(title)),
                    cursor.getString(cursor.getColumnIndex(date)),
                    cursor.getString(cursor.getColumnIndex(name)),
                    cursor.getString(cursor.getColumnIndex(destination)),
                    cursor.getString(cursor.getColumnIndex(risk)),
                    cursor.getString(cursor.getColumnIndex(description)),
                    cursor.getString(cursor.getColumnIndex(imageLink)),
                    cursor.getString(cursor.getColumnIndex(expenseType)),
                    cursor.getString(cursor.getColumnIndex(expenseAmount)),
                    cursor.getString(cursor.getColumnIndex(expenseTime)),
                    cursor.getString(cursor.getColumnIndex(expenseComments)),
                    cursor.getString(cursor.getColumnIndex(id)));

            idtModelList.add(cropModel);
        }
        return idtModelList;
    }
}
