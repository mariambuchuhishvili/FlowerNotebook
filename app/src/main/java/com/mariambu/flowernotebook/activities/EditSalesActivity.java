package com.mariambu.flowernotebook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.mariambu.flowernotebook.DB.DatabaseHelper;
import com.mariambu.flowernotebook.R;

public class EditSalesActivity extends AppCompatActivity {
    EditText  price_input, count_input, sum_input;
    Button save_btn, delete_btn, sum_btn;
    AutoCompleteTextView name_input;///////////autocomplete text = name/////////////
    //String[] names ={"ромашка","роза","гладиолус"};
    String[] names = {DatabaseHelper.COLUMN_NAME};
SimpleCursorAdapter adapter;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sales);
        name_input = findViewById(R.id.name);
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        /*не работает >=( // db = sqlHelper.getReadableDatabase();
        userCursor = db.query(DatabaseHelper.TABLE_NOMENCLATURE,new String[]{"_id", "name"}, null, null, null, null, null);
        //noinspection deprecation
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,userCursor, new String[] {"name"}, new int[] {android.R.id.text1});
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);*/
        name_input.setAdapter(adapter);
        price_input = findViewById(R.id.price);
        count_input = findViewById(R.id.count_sales);
        sum_input = findViewById(R.id.sum);

        save_btn = findViewById(R.id.saveButton);
        delete_btn = findViewById(R.id.deleteButton);
        sum_btn = findViewById(R.id.sumButton);

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

    public void sumFunction(View view) {
        int price = Integer.parseInt(String.valueOf(price_input.getText()));
        int count = Integer.parseInt(String.valueOf(count_input.getText()));
        int sum = price*count;
        sum_input.setText(Integer.toString(sum));
    }


}