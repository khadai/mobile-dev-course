package com.example.fisrtapplication.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fisrtapplication.R;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class ItemDetailsActivity extends AppCompatActivity {
    private final int TARGET_WIDTH = 100;
    private final int TARGET_HEIGHT = 100;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Vending Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getIncomingIntent();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("vending_name") &&
                getIntent().hasExtra("vending_company") &&
                getIntent().hasExtra("vending_goods") &&
                getIntent().hasExtra("vending_address") &&
                getIntent().hasExtra("vending_img_url")) {
            String vendingName = getIntent().getStringExtra("vending_name");
            String vendingGoods = getIntent().getStringExtra("vending_goods");
            String vendingAddress = getIntent().getStringExtra("vending_address");
            String vendingCompany = getIntent().getStringExtra("vending_company");
            String imageName = getIntent().getStringExtra("vending_img_url");

            setImage(vendingName, vendingGoods, vendingAddress, vendingCompany, imageName);
        }
    }

    private void setImage(String vendingName, String vendingGoods, String vendingAddress, String vendingCompany, String imageName) {
        TextView name = findViewById(R.id.ven_name_detailed);
        TextView goods = findViewById(R.id.ven_goods_detailed);
        TextView address = findViewById(R.id.ven_address_detailed);
        TextView company = findViewById(R.id.ven_company_detailed);
        ImageView imageView = findViewById(R.id.ven_img_detailed);
        Picasso.get()
                .load(imageName)
                .placeholder(R.drawable.vending_placeholder)
                .resize(TARGET_WIDTH, TARGET_HEIGHT)
                .centerCrop()
                .into(imageView);
        name.setText(vendingName);
        goods.setText(vendingGoods);
        address.setText(vendingAddress);
        company.setText(vendingCompany);
    }
}
