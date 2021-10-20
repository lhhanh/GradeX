package com.example.gradex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class AddNewTestActivity extends AppCompatActivity {

    private EditText editTextTestName, editTextClassName, editTextDescription, editTextNumberQuestion;
    private ImageView imgChooseIcon, backIcon, topDivider;
    private ImageView buttonOK, buttonSaveAnswer;
    private RelativeLayout textABCD;

    private RecyclerView editableAnswerRecView;
    private EditableAnswerRecViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_test);

        overridePendingTransition(R.anim.horizontal_slide_in,0);

        initView();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imgChooseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNewTestActivity.this, "Please choose an icon", Toast.LENGTH_SHORT).show();
            }
        });

        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTextTestName.getText().toString().equals("")) {
                    Toast.makeText(AddNewTestActivity.this, "Thầy cô đang bỏ trống tên bài thi", Toast.LENGTH_SHORT).show();
                } else if (editTextNumberQuestion.getText().toString().equals("")) {
                    Toast.makeText(AddNewTestActivity.this, "Thầy cô đang bỏ trống số câu hỏi", Toast.LENGTH_SHORT).show();
                } else {
                    String testName = editTextTestName.getText().toString();
                    String testClass = editTextClassName.getText().toString();
                    String testDescription = editTextDescription.getText().toString();
                    int numberQuestion = Integer.parseInt(editTextNumberQuestion.getText().toString());

                    textABCD.setVisibility(View.VISIBLE);
                    topDivider.setVisibility(View.VISIBLE);
                    editableAnswerRecView.setVisibility(View.VISIBLE);
                    buttonSaveAnswer.setVisibility(View.VISIBLE);
                }

            }
        });

        adapter = new EditableAnswerRecViewAdapter(this);
        editableAnswerRecView = findViewById(R.id.editableAnswerRecView);

        editableAnswerRecView.setAdapter(adapter);
        editableAnswerRecView.setLayoutManager(new GridLayoutManager(this, 1));

        ArrayList<Integer> answers = new ArrayList<>();
        answers.add(1);

        adapter.setAnswers(answers);

        buttonSaveAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddNewTestActivity.this, "New test saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void initView() {
        editTextClassName = findViewById(R.id.editTextClassName);
        editTextTestName = findViewById(R.id.editTextTestName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextNumberQuestion = findViewById(R.id.editTextNumberQuestion);

        backIcon = findViewById(R.id.backIcon);
        imgChooseIcon = findViewById(R.id.imgChooseIcon);
        topDivider = findViewById(R.id.topDivider);

        buttonOK = findViewById(R.id.buttonOK);
        buttonSaveAnswer = findViewById(R.id.buttonSaveAnswer);

        textABCD = findViewById(R.id.textABCD);

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.horizontal_slide_out);
    }
}