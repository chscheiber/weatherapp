package com.example.bsaweatherapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder> {
    interface ListItemClickListener {
        void onListItemClick(WeatherData item);
    }

    private List<WeatherData> mItems;
    private ListItemClickListener mClickListener;

    class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemTextView;
        private ImageView mItemImageView;

        ItemViewHolder(View itemView) {
            super(itemView);
            mItemTextView = itemView.findViewById(R.id.wd_item);
            mItemImageView = itemView.findViewById(R.id.wd_image);
            itemView.setOnClickListener(this);
        }

        void bind(int index) {
            WeatherData data = mItems.get(index);
            mItemTextView.setText(data.toString());
            int id = GetDrawableResource.get(data, data.getIcon());
            mItemImageView.setImageResource(id);
        }

        @Override
        public void onClick(View v) {
            if (mClickListener != null) {
                int clickedItemIndex = getAdapterPosition();
                mClickListener.onListItemClick(mItems.get(clickedItemIndex));
            }
        }
    }

    ListAdapter(List<WeatherData> items) {
        mItems = items;
    }

    public void setOnClickListener(ListItemClickListener clickListener) {
        mClickListener = clickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder viewHolder, int position) {
        viewHolder.bind(position);
    }

    @Override
    public int getItemCount() {
        return (mItems == null) ? 0 : mItems.size();
    }

    void swapData(List<WeatherData> newData) {
        mItems = newData;
        notifyDataSetChanged();
    }
}
