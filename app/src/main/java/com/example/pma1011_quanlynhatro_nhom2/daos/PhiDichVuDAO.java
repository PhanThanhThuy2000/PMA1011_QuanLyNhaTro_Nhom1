package com.example.pma1011_quanlynhatro_nhom2.daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.pma1011_quanlynhatro_nhom2.database.DBhelper;
import com.example.pma1011_quanlynhatro_nhom2.models.PhiDichVu;

import java.util.ArrayList;



public class PhiDichVuDAO {
    private final DBhelper dBhelper;

    public PhiDichVuDAO(Context context){
        dBhelper = new DBhelper(context);
    }

    public long createPhiDichVu(PhiDichVu phiDichVu) {
        SQLiteDatabase db = dBhelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("DonGiaDien", phiDichVu.getDonGiaDien());
        values.put("DonGiaNuoc", phiDichVu.getDonGiaNuoc());
        values.put("VeSinh", phiDichVu.getVeSinh());
        values.put("GuiXe", phiDichVu.getGuiXe());
        values.put("Internet", phiDichVu.getInternet());
        values.put("ThangMay", phiDichVu.getThangMay());

        long result = db.insert("PhiDichVu", null, values);
        db.close();

        return result;
    }

    public PhiDichVu getPhiDichVuByID(int phiDichVuID) {
        ArrayList<PhiDichVu> list = new ArrayList<>();
        SQLiteDatabase db = dBhelper.getReadableDatabase();
        String query = "SELECT * FROM PhiDichVu WHERE PhiDichVu_ID = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(phiDichVuID)});

        if(cursor!=null){
            while (cursor.moveToNext()){
                int phiDichVuId = cursor.getInt(cursor.getColumnIndexOrThrow("PhiDichVu_ID"));
                int donGiaDien = cursor.getInt(cursor.getColumnIndexOrThrow("DonGiaDien"));
                int donGiaNuoc = cursor.getInt(cursor.getColumnIndexOrThrow("DonGiaNuoc"));
                int veSinh = cursor.getInt(cursor.getColumnIndexOrThrow("VeSinh"));
                int guiXe = cursor.getInt(cursor.getColumnIndexOrThrow("GuiXe"));
                int internet = cursor.getInt(cursor.getColumnIndexOrThrow("Internet"));
                int thangMay = cursor.getInt(cursor.getColumnIndexOrThrow("ThangMay"));

                PhiDichVu phiDichVu = new PhiDichVu(phiDichVuId, donGiaDien, donGiaNuoc, veSinh, guiXe, internet, thangMay);
                list.add(phiDichVu);
            }
            cursor.close();
        }
        db.close();

        return list.get(0);
    }
}
