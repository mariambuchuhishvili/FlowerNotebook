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

public class ProviderActivity extends AppCompatActivity {

    ListView userList;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider);
        userList = findViewById(R.id.list);
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), EditProviderActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });

        databaseHelper = new DatabaseHelper(getApplicationContext());
    }
    @Override
    public void onResume(){
        super.onResume();
        db = databaseHelper.getReadableDatabase();
        userCursor = db.rawQuery("Select * from "+DatabaseHelper.TABLE_PROVIDER, null);
        String[] headers = new String[] {DatabaseHelper.COLUMN_PROVIDER_NAME, DatabaseHelper.COLUMN_PROVIDER_CONTACTS};
        userAdapter = new SimpleCursorAdapter(this,R.layout.item_provider,userCursor,headers, new int[]{R.id.fio,R.id.contact}, 0);
        userList.setAdapter(userAdapter);
    }
    public void openEditProviderActivity(View view){
        Intent intent = new Intent(this,EditProviderActivity.class);
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