package com.example.lab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder> {

    Context context;
    List<OrderHistoryDetails> orderHistoryDetailsList;

    public OrderHistoryAdapter(Context context, List<OrderHistoryDetails> orderHistoryDetailsList) {
        this.context = context;
        this.orderHistoryDetailsList = orderHistoryDetailsList;
    }
    public OrderHistoryAdapter()
    {

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historylayout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderHistoryDetails orderItemsDetails = orderHistoryDetailsList.get(position);
        holder.orderId.setText(orderItemsDetails.getOrderId());
        holder.date.setText(orderItemsDetails.getDate());
        holder.timeSlot.setText(orderItemsDetails.getTimeSlot());
        String item = orderItemsDetails.getProducts();
        String[] items = item.split("@");
        item="";
        for(int i=0;i<items.length;i++)
        {
            item += items[i]+"\n";
        }
        holder.product.setText(item);
        holder.charges.setText(orderItemsDetails.Charges);
    }

    @Override
    public int getItemCount() {
        return orderHistoryDetailsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView orderId,date,timeSlot,product,charges;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderId = itemView.findViewById(R.id.textViewHOrderId);
            date = itemView.findViewById(R.id.textViewHDate);
            timeSlot = itemView.findViewById(R.id.textViewHTimeSlot);
            product = itemView.findViewById(R.id.textViewHTests);
            charges = itemView.findViewById(R.id.textViewHCharges);
        }
    }
}
