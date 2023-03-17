package com.mariambu.flowernotebook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mariambu.flowernotebook.DB.DatabaseHelper;
import com.mariambu.flowernotebook.R;

public class EditNomenclatureActivity extends AppCompatActivity {

    EditText name_input, category_input,price_input, count_input;
    Button save_btn, delete_btn;

    Spinner category_spinner;
    String[] categories ={"Цветы","Открытки","Игрушки"};

    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nomenclature);

        name_input = findViewById(R.id.name);
        category_input = findViewById(R.id.category);
        price_input = findViewById(R.id.price);
        count_input = findViewById(R.id.count);

        category_spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category_spinner.setAdapter(adapter);
        AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)parent.getItemAtPosition(position);
                category_input.setText(item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        category_spinner.setOnItemSelectedListener(onItemSelectedListener);

        save_btn = findViewById(R.id.saveButton);
        delete_btn = findViewById(R.id.deleteButton);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
        }
        if (userId > 0) {
            // получаем элемент по id из бд
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_NOMENCLATURE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            name_input.setText(userCursor.getString(1));
            category_input.setText(userCursor.getString(2)); //??????????
            //category_spinner.setSelection((int) userId);
            price_input.setText(String.valueOf(userCursor.getInt(3)));
            count_input.setText(String.valueOf(userCursor.getInt(4)));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delete_btn.setVisibility(View.GONE);
        }

    }
    public void save(View view) {
        DatabaseHelper myDB = new DatabaseHelper(EditNomenclatureActivity.this);
        myDB.addNomenclature(name_input.getText().toString().trim(),
                             category_input.getText().toString().trim(),
                             Integer.valueOf(price_input.getText().toString().trim()),
                             Integer.valueOf(count_input.getText().toString().trim()));
        goHome();
    }
    public void delete(View view) {
        DatabaseHelper myDB = new DatabaseHelper(EditNomenclatureActivity.this);
        myDB.deleteNomenclature(userId);
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, NomenclatureActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}