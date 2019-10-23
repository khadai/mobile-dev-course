package com.example.fisrtapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fisrtapplication.R;
import com.example.fisrtapplication.entities.Vending;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VendingsAdapter extends RecyclerView.Adapter<VendingsAdapter.VendingViewHolder> {
    private List<Vending> vendings;

    public VendingsAdapter(List<Vending> vendings) {
        this.vendings = vendings;
    }

    @NonNull
    @Override
    public VendingsAdapter.VendingViewHolder onCreateViewHolder(@NonNull final ViewGroup parent,
                                                                final int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_vending, parent, false);
        return new VendingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VendingViewHolder holder,
                                 final int position) {
        holder.vendingName.setText(vendings.get(position).getName());
        holder.vendingCompany.setText(vendings.get(position).getCompany());
        holder.vendingGood.setText(vendings.get(position).getGood());
        holder.vendingAddress.setText(vendings.get(position).getAddress());
        Picasso.get().load(vendings.get(position).getPicture()).into(holder.vendingImageUrl);

    }

    @Override
    public int getItemCount() {
        return vendings.size();
    }

    class VendingViewHolder extends RecyclerView.ViewHolder {
        private TextView vendingName;
        private TextView vendingCompany;
        private TextView vendingGood;
        private TextView vendingAddress;
        private ImageView vendingImageUrl;

        VendingViewHolder(final View itemView) {
            super(itemView);
            vendingName = itemView.findViewById(R.id.item_vending_name);
            vendingCompany = itemView.findViewById(R.id.item_vending_company);
            vendingGood = itemView.findViewById(R.id.item_vending_good);
            vendingAddress = itemView.findViewById(R.id.item_vending_address);
            vendingImageUrl = itemView.findViewById(R.id.item_vending_img);
        }
    }
}
