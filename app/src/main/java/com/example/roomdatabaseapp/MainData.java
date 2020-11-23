package com.example.roomdatabaseapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Nama tabel
@Entity(tableName = "table_name")
public class MainData implements Serializable {
    //membuat kolom id
    @PrimaryKey(autoGenerate = true)
    private int ID;

    //membuat kolom teks
    @ColumnInfo(name = "text")
    private String text;

    //Generate getter dan setter

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
