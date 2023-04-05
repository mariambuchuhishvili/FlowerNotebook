package com.mariambu.flowernotebook.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FlowerDB.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_NOMENCLATURE = "nomenclature"; //таблица номенклатуры
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name"; //имя товара
    public static final String COLUMN_CATEGORY = "category"; //категория товара
    public static final String COLUMN_PRICE = "price"; //цена товара
    public static final String COLUMN_COUNT = "count"; //количество товара

    public static final String TABLE_SALES = "sales"; //ТАЮЛИЦА ПРОДАЖИ
    public static final String COLUMN_SALES_COUNT = "sales_count"; //количество проданного товара
    public static final String COLUMN_SUM = "sum";//сумма в типа чеке

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NOMENCLATURE +" ( " + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
                + " TEXT, "+ COLUMN_CATEGORY + " TEXT, "
                + COLUMN_PRICE + " INTEGER, " + COLUMN_COUNT + " INTEGER);");
        db.execSQL("CREATE TABLE " + TABLE_SALES +" ( " + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
                + " TEXT, "+ COLUMN_PRICE + " INTEGER, " + COLUMN_SALES_COUNT + " INTEGER, " + COLUMN_SUM + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOMENCLATURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SALES);
        onCreate(db);
    }
    public void addNomenclature(String name, String category, int prices, int count){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_CATEGORY,category);
        cv.put(COLUMN_PRICE,prices);
        cv.put(COLUMN_COUNT,count);
        db.insert(TABLE_NOMENCLATURE, null,cv);
    }
    public void addSales(String name, int prices, int sales_count, int sum){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_PRICE,prices);
        cv.put(COLUMN_SALES_COUNT,sales_count);
        cv.put(COLUMN_SUM,sum);
        db.insert(TABLE_SALES, null,cv);
    }
    ////////////////разобраться////////////////////////
    public void deleteNomenclature( long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOMENCLATURE,"_id = ?",new String[]{String.valueOf(userId)});
    }
    public void deleteSales( long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SALES,"_id = ?",new String[]{String.valueOf(userId)});
    }

}
