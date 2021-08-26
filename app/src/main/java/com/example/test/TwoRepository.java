package com.example.test;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TwoRepository implements TwoContract.Repository {

    List<CatData> catData = new ArrayList<>();

    @Override
    public List<CatData> loadData(Context context) {
        DBHelper dbHelper1 = new DBHelper(context);
        SQLiteDatabase db1 = dbHelper1.getWritableDatabase();

        Cursor c = db1.query("KITTYY", null, null, null, null, null, null);

        c.moveToFirst();

        for (int i = 0; i < c.getCount(); i++) {
            CatData catData1 = new CatData();
            int nameColInformation = c.getColumnIndex("information_cat");
            int nameColUrl = c.getColumnIndex("image_url");

            catData1.setInformation(c.getString(nameColInformation));
            catData1.setImageUrl(c.getString(nameColUrl));
            catData1.setDownloadState(true);
            catData.add(catData1);
            c.moveToNext();
        }

        c.close();
        db1.close();
        dbHelper1.close();

        return catData;
    }

    static class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context) {
            super(context, "kittyy", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table KITTYY (" + "information_cat text," + "image_url text" + ");");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }
}
