package com.android.splitpersonality.Profile_Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class profileDB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "profile_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "bluetooth";
    private static final String COL3 = "airplane";
    private static final String COL4 = "ID";
    public  profileDB(Context context){
        super(context, TABLE_NAME, null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
