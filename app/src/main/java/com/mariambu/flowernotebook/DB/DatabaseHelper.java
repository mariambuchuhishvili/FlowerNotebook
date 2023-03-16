package com.mariambu.flowernotebook.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FlowerDB.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NOMENCLATURE = "nomenclature";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_PRICE = "price";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NOMENCLATURE +" ( " + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME
                + " TEXT, "+ COLUMN_CATEGORY + " TEXT, "
                + COLUMN_PRICE + " INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOMENCLATURE);
        onCreate(db);
    }
    public void addNomenclature(String name, String category, int prices){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME,name);
        cv.put(COLUMN_CATEGORY,category);
        cv.put(COLUMN_PRICE,prices);
        db.insert(TABLE_NOMENCLATURE, null,cv);
    }
    ////////////////разобраться////////////////////////
    public void deleteNomenclature( long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOMENCLATURE,"_id = ?",new String[]{String.valueOf(userId)});
    }

}
