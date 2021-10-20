package com.example.gradex;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EditableAnswerRecViewAdapter extends RecyclerView.Adapter<EditableAnswerRecViewAdapter.ViewHolder> {
    private static final String TAG = "EditableAnswerRVAdapter";

    private ArrayList<Integer> answers = new ArrayList<>();
    private Context mContext;

    public EditableAnswerRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_editable_answer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Started");

        holder.quesNum.setText(""+(position+1));

        holder.answerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAns(holder);
                holder.answerA.setBackgroundColor(ContextCompat.getColor(mContext, R.color.Aeroblue));
                Toast.makeText(mContext, "answer A selected", Toast.LENGTH_SHORT).show();
            }
        });

        holder.answerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAns(holder);
                holder.answerB.setBackgroundColor(ContextCompat.getColor(mContext, R.color.Aeroblue));
                Toast.makeText(mContext, "answer B selected", Toast.LENGTH_SHORT).show();
            }
        });

        holder.answerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAns(holder);
                holder.answerC.setBackgroundColor(ContextCompat.getColor(mContext, R.color.Aeroblue));
                Toast.makeText(mContext, "answer C selected", Toast.LENGTH_SHORT).show();
            }
        });

        holder.answerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAns(holder);
                holder.answerD.setBackgroundColor(ContextCompat.getColor(mContext, R.color.Aeroblue));
                Toast.makeText(mContext, "answer D selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearAns(ViewHolder holder) {
        holder.answerA.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        holder.answerB.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        holder.answerC.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        holder.answerD.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
    }


    @Override
    public int getItemCount() {
        return answers.size();
    }

    public void setAnswers(ArrayList<Integer> answers) {
        this.answers = answers;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private TextView quesNum;
        private ImageView answerA, answerB, answerC, answerD;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            quesNum = itemView.findViewById(R.id.quesNum);

            answerA = itemView.findViewById(R.id.answerA);
            answerB = itemView.findViewById(R.id.answerB);
            answerC = itemView.findViewById(R.id.answerC);
            answerD = itemView.findViewById(R.id.answerD);

        }
    }
}
