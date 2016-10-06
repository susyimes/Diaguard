package com.faltenreich.diaguard.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.data.entity.Food;
import com.faltenreich.diaguard.ui.view.viewholder.FoodViewHolder;

/**
 * Created by Faltenreich on 11.09.2016.
 */
public class FoodAdapter extends BaseAdapter<Food, FoodViewHolder> {

    public FoodAdapter(Context context) {
        super(context);
    }

    @Override
    public FoodViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FoodViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.list_item_food, parent, false));
    }

    @Override
    public void onBindViewHolder(FoodViewHolder holder, int position) {
        holder.bindData(getItem(holder.getAdapterPosition()));
    }
}