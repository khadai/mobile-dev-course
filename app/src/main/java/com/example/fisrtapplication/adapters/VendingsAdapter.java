package com.example.fisrtapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fisrtapplication.R;
import com.example.fisrtapplication.entities.Vending;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VendingsAdapter extends RecyclerView.Adapter<VendingsAdapter.VendingViewHolder>{
    private List<Vending> vendings = new ArrayList<>();

    @NonNull
    @Override
    public VendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_vending,parent, false);
        return new VendingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull VendingViewHolder holder, int position) {
        final Vending vending = vendings.get(position);
        holder.vendingName.setText(vending.getName());
    }

    @Override
    public int getItemCount() {
        return vendings.size();
    }

    public void updateVendings(final List<Vending> vendings) {
        this.vendings = vendings;
    }

    class VendingViewHolder extends RecyclerView.ViewHolder {
        private TextView vendingName;

        VendingViewHolder(@NonNull final View itemView) {
            super(itemView);

            vendingName = itemView.findViewById(R.id.item_vending_name);
        }
    }
}
