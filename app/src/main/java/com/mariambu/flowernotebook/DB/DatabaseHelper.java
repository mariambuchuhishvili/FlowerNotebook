package com.mariambu.flowernotebook.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FlowerDB.db";
    private static final int DATABASE_VERSION = 8;

    //Номенклатура//
    public static final String TABLE_NOMENCLATURE = "nomenclature"; //таблица номенклатуры
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name"; //имя товара
    public static final String COLUMN_CATEGORY = "category"; //категория товара
    public static final String COLUMN_PRICE = "price"; //цена товара
    public static final String COLUMN_COUNT = "count"; //количество товара

    //Продажи//
    public static final String TABLE_SALES = "sales"; //таблица продажи
    public static final String COLUMN_SALES_COUNT = "sales_count"; //количество проданного товара
    public static final String COLUMN_SUM = "sum";//сумма в типа чеке
    public static final String COLUMN_TIME = "time";//время продажи
    public static final String COLUMN_COMMENTS = "comments";//примечания

    //Сотрудники//
    public static final String TABLE_EMPLOYEE = "employee"; //таблица сотрудники
    public static final String COLUMN_EMPLOYEE_NAME = "fioemployee"; //ФИО сотрудника
    public static final String COLUMN_EMPLOYEE_PROFESSION = "profession"; //должность сотрудника
    public static final String COLUMN_EMPLOYEE_BIRTHDAY = "bday_employee"; //др сотрудника
    public static final String COLUMN_EMPLOYEE_ADDRESS = "address_employee"; //адрес сотрудника
    public static final String COLUMN_EMPLOYEE_CONTACTS = "contacts_employee"; //контакты сотрудника (номер телефона)

    //Поставщики//
    public static final String TABLE_PROVIDER = "provider"; //таблица поставщики
    public static final String COLUMN_PROVIDER_NAME = "fioprovider"; //ФИО поставщика
    public static final String COLUMN_PROVIDER_CONTACTS = "provider_contacts"; //контакты поставщика (номер телефона)
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //таблица номенклатуры//
        db.execSQL("CREATE TABLE " + TABLE_NOMENCLATURE +" ( " + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
                + " TEXT, "+ COLUMN_CATEGORY + " TEXT, "
                + COLUMN_PRICE + " INTEGER, " + COLUMN_COUNT + " INTEGER, " + COLUMN_PROVIDER_NAME+" TEXT);");
       //таблица продажи//
        db.execSQL("CREATE TABLE " + TABLE_SALES +" ( " + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
                + " TEXT, "+ COLUMN_PRICE + " INTEGER, " + COLUMN_SALES_COUNT + " INTEGER, " + COLUMN_SUM + " INTEGER, "
                + COLUMN_TIME+" TEXT, " +COLUMN_COMMENTS+" TEXT, "+COLUMN_EMPLOYEE_NAME+" TEXT);");
        //таблица сотрудники//
        db.execSQL("CREATE TABLE " +TABLE_EMPLOYEE+ " ( " + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EMPLOYEE_NAME
                + " TEXT, " +COLUMN_EMPLOYEE_PROFESSION+ " TEXT, "
                + COLUMN_EMPLOYEE_BIRTHDAY + " TEXT, " + COLUMN_EMPLOYEE_ADDRESS
                + " TEXT, " + COLUMN_EMPLOYEE_CONTACTS+ " TEXT);");
        //таблица поставщики//
        db.execSQL("CREATE TABLE " +TABLE_PROVIDER+ " ( " + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PROVIDER_NAME
                + " TEXT, "  + COLUMN_PROVIDER_CONTACTS+ " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOMENCLATURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROVIDER);
        onCreate(db);
    }
    //добавление/сохранение в таблице номенклатура
    public void addNomenclature(String name, String category, int prices, int count, String providerName, long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_CATEGORY,category);
        cv.put(COLUMN_PRICE,prices);
        cv.put(COLUMN_COUNT,count);
        cv.put(COLUMN_PROVIDER_NAME, providerName);
        if (userId>0) {
            db.update(TABLE_NOMENCLATURE, cv, COLUMN_ID + "=" + userId,  null);
        } else {db.insert(TABLE_NOMENCLATURE, null,cv);}

    }
    //добавление/сохранение в таблице продажи
    public void addSales(String name, int prices, int sales_count, int sum, String time, String comments, String employee, long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_PRICE,prices);
        cv.put(COLUMN_SALES_COUNT,sales_count);
        cv.put(COLUMN_SUM,sum);
        cv.put(COLUMN_TIME,time);
        cv.put(COLUMN_COMMENTS, comments);
        cv.put(COLUMN_EMPLOYEE_NAME, employee);
        if (userId>0) {db.update(TABLE_SALES, cv, COLUMN_ID + "=" + userId,  null);}
        else {db.insert(TABLE_SALES, null,cv);}

    }
    //добавление/сохранение в таблице сотрудники
    public void addEmployee(String fio_employee, String profession, String bday_employee, String address_employee, String contacts_employee, long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_EMPLOYEE_NAME,fio_employee);
        cv.put(COLUMN_EMPLOYEE_PROFESSION,profession);
        cv.put(COLUMN_EMPLOYEE_BIRTHDAY,bday_employee);
        cv.put(COLUMN_EMPLOYEE_ADDRESS,address_employee);
        cv.put(COLUMN_EMPLOYEE_CONTACTS,contacts_employee);
        if (userId>0) {db.update(TABLE_EMPLOYEE, cv, COLUMN_ID + "="+userId, null);}
        else {db.insert(TABLE_EMPLOYEE, null,cv);}

    }
    //добавление/сохранение в таблице поставщики
    public void addProvider(String fio_provider, String provider_contacts, long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PROVIDER_NAME,fio_provider);
        cv.put(COLUMN_PROVIDER_CONTACTS,provider_contacts);
        if (userId>0) {db.update(TABLE_PROVIDER, cv, COLUMN_ID + "="+userId, null);}
        else {db.insert(TABLE_PROVIDER, null,cv);}

    }

    //удаление из таблицы номенклатура
    public void deleteNomenclature( long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOMENCLATURE,"_id = ?",new String[]{String.valueOf(userId)});
    }
    //удаление из таблицы продажи
    public void deleteSales( long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SALES,"_id = ?",new String[]{String.valueOf(userId)});
    }
    //удаление из таблицы сотрудники
    public void deleteEmployee( long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EMPLOYEE,"_id = ?",new String[]{String.valueOf(userId)});
    }
    //удаление из таблицы поставщики
    public void deleteProvider( long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROVIDER,"_id = ?",new String[]{String.valueOf(userId)});
    }
    public void updateNomenclature( int countNew, String[] name ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_COUNT, countNew);
        db.update("nomenclature", cv, COLUMN_NAME + "= ?" , name);
    }
}
