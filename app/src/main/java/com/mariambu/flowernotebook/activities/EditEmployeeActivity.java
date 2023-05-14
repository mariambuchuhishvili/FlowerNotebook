package com.mariambu.flowernotebook.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mariambu.flowernotebook.DB.DatabaseHelper;
import com.mariambu.flowernotebook.R;

public class EditEmployeeActivity extends AppCompatActivity {
    EditText fio_input,profession_input,bday_input,address_input, contacts_input;
    Button save_btn, delete_btn;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);
        fio_input = findViewById(R.id.fio);
        profession_input = findViewById(R.id.profession);
        bday_input = findViewById(R.id.profession);
        address_input = findViewById(R.id.address);
        contacts_input = findViewById(R.id.contacts);
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
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE_EMPLOYEE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            fio_input.setText(userCursor.getString(1));
            profession_input.setText(userCursor.getString(2));
            bday_input.setText(userCursor.getString(3));
            address_input.setText(userCursor.getString(4));
            contacts_input.setText(userCursor.getString(5));
            userCursor.close();
        } else {
            // скрываем кнопку удаления
            delete_btn.setVisibility(View.GONE);
        }
    }
    public void save(View view) {
        DatabaseHelper myDB = new DatabaseHelper(EditEmployeeActivity.this);
        myDB.addEmployee(fio_input.getText().toString().trim(),
                        profession_input.getText().toString().trim(),
                        bday_input.getText().toString().trim(),
                        address_input.getText().toString().trim(),
                        contacts_input.getText().toString().trim());
        goHome();
    }
    public void delete(View view) {
        DatabaseHelper myDB = new DatabaseHelper(EditEmployeeActivity.this);
        myDB.deleteEmployee(userId);
        goHome();
    }
    private void goHome(){
        // закрываем подключение
        db.close();
        // переход к главной activity
        Intent intent = new Intent(this, EmployeeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
}