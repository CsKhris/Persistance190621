package com.cs.persistance190621;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Anonymous나 Lambda에서 사용할 수 있도록 final로 변수 생성
        final EditText display = (EditText)findViewById(R.id.display);

        // File을 생성하여 Data를 저장하기 위한 Button의 Click Event 처리
        final Button save = (Button)findViewById(R.id.save);
        save.setOnClickListener((view)->{
            // try ~ resource 구문
            // ()안에서 만든 객체는 close()를 하지 않아도 됩니다.
            try(FileOutputStream fos = openFileOutput("test.txt", Context.MODE_PRIVATE)){
                fos.write("Android File Output".getBytes());
                fos.flush();
                display.setText("SAVED.");
            }catch (Exception e){
                Log.e("Fail", e.getMessage());
            }
        });

        // Load
        Button load = (Button)findViewById(R.id.load);
        load.setOnClickListener((view)->{
            try(FileInputStream fis = openFileInput("test.txt")){

            // File의 내용을 읽을 저장 공간 만들기
            byte [] buf = new byte[fis.available()];

            // File의 내용읽기
                fis.read(buf);
                display.setText(new String(buf));
            }catch (Exception e){
                Log.e("Fail.", e.getMessage());
            }
        });

        // Delete
        final Button delete = (Button)findViewById(R.id.delete);
        delete.setOnClickListener((view)->{
            boolean result = deleteFile("test.txt");
            if(result){
                display.setText("Deleted.");
            }else {
                display.setText("Fail.");
            }
        });

        // Resread
        final Button resread = (Button)findViewById(R.id.resread);
        resread.setOnClickListener((view)-> {
            try(InputStream fis = getResources().openRawResource(R.raw.data)) {
                byte[] buf = new byte[fis.available()];
                fis.read(buf);
                display.setText(new String(buf));
            } catch (Exception e) {
                Log.e("Resource File Read Error", e.getMessage());
            }
        });
    }
}
