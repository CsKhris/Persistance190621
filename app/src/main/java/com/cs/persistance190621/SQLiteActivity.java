package com.cs.persistance190621;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

//SQLite를 상속받기 뒤한 Class 생성
class WordDBHelper extends SQLiteOpenHelper{
    //생성자에서 Database를 생성
    public WordDBHelper(Context context){
        //Database File 생성
        //EndWord.db 를 File이름으로 하고 Version은 1
        //factory는 표준 Cursor 이용
        super(context, "EndWord.db", null, 1);
    }

    //Database가 만들어 질 때 호출되는 Method
    @Override
    public void onCreate(SQLiteDatabase db){
        //Table 만들기
        db.execSQL("create table dic(" + "_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "eng TEXT, han TEXT);");
    }

    //Database Version이 변경 될 때 호출되는 Method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Table을 삭제하고 새로 생성
        //기존 Data가 존재하고 Data를 보존할 필요성이 있을 경우
        //Data를 옮기고 Table을 다시 생성
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

        //Button 찾아오기
        Button create = (Button)findViewById(R.id.createsql);
        //저장 Button을 Click 했을 때 처리
        create.setOnClickListener((view -> {
            //Database 가져오기
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //삽입할 Data 생성
            ContentValues row = new ContentValues();
            row.put("han", "남자");
            row.put("eng", "Man");
            //Data 삽입
            db.insert("dic", null, row);

            //SQL을 이용하여 삽입
            db.execSQL("insert into dic values(null, 'Woman', '여자');");
            //Log 출력
            Log.e("Data Insert", "Success.");

            //Database 닫기
            dbHelper.close();
        }));

        //읽기 Button 찾아오기
        Button read = (Button)findViewById(R.id.readsql);
        //읽기 Button을 눌렀을 때 수행할 내용
        read.setOnClickListener((view -> {
            //Edit 찾아오기
            EditText display = (EditText)findViewById(R.id.displaysql);
            //dic Table의 모든 Data 찾아오기
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("select eng, han from dic", null);

            //모든 Data 읽기
            String r = "";
            while(cursor.moveToNext()){
                String eng = cursor.getString(0);
                String han = cursor.getString(1);
                r += eng + ":" + han + "\n";
            }
            display.setText(r);
            cursor.close();
            dbHelper.close();
        }));

        //수정 Button 찾아오기
        Button update = (Button)findViewById(R.id.updatesql);
        //수정 Button을 Click 했을 때 수행할 내용
        update.setOnClickListener((view -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //수정할 내용 작성 - set 절의 내용('조건'은 적지 않습니다.)
            ContentValues row = new ContentValues();
            row.put("han", "남성");
            //수정
            db.update(
                    "dic", row,
                    "eng='man'",
                    null);
            db.close();
        }));

        Button delete = (Button)findViewById(R.id.deletesql);
        delete.setOnClickListener((view -> {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete("dic", "eng='man'", null);
            db.close();
        }));
    }
}