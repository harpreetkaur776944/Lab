package com.example.lab;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestsAdapter extends RecyclerView.Adapter<TestsAdapter.ViewHolder>{

    Context context;
    List<Test> testList;

    public TestsAdapter(Context context, List<Test> testList) {
        this.context = context;
        this.testList = testList;
    }

    public TestsAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_test_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Test test = testList.get(position);
        holder.name.setText(test.getName());
        holder.details.setText(test.getDetails());
        holder.readmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent in = new Intent(v.getContext(),SelectTestCart.class);
                in.putExtra("testcode",test.getTestCode());
                v.getContext().startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{
        TextView name,details;
        Button readmore;

        public ViewHolder(@NonNull View view) {
            super(view);
                name = view.findViewById(R.id.textView3);
                details = view.findViewById(R.id.textView4);
                readmore = view.findViewById(R.id.button);
        }
    }
}
