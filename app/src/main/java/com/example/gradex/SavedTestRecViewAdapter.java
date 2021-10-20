package com.example.gradex;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavedTestRecViewAdapter extends RecyclerView.Adapter<SavedTestRecViewAdapter.ViewHolder> {
    private static final String TAG = "SavedTestRVA";
    
    private ArrayList<Test> savedTests = new ArrayList<>();
    private Context mContext;

    public SavedTestRecViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_saved_test, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");

        //holder.testIcon.setBackground();
        holder.textTestName.setText(savedTests.get(position).getTestName()); //set Test name
        holder.textClassName.setText(savedTests.get(position).getTestClass()); //set Class
        holder.testIcon.setBackground(Drawable.createFromPath(savedTests.get(position).getTestIconURL()));

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, savedTests.get(position).getTestName() + " selected", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return savedTests.size();
    }

    public void setSavedTests(ArrayList<Test> savedTests) {
        this.savedTests = savedTests;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView testIcon, saveIcon;
        private TextView textTestName, textClassName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            parent = itemView.findViewById(R.id.parent);
            testIcon = itemView.findViewById(R.id.testIcon);
            saveIcon = itemView.findViewById(R.id.saveIcon);
            textTestName = itemView.findViewById(R.id.textTestName);
            textClassName = itemView.findViewById(R.id.textClassName);

        }
    }
}
