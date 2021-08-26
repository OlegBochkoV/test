package com.example.test;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.Repository repository;
    private MainContract.View view;

    Context contextP;

    MainPresenter(MainContract.View view) {
        this.view = view;
        repository = new MainRepository();
    }

    @Override
    public void init(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("KITTYY",null,null);

        db.close();
        dbHelper.close();

        contextP = context;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<CatData> catData = null;
                catData = repository.loadData(contextP);
                view.showCats(catData);
            }
        });
        thread.start();

    }

    @Override
    public void update_recycler() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<CatData> catData = null;
                catData = repository.updateData();
                view.showCats(catData);
            }
        });
        thread.start();
    }

    @Override
    public void favouriteScreenButtonClick() {
        repository.saveStorage();
        view.openTwoScreen();
    }

    @Override
    public void tempfunc(CatData catData, int position){
        repository.editCatData(catData,position);
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