package com.cs.persistance190621;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

class WordDBHelper extends SQLiteOpenHelper{
    //생성자에서 Database를 생성
    public WordDBHelper(Context context){
        super(context, "EndWord.db", null, 1);
    }

    //Database가 만들어 질 때 호출되는 Method
    @Override
    public void onCreate(SQLiteDatabase db){
        //Table 만들기
        db.execSQL("create table dic(" + "_id INTEGET PRIMARY KEY AUTOINCREMENT, " + "eng TEXT, han TEXT);");
    }

    //Database Version이 변경 될 때
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Table을 삭제하고 새로 생성
        db.execSQL("drop table if exists dic");
        onCreate(db);
    }
}

public class SQLiteActivity extends AppCompatActivity {

    //Database 변수
    WordDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);

        //Database 생성
        dbHelper = new WordDBHelper(SQLiteActivity.this);

    }
}