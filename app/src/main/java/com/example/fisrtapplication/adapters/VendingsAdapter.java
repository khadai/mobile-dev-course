package com.example.fisrtapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fisrtapplication.R;
import com.example.fisrtapplication.activities.ItemDetailsActivity;
import com.example.fisrtapplication.entities.Vending;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class VendingsAdapter extends RecyclerView.Adapter<VendingsAdapter.VendingViewHolder> {
    private static final int TARGET_WIDTH = 88;
    private static final int TARGET_HEIGHT = 86;

    private List<Vending> vendings;
    private Context mContext;

    public VendingsAdapter(Context context, List<Vending> vendings) {
        this.mContext = context;
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
        Picasso.get()
                .load(vendings.get(position).getPicture())
                .placeholder(R.drawable.vending_placeholder)
                .resize(TARGET_WIDTH, TARGET_HEIGHT)
                .centerCrop()
                .into(holder.vendingImageUrl);
        holder.parentLayout.setOnClickListener(view -> {
            openItemDetails(position);
        });
    }

    public void openItemDetails(int position) {
        Log.d("FROM", vendings.toString());

        Intent intent = new Intent(mContext, ItemDetailsActivity.class);
        intent.putExtra("vending_name", vendings.get(position).getName());
        intent.putExtra("vending_company", vendings.get(position).getCompany());
        intent.putExtra("vending_goods", vendings.get(position).getGood());
        intent.putExtra("vending_address", vendings.get(position).getAddress());
        intent.putExtra("vending_img_url", vendings.get(position).getPicture());

        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return vendings.size();
    }

    public List<Vending> getVendings() {
        return vendings;
    }

    class VendingViewHolder extends RecyclerView.ViewHolder {
        private TextView vendingName;
        private TextView vendingCompany;
        private TextView vendingGood;
        private TextView vendingAddress;
        private ImageView vendingImageUrl;
        private ConstraintLayout parentLayout;

        VendingViewHolder(final View itemView) {
            super(itemView);
            vendingName = itemView.findViewById(R.id.item_vending_name);
            vendingCompany = itemView.findViewById(R.id.item_vending_company);
            vendingGood = itemView.findViewById(R.id.item_vending_good);
            vendingAddress = itemView.findViewById(R.id.item_vending_address);
            vendingImageUrl = itemView.findViewById(R.id.item_vending_img);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
