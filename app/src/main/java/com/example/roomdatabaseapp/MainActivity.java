package com.example.roomdatabaseapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //inisiasi variabel
    EditText editText;
    Button btAdd, btReset;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Aplikasi Note");

        //mencari variabel
        editText = findViewById(R.id.edittext);
        btAdd = findViewById(R.id.bAdd);
        btReset = findViewById(R.id.bReset);
        recyclerView = findViewById(R.id.recyler_view);

        //inisiasi database
        database = RoomDB.getInstance(this);
        //menyimpan nilai databse di data list
        dataList = database.mainDao().getAll();

        //inisiasi linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //inisiasi adapter
        adapter = new MainAdapter(MainActivity.this,dataList);
        //set adapter
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ambil string dari edit text
                String sText = editText.getText().toString().trim();
                //cek kondisi
                if (!sText.equals("")){
                    //jika teks tidak kosong
                    //inisiasi main data
                    MainData data = new MainData();
                    //set teks ke main data
                    data.setText(sText);
                    //memasukkan teks ke database
                    database.mainDao().insert(data);
                    //clear edit teks
                    editText.setText("");
                    //Notifikasi jika data dimasukkan
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hapus semua data
                database.mainDao().reset(dataList);
                //notifikasi jika semua data terhapus
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
            }
        });
    }
}