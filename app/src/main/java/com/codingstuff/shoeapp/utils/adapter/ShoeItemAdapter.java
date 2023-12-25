package com.codingstuff.shoeapp.utils.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.codingstuff.shoeapp.R;
import com.codingstuff.shoeapp.utils.model.ShoeItem;

import java.util.List;

public class ShoeItemAdapter extends RecyclerView.Adapter<ShoeItemAdapter.ShoeItemViewHolder> {

    private List<ShoeItem> shoeItemList;
    private ShoeClickedListeners shoeClickedListeners;
    private String[] sizeOptions = {"Small ", "Midium", "Larg",  "Larg"};

    public ShoeItemAdapter(ShoeClickedListeners shoeClickedListeners){
        this.shoeClickedListeners = shoeClickedListeners;
    }

    public void setShoeItemList(List<ShoeItem> shoeItemList){
        this.shoeItemList = shoeItemList;
    }
//viewHolder put here
    @NonNull
    @Override
    public ShoeItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_shoe, parent, false);
        return new ShoeItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoeItemViewHolder holder, int position) {
        ShoeItem shoeItem = shoeItemList.get(position);
        holder.shoeNameTv.setText(shoeItem.getShoeName());
        holder.shoeBrandNameTv.setText(shoeItem.getShoeBrandName());
        holder.shoePriceTv.setText(String.valueOf(shoeItem.getShoePrice()));
        holder.shoeImageView.setImageResource(shoeItem.getShoeImage());
        holder.eachShoeColorTv.setText(shoeItem.getColor());
        holder.eachShoeColorTv1.setText(shoeItem.getColor1());
        holder.eachShoeColorTv2.setText(shoeItem.getColor2());
        holder.AvailbleTv.setText(shoeItem.getAvailable());
        holder.AnumberTv.setText(String.valueOf(shoeItem.getAnum()));

        // Set the adapter for the Spinner
        ArrayAdapter<String> sizeAdapter = new ArrayAdapter<>(holder.itemView.getContext(), android.R.layout.simple_spinner_item, sizeOptions);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.eachShoeSizeSpinner.setAdapter(sizeAdapter);

        // Find the position of the size in sizeOptions
        int sizePosition = getPositionForSize(shoeItem.getSize());
//
        // Set the selection for the Spinner
        holder.eachShoeSizeSpinner.setSelection(sizePosition);

        holder.addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int available = shoeItem.getAnum();
                if (available > 0) {
                    available--;
                    shoeItem.setAnum(available);
                    holder.AnumberTv.setText(String.valueOf(available));

                    if (available == 0) {
                        holder.AvailbleTv.setText("Out of Stock");
                    }

                    notifyItemChanged(holder.getAdapterPosition());
                    shoeClickedListeners.onAddToCartBtnClicked(shoeItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return shoeItemList != null ? shoeItemList.size() : 0;
    }

    private int getPositionForSize(String size) {
        for (int i = 0; i < sizeOptions.length; i++) {
            if (sizeOptions[i].equals(size)) {
                return i;
            }
        }
        return 0; // Default position if size is not found
    }

    public class ShoeItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView shoeImageView, addToCartBtn;
        private TextView shoeNameTv, shoeBrandNameTv, shoePriceTv, eachShoeColorTv,eachShoeColorTv1,eachShoeColorTv2, AvailbleTv, AnumberTv;
        private Spinner eachShoeSizeSpinner;
        private CardView cardView;

        public ShoeItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.eachShoeCardView);
            addToCartBtn = itemView.findViewById(R.id.eachShoeAddToCartBtn);
            shoeNameTv = itemView.findViewById(R.id.eachShoeName);
            shoeImageView = itemView.findViewById(R.id.eachShoeIv);
            shoeBrandNameTv = itemView.findViewById(R.id.eachShoeBrandNameTv);
            shoePriceTv = itemView.findViewById(R.id.eachShoePriceTv);
            eachShoeColorTv = itemView.findViewById(R.id.eachShoeColorTv);
            eachShoeColorTv1 = itemView.findViewById(R.id.eachShoeColorTv1);
            eachShoeColorTv2 = itemView.findViewById(R.id.eachShoeColorTv2);

            eachShoeSizeSpinner = itemView.findViewById(R.id.eachShoeSizeSpinner);
            AvailbleTv = itemView.findViewById(R.id.available);
            AnumberTv = itemView.findViewById(R.id.numInSock);

        }
    }

    public interface ShoeClickedListeners {
        void onCardClicked(ShoeItem shoe);

        void onAddToCartBtnClicked(ShoeItem shoeItem);
    }
}
