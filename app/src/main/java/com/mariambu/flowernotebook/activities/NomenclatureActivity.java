package com.mariambu.flowernotebook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.mariambu.flowernotebook.DB.DatabaseHelper;
import com.mariambu.flowernotebook.R;

public class NomenclatureActivity extends AppCompatActivity {
    ListView userList;
    Spinner filter_spinner;
    String[] filters ={"Без фильтров","Категория 'Игрушки'","Категория 'Открытки'","Категория 'Цветы'","Категория 'Семена'", "Категория 'Горшочные цветы'", "Категория 'Декорация'"};

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nomenclature);
        userList = findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditNomenclatureActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
        filter_spinner = findViewById(R.id.filters);
    }
    @Override
    public void onResume() {
        super.onResume();
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, filters);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter_spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String item = (String)parent.getItemAtPosition(position);
                String item = filter_spinner.getSelectedItem().toString();
                switch (item) {
                    case ("Категория 'Игрушки'"):
                        db = databaseHelper.getReadableDatabase();
                        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE +
                                " where " +DatabaseHelper.COLUMN_CATEGORY+ "='Игрушки'", null);

                        FilterForNomenclature(userCursor);
                        break;
                    case ("Категория 'Открытки'"):
                        db = databaseHelper.getReadableDatabase();
                        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE + " where " +DatabaseHelper.COLUMN_CATEGORY+ "='Открытки'", null);

                        FilterForNomenclature(userCursor);
                        break;
                    case ("Категория 'Цветы'"):
                        db = databaseHelper.getReadableDatabase();
                        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE + " where " +DatabaseHelper.COLUMN_CATEGORY+ "='Цветы'", null);

                        FilterForNomenclature(userCursor);
                        break;
                    case ("Без фильтров"):
                        db = databaseHelper.getReadableDatabase();
                        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE, null);

                        FilterForNomenclature(userCursor);
                        break;
                    case ("Категория 'Семена'"):
                        db = databaseHelper.getReadableDatabase();
                        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE + " where " +DatabaseHelper.COLUMN_CATEGORY+ "='Семена'", null);

                        FilterForNomenclature(userCursor);
                        break;
                    case ("Категория 'Горшочные цветы'"):
                        db = databaseHelper.getReadableDatabase();
                        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE + " where " +DatabaseHelper.COLUMN_CATEGORY+ "='Горшочные цветы'", null);

                        FilterForNomenclature(userCursor);
                        break;
                    case ("Категория 'Декорация'"):
                        db = databaseHelper.getReadableDatabase();
                        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE + " where " +DatabaseHelper.COLUMN_CATEGORY+ "='Декорация'", null);

                        FilterForNomenclature(userCursor);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        };
        filter_spinner.setOnItemSelectedListener(onItemSelectedListener);

    }
    public void FilterForNomenclature(Cursor userCursor){
        db = databaseHelper.getReadableDatabase();
        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_CATEGORY,
                DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_COUNT};
        userAdapter = new SimpleCursorAdapter(NomenclatureActivity.this,R.layout.item,userCursor,headers,
                new int[]{R.id.name, R.id.category, R.id.price, R.id.count}, 0);
        userList.setAdapter(userAdapter);
    }
    public void openEditNomenclatureActivity(View view){
        Intent intent = new Intent(this,EditNomenclatureActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        db.close();
        userCursor.close();
    }
}