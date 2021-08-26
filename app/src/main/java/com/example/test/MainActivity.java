package com.example.test;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    private MainContract.Presenter presenter;
    RecyclerView recyclerView;
    MyAdapter myAdapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                android.Manifest.permission.READ_CONTACTS,
                android.Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_SMS,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.MANAGE_EXTERNAL_STORAGE
        };
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);

        presenter = new MainPresenter(this);
        recyclerView = (RecyclerView) findViewById(R.id.list_id);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_id);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.update_recycler();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        presenter.init(getApplicationContext());
    }

    @Override
    public void showCats(List<CatData> catData) {
        myAdapter = new MyAdapter(this, catData, presenter);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                recyclerView.setAdapter(myAdapter);
            }
        });
    }

    @Override
    public void openTwoScreen(){
        Intent intent = new Intent(MainActivity.this, TwoActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_favourite:
                presenter.favouriteScreenButtonClick();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}