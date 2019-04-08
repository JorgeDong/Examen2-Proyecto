package com.iteso.ItesoStore;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iteso.test.R;
import com.iteso.ItesoStore.beans.ItemProduct;

import java.util.ArrayList;

public class AdapterProduct extends RecyclerView.Adapter<AdapterProduct.ViewHolder> {

    private ArrayList<ItemProduct> mDataSet;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button mDetail;
        public TextView mProductTitle;
        public TextView mProductStore;
        public TextView mProductCity;
        public TextView mProductPhone;
        public ImageView mProductImage;
        public ImageView mProductThumbnail;
        public RelativeLayout mEventLayout;

        public ViewHolder(View v) {
            super(v);
            mEventLayout = v.findViewById(R.id.item_product_layout);
            mDetail = v.findViewById(R.id.item_product_detail);
            mProductTitle = v.findViewById(R.id.item_product_title);
            mProductStore = v.findViewById(R.id.item_product_store);
            mProductCity = v.findViewById(R.id.item_product_location);
            mProductPhone = v.findViewById(R.id.item_product_phone);
            mProductImage = v.findViewById(R.id.item_product_image);
            mProductThumbnail = v.findViewById(R.id.item_product_thumbnail);
        }
    }


    public AdapterProduct(Context context, ArrayList<ItemProduct> myDataSet) {
        this.mDataSet = myDataSet;
        this.context = context;
    }


    @NonNull
    public AdapterProduct.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.mProductTitle.setText(mDataSet.get(position).getTitle());
        holder.mProductStore.setText(mDataSet.get(position).getStore().toString());
        holder.mProductCity.setText(mDataSet.get(position).getStore().getCity().getName());

        switch (mDataSet.get(position).getImage()) {
            case 0:
                holder.mProductImage.setImageResource(R.drawable.mac);
                break;
            case 1:
                holder.mProductImage.setImageResource(R.drawable.alienware);
                break;
            case 3:
                holder.mProductImage.setImageResource(R.drawable.home);
                break;
            case 4:
                holder.mProductImage.setImageResource(R.drawable.micro);
                break;
        }
        Bitmap bitmap = ((BitmapDrawable) holder.mProductThumbnail.getDrawable()).getBitmap();
        holder.mProductThumbnail.setImageBitmap(bitmap);

        holder.mDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, mDataSet.get(position).toString(), Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }
}
