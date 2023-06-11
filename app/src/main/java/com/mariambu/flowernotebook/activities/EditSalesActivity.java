package com.mariambu.flowernotebook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mariambu.flowernotebook.DB.DatabaseHelper;
import com.mariambu.flowernotebook.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditSalesActivity extends AppCompatActivity {
    EditText  price_input, count_input, sum_input, time_autho_input, comments;
    TextView countInNomenclature, countInNomenclatureDigit;

    Button save_btn, delete_btn, sum_btn;
    AutoCompleteTextView name_input, employee_auto;///////////autocomplete text = name/////////////

    Map<String,Integer> names = new HashMap<String,Integer>();
    Map<String,Integer> namesforcount = new HashMap<String,Integer>();
    ArrayList<String> namesArray = new ArrayList<String>();
    ArrayList<String> employeeArray = new ArrayList<String>();

    SimpleCursorAdapter adapter;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sales);
        name_input = findViewById(R.id.name);
        price_input = findViewById(R.id.price);
        employee_auto = findViewById(R.id.employeeAutoComplete);
        comments = findViewById(R.id.comments);
        countInNomenclature = findViewById(R.id.countInNomenclature);
        countInNomenclatureDigit = findViewById(R.id.countInNomenclatureDigit);

        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();
        db = sqlHelper.getReadableDatabase();


        // сделла выпадающий список из бд !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        Cursor cursorName = db.query("nomenclature",null, null,null,null,null,null);
        if (cursorName!=null && cursorName.moveToFirst()) {
            do {

                String name = cursorName.getString(cursorName.getColumnIndexOrThrow("name"));
                int price = cursorName.getInt(cursorName.getColumnIndexOrThrow("price"));
                int count = cursorName.getInt(cursorName.getColumnIndexOrThrow("count"));
                namesforcount.put(name,count);
                names.put(name,price);
                namesArray.add(name);
            }while (cursorName.moveToNext());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,namesArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        name_input.setAdapter(adapter);

        //сделла выпадающий список из бд !!!!!!!!!!!!!!!!!!!!!!!!!!!!!! РАБОТАЕТ!!!!!!!!!!!!!!!!!
        //ДОБАВИЛА АВТОЗАПОЛНЕНИЕ НАЗВАНИЯ И ЦЕНЫЫЫЫЫЫ! я молодец =)
        name_input.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(EditSalesActivity.this, "price"+names.get(name_input.getAdapter().getItem(position)), Toast.LENGTH_SHORT).show();
                String price = names.get(name_input.getAdapter().getItem(position)).toString();
                String count = namesforcount.get(name_input.getAdapter().getItem(position)).toString();
                price_input.setText(price);
                countInNomenclatureDigit.setText(count);
            }
        });
        //ДОБАВИЛА АВТОЗАПОЛНЕНИЕ НАЗВАНИЯ И ЦЕНЫЫЫЫЫЫ! я молодец =)
        //для авто заполнения сотрудника
        Cursor cursorEmployee = db.query("employee",null, null,null,null,null,null);
        if (cursorEmployee!=null && cursorEmployee.moveToFirst()) {
            do {
                String name = cursorEmployee.getString(cursorEmployee.getColumnIndexOrThrow("fioemployee"));
                employeeArray.add(name);
            }while (cursorEmployee.moveToNext());
        }
        ArrayAdapter<String> adapterEmployee = new ArrayAdapter(this, android.R.layout.simple_spinner_item,employeeArray);
        adapterEmployee.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employee_auto.setAdapter(adapterEmployee);




        count_input = findViewById(R.id.count_sales);
        sum_input = findViewById(R.id.sum);
        time_autho_input = findViewById(R.id.time);

        //time autho complite
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm, dd-MM-yy");
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
        time_autho_input.setText(date);

        save_btn = findViewById(R.id.saveButton);
        delete_btn = findViewById(R.id.deleteButton);
        sum_btn = findViewById(R.id.sumButton);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            userId = extras.getLong("id");
            //name = extras.getString("name");
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
            time_autho_input.setText(String.valueOf(userCursor.getString(5)));
            comments.setText(String.valueOf(userCursor.getString(6)));
            employee_auto.setText(String.valueOf(userCursor.getString(7)));


            Cursor cursorcount =  db.rawQuery("select * from " + DatabaseHelper.TABLE_NOMENCLATURE + " where " +
                    DatabaseHelper.COLUMN_NAME + "=?", new String[]{String.valueOf(userCursor.getString(1))});
            cursorcount.moveToFirst();
            countInNomenclatureDigit.setText(String.valueOf(cursorcount.getInt(4)));
            cursorcount.close();
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delete_btn.setVisibility(View.GONE);
        }

    }
    public  void UpdateCount(){//пытаюсь сделать убавления товара при продаже
        String name = name_input.getText().toString().trim();

        //userCursor = db.rawQuery("select "+DatabaseHelper.COLUMN_COUNT+" from " + DatabaseHelper.TABLE_SALES + " where " +
                //DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(IDofTovar)});
        //userCursor.moveToFirst();
        //tv_count.setText(String.valueOf(userCursor.getInt(1)));
        //userCursor.moveToFirst();
        //int count = Integer.parseInt(String.valueOf(userCursor.getInt(4)));
        //userCursor.close();
        //int countSales = Integer.parseInt(count_input.getText().toString());
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
        //ContentValues cv = new ContentValues();//
        //cv.put("count", Integer.parseInt(DatabaseHelper.COLUMN_COUNT) - Integer.parseInt(count_input.getText().toString()));//
        //db.update("nomenclature", cv, " name = ? ", new String[] {name_input.getText().toString().trim()});//
        //db.execSQL("UPDATE nomenclature SET count = " + count + " - "  + countSales + " WHERE name = " + name_input.getText().toString() +";");
    }
    public void save(View view) {
        DatabaseHelper myDB = new DatabaseHelper(EditSalesActivity.this);
       myDB.addSales(name_input.getText().toString().trim(),
                Integer.valueOf(price_input.getText().toString().trim()),
                Integer.valueOf(count_input.getText().toString().trim()),
                Integer.valueOf(sum_input.getText().toString().trim()),
                time_autho_input.getText().toString().trim(),
                comments.getText().toString().trim(),
                employee_auto.getText().toString().trim(), userId);
        //UpdateCount();
        String[] name = new String[] {name_input.getText().toString().trim()};
        int countNew = Integer.parseInt(countInNomenclatureDigit.getText().toString().trim()) - Integer.parseInt(count_input.getText().toString().trim());
        myDB.updateNomenclature(countNew, name );
        goHome();
    }
    public void delete(View view) {
        DatabaseHelper myDB = new DatabaseHelper(EditSalesActivity.this);
        myDB.deleteSales(userId);
        String[] name = new String[] {name_input.getText().toString().trim()};
        int countNew = Integer.parseInt(countInNomenclatureDigit.getText().toString().trim()) + Integer.parseInt(count_input.getText().toString().trim());
        myDB.updateNomenclature(countNew, name );
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