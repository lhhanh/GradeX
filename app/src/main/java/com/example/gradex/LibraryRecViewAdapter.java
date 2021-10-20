package com.example.gradex;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class LibraryRecViewAdapter extends RecyclerView.Adapter<LibraryRecViewAdapter.ViewHolder> {
    private static final String TAG = "LibraryRecViewAdapter";

    private ArrayList<Test> tests = new ArrayList<>();
    private Context mContext;

    public LibraryRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_test, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.testName.setText(tests.get(position).getTestName());
        holder.className.setText(tests.get(position).getTestClass());
        holder.testDate.setText("Đã tạo ngày " + tests.get(position).getTestAddDate());
        holder.testIcon.setBackground(Drawable.createFromPath(tests.get(position).getTestIconURL()));

        if (tests.get(position).isSaved()) {
            holder.saveIcon.setBackground(Drawable.createFromPath("drawable/ic_bookmarked.xml"));
        } else {
            holder.saveIcon.setBackground(Drawable.createFromPath("drawable/ic_save.xml"));
        }

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TestActivity.class);
                mContext.startActivity(intent);

            }
        });

        holder.buttonCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tests.size();
    }

    public void setTests(ArrayList<Test> tests) {
        this.tests = tests;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView testIcon, saveIcon;
        private TextView testName, className, testDate;
        private ImageView buttonCapture;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            testIcon = itemView.findViewById(R.id.testIcon);
            saveIcon = itemView.findViewById(R.id.saveIcon);
            testName = itemView.findViewById(R.id.testName);
            className = itemView.findViewById(R.id.className);
            testDate = itemView.findViewById(R.id.testDate);
            buttonCapture = itemView.findViewById(R.id.buttonCapture);
        }
    }
}
