package com.codingstuff.shoeapp.views;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codingstuff.shoeapp.R;
import com.codingstuff.shoeapp.utils.adapter.ShoeItemAdapter;
import com.codingstuff.shoeapp.utils.model.ShoeCart;
import com.codingstuff.shoeapp.utils.model.ShoeItem;
import com.codingstuff.shoeapp.viewmodel.CartViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ShoeItemAdapter.ShoeClickedListeners {
    private int j;
    private RecyclerView recyclerView;
    private List<ShoeItem> shoeItemList;
    private ShoeItemAdapter adapter;
    private CartViewModel viewModel;
    private List<ShoeCart> shoeCartList;
    private CoordinatorLayout coordinatorLayout;
    private ImageView cartImageView;
    private RecyclerView secondRecyclerView; // New RecyclerView added

    private WifiBroadcastReceiver wifiBroadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        registerNetworkChangeReceiver();

        initializeVariables();
        setUpList();


        adapter.setShoeItemList(shoeItemList);
        recyclerView.setAdapter(adapter);
        adapter.setShoeItemList(shoeItemList); // Use the same data for both RecyclerViews
        secondRecyclerView.setAdapter(adapter); // Use the same adapter for


        cartImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel.getAllCartItems().observe(this, new Observer<List<ShoeCart>>() {
            @Override
            public void onChanged(List<ShoeCart> shoeCarts) {
                shoeCartList.addAll(shoeCarts);
            }
        });
    }

    private void setUpList() {
        shoeItemList.add(new ShoeItem("Nice while shirt", "dress", R.drawable.shopping, 30," "," "," ","l","in stock",30));
        shoeItemList.add(new ShoeItem("احمد عاطف", "بني ادم", R.drawable.ahmed, 00," "," "," ","l","out of stock",00));
        shoeItemList.add(new ShoeItem("Court Zoom Vapor", "Casual", R.drawable.outfittt, 18," "," "," ","l","in stock",30));
        shoeItemList.add(new ShoeItem("Black dress", "dress", R.drawable.fostan, 16.5," "," "," ","l","in stock",3));
        shoeItemList.add(new ShoeItem("pants", "Casual", R.drawable.bantlon, 20," "," "," ","l","in stock",3));
        shoeItemList.add(new ShoeItem("Tag Women's chain", "accessories", R.drawable.kinga, 22," "," "," ","l","in stock",3));
        shoeItemList.add(new ShoeItem("His pharaohs Women's chain", "accessories", R.drawable.fra3na, 12," "," "," ","l","in stock",3));
        shoeItemList.add(new ShoeItem("His pharaohs Women's chain", "accessories", R.drawable.silsla, 15," "," "," ","l","out of stock",00));
    }

    private void initializeVariables() {

        cartImageView = findViewById(R.id.cartIv);
        coordinatorLayout = findViewById(R.id.coordinatorLayout);
        shoeCartList = new ArrayList<>();
        viewModel = new ViewModelProvider(this).get(CartViewModel.class);
        shoeItemList = new ArrayList<>();
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        // Initialize the second RecyclerView

        secondRecyclerView = findViewById(R.id.secondRecyclerView); // Replace 'secondRecyclerView' with the correct ID
        secondRecyclerView.setHasFixedSize(true);
        secondRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        adapter = new ShoeItemAdapter(this);

    }

    @Override
    public void onCardClicked(ShoeItem shoe) {

        Intent intent = new Intent(MainActivity.this, DetailedActivity.class);
        intent.putExtra("shoeItem", shoe);
        startActivity(intent);
    }

    @Override
    public void onAddToCartBtnClicked(ShoeItem shoeItem) {
        ShoeCart shoeCart = new ShoeCart();
        shoeCart.setShoeName(shoeItem.getShoeName());
        shoeCart.setShoeBrandName(shoeItem.getShoeBrandName());
        shoeCart.setShoePrice(shoeItem.getShoePrice());
        shoeCart.setShoeImage(shoeItem.getShoeImage());



        final int[] quantity = {1};
        final int[] id = new int[1];

        if (!shoeCartList.isEmpty()) {
            for (int i = 0; i < shoeCartList.size(); i++) {
                if (shoeCart.getShoeName().equals(shoeCartList.get(i).getShoeName())) {
                    quantity[0] = shoeCartList.get(i).getQuantity();
                    quantity[0]++;
                    id[0] = shoeCartList.get(i).getId();
                }
            }
        }

        Log.d("TAG", "onAddToCartBtnClicked: " + quantity[0]);

        if (quantity[0] == 1) {
            shoeCart.setQuantity(quantity[0]);
            shoeCart.setTotalItemPrice(quantity[0] * shoeCart.getShoePrice());
            viewModel.insertCartItem(shoeCart);
        } else {
            viewModel.updateQuantity(id[0], quantity[0]);
            viewModel.updatePrice(id[0], quantity[0] * shoeCart.getShoePrice());
        }

        makeSnackBar("Item Added To Cart");
    }

    private void registerNetworkChangeReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SUPPLICANT_CONNECTION_CHANGE_ACTION);

        wifiBroadcastReceiver = new WifiBroadcastReceiver();
        registerReceiver(wifiBroadcastReceiver, intentFilter);
    }
//////////////////////////////////////////////////////////
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiBroadcastReceiver);
    }

    private void makeSnackBar(String msg) {
        Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_SHORT)
                .setAction("Go to Cart", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(MainActivity.this, CartActivity.class));
                    }
                }).show();
    }

}