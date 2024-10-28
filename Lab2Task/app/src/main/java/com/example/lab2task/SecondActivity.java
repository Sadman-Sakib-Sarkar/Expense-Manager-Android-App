package com.example.lab2task;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity {
    public class Phones{
        List<String>getInfo(String group){
            List<String>info= new ArrayList<>();
            if (group.equals("Samsung")) {
                info.add("\nSamsung Galaxy S21");
                info.add("\nSamsung Galaxy S21 Plus");
                info.add("\nSamsung Galaxy S21 Ultra");
                info.add("\nSamsung Galaxy S24");
                info.add("\nSamsung Galaxy S24 Plus");
                info.add("\nSamsung Galaxy S24 Ultra");
            }

            if (group.equals("Apple")) {
                info.add("\nIphone 14");
                info.add("\nIphone 14 Pro");
                info.add("\nIphone 14 Pro Max");
                info.add("\nIphone 15");
                info.add("\nIphone 15 Pro");
                info.add("\nIphone 15 Pro Max");
            }

            if (group.equals("Xiaomi")) {
                info.add("\nRedmi 13");
                info.add("\nRedmi Note 13 Pro");
                info.add("\nRedmi Note 13 pro 5g");
                info.add("\nRedmi Note 13 pro plus 5g");
            }

            return info;
        }
    }
    private Phones expert= new Phones();

    public static final String EXTRA_MESSAGE="message";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);
        TextView info2 =(TextView)  findViewById(R.id.infoss);
        Intent intent = getIntent();
        String messageText = intent.getStringExtra(EXTRA_MESSAGE);
        TextView messageView = (TextView)findViewById(R.id.text);
        List<String> infoList =expert.getInfo(messageText);
        StringBuilder infoFormatted = new StringBuilder();
        for(String inf : infoList)
        {
            infoFormatted.append(inf).append('\n');
        }
        info2.setText("Phone Models");
        messageView .setText(infoFormatted);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}