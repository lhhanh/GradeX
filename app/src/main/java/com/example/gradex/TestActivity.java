package com.example.gradex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    private ImageView backIcon, imgTestIcon;
    private TextView textTestName, textClassName, textDescription, textQuesNumber;
    private ImageView buttonCapture, buttonChangeAnswer;

    private RecyclerView ineditableAnswerRecView;
    private IneditableAnswerRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        overridePendingTransition(R.anim.horizontal_slide_in,0);
        initView();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textTestName.setText(""); //Set test name
        textClassName.setText(""); //Set test class
        textDescription.setText(""); //Set description
        textQuesNumber.setText("Số câu: " + ""); //Set number of questions



        adapter = new IneditableAnswerRecViewAdapter(this);
        ineditableAnswerRecView = findViewById(R.id.ineditableAnswerRecView);
        ineditableAnswerRecView.setAdapter(adapter);
        ineditableAnswerRecView.setLayoutManager(new GridLayoutManager(this, 1));

        ArrayList<Integer> answers = new ArrayList<>();
        answers.add(1);
        adapter.setAnswers(answers);

        buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "Heading to camera", Toast.LENGTH_SHORT).show();
            }
        });

        buttonChangeAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestActivity.this, "Heading to add new activity", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initView() {
        backIcon = findViewById(R.id.backIcon);
        imgTestIcon = findViewById(R.id.imgTestIcon);
        textTestName = findViewById(R.id.textTestName);
        textClassName = findViewById(R.id.textClassName);
        textDescription = findViewById(R.id.textDescription);
        textQuesNumber = findViewById(R.id.textQuesNumber);
        buttonCapture = findViewById(R.id.buttonCapture);
        buttonChangeAnswer = findViewById(R.id.buttonChangeAnswer);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.horizontal_slide_out);
    }
}