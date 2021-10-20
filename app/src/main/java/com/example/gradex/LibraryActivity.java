package com.example.gradex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {

    private RecyclerView libraryRecView;
    private LibraryRecViewAdapter adapter;
    private ImageView exitIcon, newTestIcon;
    private ProgressBar progressHorizontal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);

        overridePendingTransition(R.anim.vertical_slide_in, 0);

        //Progress Bar
        progressHorizontal = findViewById(R.id.progress_horizontal);

        //Recycler View
        adapter = new LibraryRecViewAdapter(this);
        libraryRecView = findViewById(R.id.libraryRecView);

        libraryRecView.setAdapter(adapter);
        libraryRecView.setLayoutManager(new GridLayoutManager(this, 1));

        ArrayList<Test> tests = new ArrayList<>();
        tests.add(new Test("Thi học kỳ Lý", "Lớp 16T1", "19/10/2021","drawable/ic_telescope.xml"));

        adapter.setTests(tests);

        //Toolbar icons
        exitIcon = findViewById(R.id.exitIcon);
        newTestIcon = findViewById(R.id.newTestIcon);

        exitIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        newTestIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LibraryActivity.this, AddNewTestActivity.class);
                startActivity(intent);

            }
        });

    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.vertical_slide_out);
    }
}