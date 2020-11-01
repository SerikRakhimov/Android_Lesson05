package com.example.products;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private ArrayList<Product> products;
    private MyOnClickListener onClickListener;

    public MyAdapter(ArrayList<Product> products) {
        this.products = products;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product product = products.get(position);

        holder.food.setImageResource(product.getFoodResId());
        holder.name.setText(product.getName());
        holder.description.setText(product.getDescription());

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(product);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClickListener.onLongClick(product);
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setOnClickListener(MyOnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void addItem(Product product) {
        products.add(product);
        notifyDataSetChanged();
    }

    public void removeItem(Product product) {
        products.remove(product);
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView food;
        public TextView name, description;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            food = itemView.findViewById(R.id.productFoodImageView);
            name = itemView.findViewById(R.id.productNameTextView);
            description = itemView.findViewById(R.id.productDescriptionTextView);
        }
    }

    public interface MyOnClickListener {
        void onClick(Product product);
        void onLongClick(Product product);
    }

}
