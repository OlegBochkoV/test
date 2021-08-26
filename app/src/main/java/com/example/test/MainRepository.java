package com.example.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class MainRepository implements MainContract.Repository {

    private static int number = 0;
    private static List<CatData> listCatData = new ArrayList<>();
    private static catApi catapi;
    Context context;

    @Override
    public List<CatData> loadData(Context context) {
        this.context = context;
        Observable.range(1, 10)
                .flatMap(integer -> Observable.just(integer)
                        .subscribeOn(Schedulers.computation())
                        .map(MainRepository::tempload))
                .blockingLast();
//        for(int i=0;i<10;i++)
//            tempload(i);
        return listCatData;
    }

    private static int tempload(int value) {
        ++number;
        CatData catData = new CatData();
        catapi = catController.getApi();
        try {
            Response<List<catTempClass>> response = catapi.getData().execute();
            catData.setImageUrl(response.body().get(0).getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        catData.setInformation("kitty" + value);
        catData.setDownloadState(false);
        listCatData.add(catData);
        return 0;
    }

    @Override
    public List<CatData> updateData() {
        Observable.range(1, 5)
                .flatMap(integer -> Observable.just(integer)
                        .subscribeOn(Schedulers.computation())
                        .map(MainRepository::tempupdateData))
                .blockingLast();
//        for(int i=0;i<5;i++)
//            tempupdateData(i);
        return listCatData;
    }

    private static int tempupdateData(int value) {
        CatData catData = new CatData();
        catapi = catController.getApi();
        try {
            Response<List<catTempClass>> response = catapi.getData().execute();
            catData.setImageUrl(response.body().get(0).getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
        catData.setInformation("kitty" + ++number);
        catData.setDownloadState(false);
        listCatData.add(catData);
        Log.d("QWERTYASD",String.valueOf(number));
        return 0;
    }

    @Override
    public void editCatData(CatData catData, int position) {
        listCatData.get(position).setDownloadState(catData.getDownloadState());
    }

    @Override
    public void saveStorage() {
        DBHelper dbHelper1 = new DBHelper(context);
        SQLiteDatabase db1 = dbHelper1.getWritableDatabase();
        db1.delete("KITTYY", null, null);

        db1.close();
        dbHelper1.close();

        DBHelper dbHelper = new DBHelper(context);

        ContentValues contentValues = new ContentValues();

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        for (int i = 0; i < listCatData.size(); i++) {
            if (listCatData.get(i).getDownloadState()) {
                contentValues.put("information_cat", listCatData.get(i).getInformation());
                contentValues.put("image_url", listCatData.get(i).getImageUrl());
                db.insert("KITTYY", null, contentValues);
            }
        }
        db.close();
        contentValues.clear();
        dbHelper.close();
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