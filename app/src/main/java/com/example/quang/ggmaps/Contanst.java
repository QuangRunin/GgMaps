package com.example.quang.ggmaps;

public interface Contanst{
    String TABLE_MAPS="MAPS";
    String   COLUMN_KINHDO = "Kinhdo";
    String   COLUMN_ID= "ID";
    String   COLUMN_VIDO = "Vido";
    String CREATE_TABLE_MAPS =" CREATE TABLE "+ TABLE_MAPS + "(" +
            COLUMN_ID+"_id INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_KINHDO+" TEXT, " +
            COLUMN_VIDO+" TEXT "+
            ")";
}
