package com.example.m_expense;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.m_expense.database.DB_Handler;

import java.util.List;

public class adapter extends RecyclerView.Adapter<adapter.ProductViewHolder> {
    //accessing the products added to class
    private Context mCtx;
    private List<tripModel> modelList;
    String src;

    public adapter(Context mCtx, List<tripModel> modelList) {
        this.mCtx = mCtx;
        this.modelList = modelList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View views = null;
        LayoutInflater mInflater = LayoutInflater.from(mCtx);
        views = mInflater.inflate(R.layout.trip_layout, parent, false);
        return new ProductViewHolder(views);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        tripModel models = modelList.get(position);

        holder.tripName.setText( models.getTitle());
        holder.dateTime.setText( models.getDate());
        Glide.with(mCtx).load(models.getImageLink()).into(holder.imageView);

        holder.card.setOnClickListener(v -> {
            Intent myIntent = new Intent(mCtx, TripExpenses.class);
            myIntent.putExtra("hasData", true);
            myIntent.putExtra("model", models);
            mCtx.startActivity(myIntent);
        });
        holder.delete.setOnClickListener(v -> {
            DB_Handler prcndbHandler = new DB_Handler(mCtx);
            prcndbHandler.tripDelete(models.getId());

            Intent myIntent = new Intent(mCtx, MainActivity.class);
            mCtx.startActivity(myIntent);
        });
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {

        TextView tripName, dateTime;
        ImageView imageView;
        CardView card;
        Button delete;

        public ProductViewHolder(View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.button2);
            card = itemView.findViewById(R.id.card);
            tripName = itemView.findViewById(R.id.tripName);
            dateTime = itemView.findViewById(R.id.dateTime);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
