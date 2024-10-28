package com.example.studentid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView facebook = findViewById(R.id.facebook);
        facebook.setOnClickListener(v -> openLink("https://www.facebook.com/Sadman.Sakib.Sarkar"));

        ImageView instagram = findViewById(R.id.instagram);
        instagram.setOnClickListener(v -> openLink("https://www.instagram.com/_.s_a_d_m_a_n._/"));

        ImageView linkedin = findViewById(R.id.linkedin);
        linkedin.setOnClickListener(v -> openLink("https://www.linkedin.com/in/sadman-sakib-9785a92b7"));

        ImageView github = findViewById(R.id.github);
        github.setOnClickListener(v -> openLink("https://github.com/Sadman-Sakib-Sarkar"));

        ImageView mypic = findViewById(R.id.mypic);
        mypic.setOnClickListener(v -> openLink("https://sadmansakib.netlify.app/"));
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}