package com.mariambu.flowernotebook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.mariambu.flowernotebook.DB.DatabaseHelper;
import com.mariambu.flowernotebook.R;

public class EditSalesActivity extends AppCompatActivity {
    EditText name_input, price_input, count_input, sum_input;
    Button save_btn, delete_btn;


    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sales);
        name_input = findViewById(R.id.name);
        price_input = findViewById(R.id.price);
        count_input = findViewById(R.id.count_sales);
        sum_input = findViewById(R.id.sum);

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
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_SALES + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            name_input.setText(userCursor.getString(1));
            price_input.setText(String.valueOf(userCursor.getInt(2)));
            count_input.setText(String.valueOf(userCursor.getInt(3)));
            sum_input.setText(String.valueOf(userCursor.getInt(4)));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delete_btn.setVisibility(View.GONE);
        }
    }
    public void save(View view) {
        DatabaseHelper myDB = new DatabaseHelper(EditSalesActivity.this);
        myDB.addSales(name_input.getText().toString().trim(),
                Integer.valueOf(price_input.getText().toString().trim()),
                Integer.valueOf(count_input.getText().toString().trim()),
                Integer.valueOf(sum_input.getText().toString().trim()));
        goHome();
    }
    public void delete(View view) {
        DatabaseHelper myDB = new DatabaseHelper(EditSalesActivity.this);
        myDB.deleteSales(userId);
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, SalesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}