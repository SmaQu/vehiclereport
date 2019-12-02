package com.alastor.vehiclereport.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alastor.vehiclereport.R;
import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;

public class AutoCompleteCategoryAdapter extends ArrayAdapter<Category.CategoryId> {

    private static final String TAG = AutoCompleteCategoryAdapter.class.getSimpleName();
    private @NonNull
    Category.CategoryId[] categories;
    private int resource;

    public AutoCompleteCategoryAdapter(@NonNull Context context,
                                       int resource,
                                       @NonNull Category.CategoryId[] categories) {
        super(context, resource, categories);
        this.categories = categories;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater
                    .from(parent.getContext())
                    .inflate(resource, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.bindView(categories[position]);
        return convertView;
    }

    private class ViewHolder {
        private TextView categoryTv;
        private View itemView;

        public ViewHolder(final View view) {
            this.itemView = view;
            view.findViewById(R.id.text_category);
        }

        void bindView(Category.CategoryId categoryId) {
            final TextView categoryTv = itemView.findViewById(R.id.text_category);
            categoryTv.setText(categoryId.getTranslation(getContext()));
        }
    }
}
