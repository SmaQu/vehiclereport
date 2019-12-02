package com.alastor.vehiclereport.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alastor.vehiclereport.DataUtils;
import com.alastor.vehiclereport.R;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private List<Category> mCategories = new ArrayList<>();
    private OnCategoryListener mOnCategoryListener;

    public CategoryAdapter(OnCategoryListener onCategoryListener) {
        this.mOnCategoryListener = onCategoryListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new ViewHolder(view, mOnCategoryListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Category category = mCategories.get(position);
        holder.bindView(category);
    }

    @Override
    public int getItemCount() {
        return mCategories.size();
    }

    public void setCategories(List<Category> freshCategories) {
        mCategories.clear();
        mCategories.addAll(freshCategories);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mExecutionDate;
        private TextView mCategoryName;
        private TextView mAmountElements;
        private ImageView mCategoryAvatar;

        private OnCategoryListener onCategoryListener;

        ViewHolder(@NonNull View itemView, OnCategoryListener onCategoryListener) {
            super(itemView);

            mExecutionDate = itemView.findViewById(R.id.text_execution_date);
            mCategoryName = itemView.findViewById(R.id.text_category_name);
            mAmountElements = itemView.findViewById(R.id.text_amount_elements);
            mCategoryAvatar = itemView.findViewById(R.id.image_category_avatar);

            this.onCategoryListener = onCategoryListener;
        }

        void bindView(Category category) {
            mExecutionDate.setText(DataUtils.getData(category.getExecutionTimestamp()));
            mCategoryName.setText(Category.CategoryId.valueOf(category.getId()).getTranslation(itemView.getContext()));
            mAmountElements.setText(String.valueOf(category.getAmountOfElements()));
            mCategoryAvatar.setImageDrawable(ContextCompat
                    .getDrawable(
                            itemView.getContext(),
                            R.drawable.ic_launcher_background));

            itemView.setOnClickListener(v -> {
                onCategoryListener.onCategoryClick(category.getId());
            });
        }
    }

    public interface OnCategoryListener {
        void onCategoryClick(String categoryId);
    }
}
