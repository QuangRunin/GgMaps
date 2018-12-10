package com.example.quang.ggmaps;

public interface Contanst{
    String TABLE_MAPS="MAPS";
    String COLUMN_ID = "id";
    String   COLUMN_KINHDO = "Kinhdo";
    String   COLUMN_VIDO = "Vido";
    String CREATE_TABLE_MAPS =" CREATE TABLE "+ TABLE_MAPS + "(" +
            COLUMN_ID+" INTEGER PRIMAKY AUTO INCREMENT, "+
            COLUMN_KINHDO+" LONG NOT NULL, " +
            COLUMN_VIDO+" LONG NOT NULL "+
            ")";
}
