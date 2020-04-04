package com.example.lab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectTestCartAdapter extends RecyclerView.Adapter<SelectTestCartAdapter.ViewHolder>{

    Context context;
    List<Test> testList;

    public SelectTestCartAdapter(Context context, List<Test> testList) {
        this.context = context;
        this.testList = testList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_test_layout,parent,false);
        SelectTestCartAdapter.ViewHolder viewHolder = new SelectTestCartAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Test test = testList.get(position);
        holder.testName.setText(test.getName());
        holder.preTest.setText(test.getPreTestInformation());
        holder.report.setText(test.getReportAvailability());
        holder.category.setText(test.getCategory());
        holder.testUsage.setText(test.getTestUsuage());
        holder.price.setText(holder.price.getText()+" "+test.getPrice());
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.addedToCart.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public int getItemCount() {
        return testList.size();
    }


    public class ViewHolder extends  RecyclerView.ViewHolder{

        TextView preTest,report,testUsage,category,price,addedToCart,testName;
        ImageView addToCart;

        public ViewHolder(@NonNull View view) {
            super(view);
            testName = view.findViewById(R.id.textView5);
            preTest = view.findViewById(R.id.textView7);
            report = view.findViewById(R.id.textView9);
            testUsage = view.findViewById(R.id.textView11);
            category = view.findViewById(R.id.textView13);
            price = view.findViewById(R.id.textView15);
            addedToCart = view.findViewById(R.id.textView16);
            addToCart = view.findViewById(R.id.imageView4);
        }
    }
}
