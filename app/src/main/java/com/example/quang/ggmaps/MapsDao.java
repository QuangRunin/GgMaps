package com.example.quang.ggmaps;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MapsDao implements Contanst {
    private DatabaseManager database;

    public MapsDao(DatabaseManager database) {
        this.database = database;
    }
    public long insertMaps(Maps maps){
        SQLiteDatabase sqLiteDatabase  = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, maps.get_id());
        values.put(COLUMN_KINHDO,maps.getKinhdo());
        values.put(COLUMN_VIDO,maps.getVido());
        Long id = sqLiteDatabase.insert(TABLE_MAPS,null,values);
        Log.e("inserMaps","InsertMap"+maps);
        sqLiteDatabase.close();
        return id;
    }
    public List<Maps> getAllMasps() {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        List<Maps> maps = new ArrayList<>();
        String select = " SELECT * FROM " + TABLE_MAPS;
        Cursor cursor = sqLiteDatabase.rawQuery(select, null);
        if (cursor.moveToFirst()) {
            do {
                Maps maps1 = new Maps();
                maps1.setKinhdo(cursor.getLong(0));
                maps1.setVido(cursor.getLong(1));
                maps.add(maps1);
            } while (cursor.moveToNext());
        }
        return maps;
    }
    public int deleteMasp(Maps map) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        return sqLiteDatabase.delete(TABLE_MAPS, COLUMN_KINHDO + " =?", new String[]{String.valueOf(map.get_id())});
    }
    public Maps getMapsId(String idMaps) {
        Maps maps = null;
        SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(
                TABLE_MAPS,
                new String[]{COLUMN_ID,COLUMN_KINHDO, COLUMN_VIDO},
                COLUMN_KINHDO + " =?",
                new String[]{idMaps},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            String mapskinhdo = cursor.getString(cursor.getColumnIndex(COLUMN_KINHDO));
            String mapsvido = cursor.getString(cursor.getColumnIndex(COLUMN_VIDO));
            maps = new Maps();
            maps.setKinhdo(Long.getLong(mapskinhdo));
            maps.setVido(Long.getLong(mapsvido));


        }

        return maps;
    }
    public long updateMaps(Maps maps) {
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_KINHDO, maps.getKinhdo());
        values.put(COLUMN_VIDO, maps.getVido());
        return  sqLiteDatabase.update(TABLE_MAPS,
                values,
                COLUMN_KINHDO + "=?",
                new String[]{Long.toString(maps.getKinhdo())});
    }
    public Maps getViTriByMaps(String kd) {
        Maps maps = null;
        SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_MAPS,
                new String[]{ COLUMN_ID,COLUMN_KINHDO, COLUMN_VIDO},
                COLUMN_ID + "=?",
                new String[]{kd},
                null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            maps = new Maps();
            maps.set_id(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
            maps.setKinhdo(cursor.getLong(cursor.getColumnIndex(COLUMN_KINHDO)));
            maps.setVido(cursor.getLong(cursor.getColumnIndex(COLUMN_VIDO)));

        }
        return maps;


    }
}
