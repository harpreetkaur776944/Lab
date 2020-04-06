package com.example.lab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ViewCartSummaryAdapter extends RecyclerView.Adapter<ViewCartSummaryAdapter.ViewHolder> {

    Context context;
    List<Test> testList;


    public ViewCartSummaryAdapter(Context context, List<Test> testList) {
        this.context = context;
        this.testList = testList;
    }

    public ViewCartSummaryAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_summary,parent,false);
        ViewCartSummaryAdapter.ViewHolder viewHolder = new ViewCartSummaryAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Test test = testList.get(position);
        holder.name.setText(test.getName());
        holder.price.setText(test.getPrice());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,price;
        ImageView delete;
        public ViewHolder(@NonNull View view) {
            super(view);
            name = view.findViewById(R.id.textView21);
            price = view.findViewById(R.id.textView22);
            delete = view.findViewById(R.id.imageView5);
        }
    }
}
