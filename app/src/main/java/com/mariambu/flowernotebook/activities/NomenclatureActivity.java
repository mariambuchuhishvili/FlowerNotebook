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
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.mariambu.flowernotebook.DB.DatabaseHelper;
import com.mariambu.flowernotebook.R;

public class NomenclatureActivity extends AppCompatActivity {
    ListView userList;
    Spinner filter_spinner;
    String[] filters ={"Без фильтров","Категория 'игрушки'","Категория 'открытки'","Категория 'цветы'"};

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
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, filters);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        filter_spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                switch (item) {
                    case ("Категория 'игрушки'"):
                        db = databaseHelper.getReadableDatabase();
                        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE +
                                " where " +DatabaseHelper.COLUMN_CATEGORY+ "='Игрушки'", null);
                        String[] headers1 = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_CATEGORY,
                                DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_COUNT};
                        userAdapter = new SimpleCursorAdapter(NomenclatureActivity.this,R.layout.item,userCursor,headers1,
                                new int[]{R.id.name, R.id.category, R.id.price, R.id.count}, 0);
                        userList.setAdapter(userAdapter);
                        break;
                    case ("Категория 'открытки'"):
                        db = databaseHelper.getReadableDatabase();
                        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE + " where " +DatabaseHelper.COLUMN_CATEGORY+ "='Открытки'", null);
                        String[] headers2 = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_CATEGORY,
                                DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_COUNT};
                        userAdapter = new SimpleCursorAdapter(NomenclatureActivity.this,R.layout.item,userCursor,headers2,
                                new int[]{R.id.name, R.id.category, R.id.price, R.id.count}, 0);
                        userList.setAdapter(userAdapter);
                        break;
                    case ("Категория 'цветы'"):
                        db = databaseHelper.getReadableDatabase();
                        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE + " where " +DatabaseHelper.COLUMN_CATEGORY+ "='Цветы'", null);
                        String[] headers3 = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_CATEGORY,
                                DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_COUNT};
                        userAdapter = new SimpleCursorAdapter(NomenclatureActivity.this,R.layout.item,userCursor,headers3,
                                new int[]{R.id.name, R.id.category, R.id.price, R.id.count}, 0);
                        userList.setAdapter(userAdapter);
                        break;
                    default:
                        db = databaseHelper.getReadableDatabase();
                        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE, null);
                        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_CATEGORY,
                                DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_COUNT};
                        userAdapter = new SimpleCursorAdapter(NomenclatureActivity.this,R.layout.item,userCursor,headers,
                                new int[]{R.id.name, R.id.category, R.id.price, R.id.count}, 0);
                        userList.setAdapter(userAdapter);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
        filter_spinner.setOnItemSelectedListener(onItemSelectedListener);
    }
    /*@Override
    public void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_NOMENCLATURE, null);
        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_CATEGORY,
                DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_COUNT};
        userAdapter = new SimpleCursorAdapter(this,R.layout.item,userCursor,headers,
                new int[]{R.id.name, R.id.category, R.id.price, R.id.count}, 0);

        userList.setAdapter(userAdapter);
    }*/
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