package com.mariambu.flowernotebook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.mariambu.flowernotebook.DB.DatabaseHelper;
import com.mariambu.flowernotebook.R;

public class SalesActivity extends AppCompatActivity {
    ListView userList;

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        userList = findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditSalesActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_SALES, null);
        String[] headers = new String[] {DatabaseHelper.COLUMN_NAME, DatabaseHelper.COLUMN_PRICE, DatabaseHelper.COLUMN_SALES_COUNT, DatabaseHelper.COLUMN_SUM, DatabaseHelper.COLUMN_TIME};
        userAdapter = new SimpleCursorAdapter(this,R.layout.item_sales,userCursor,headers, new int[]{R.id.Name, R.id.price, R.id.Count_sales, R.id.sum, R.id.time}, 0);

        userList.setAdapter(userAdapter);
    }

    public void openEditNomenclatureActivity(View view){
        Intent intent = new Intent(this,EditSalesActivity.class);
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