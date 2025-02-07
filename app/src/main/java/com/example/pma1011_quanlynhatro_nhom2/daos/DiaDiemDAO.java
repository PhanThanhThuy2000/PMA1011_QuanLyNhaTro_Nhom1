package com.example.pma1011_quanlynhatro_nhom2.daos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pma1011_quanlynhatro_nhom2.database.DBhelper;
import com.example.pma1011_quanlynhatro_nhom2.models.DiaDiem;

import java.util.ArrayList;



public class DiaDiemDAO {

    private final DBhelper dBhelper;

    public DiaDiemDAO(Context context) {
        this.dBhelper = new DBhelper(context);
    }

    public ArrayList<DiaDiem> read(){
        ArrayList<DiaDiem> list = new ArrayList<>();
        SQLiteDatabase database = dBhelper.getReadableDatabase();

        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM DiaDiem", null);
            if(cursor.getColumnCount() > 0){
                //Nếu cursor lớn hơn 0 di chuyển con trỏ lên đầu
                cursor.moveToFirst();
                //Khởi tạo vòng lặp để lấy dữ liệu
                do {
                    list.add(new DiaDiem(
                            cursor.getInt(0),
                            cursor.getString(1)
                    ));
                } while (cursor.moveToNext());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
        return list;
    }

    public DiaDiem getDiaDiemByDiaDiemID(int diaDiemID){
        DiaDiem diaDiem = null;
        SQLiteDatabase database = dBhelper.getReadableDatabase();
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM DiaDiem WHERE DiaDiem_ID = ?", new String[]{String.valueOf(diaDiemID)});
            if(cursor.getColumnCount() > 0){
                cursor.moveToFirst();
                diaDiem = new DiaDiem(
                        cursor.getInt(0),
                        cursor.getString(1)
                );
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
        return diaDiem;
    }
}
