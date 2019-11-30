package com.alastor.vehiclereport.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.alastor.vehiclereport.repository.roomdatabase.entity.Category;

public class CategoryAutoCompleteTextView extends AppCompatAutoCompleteTextView {

    private Category.CategoryId categoryId = null;

    public CategoryAutoCompleteTextView(Context context) {
        super(context);
    }

    public CategoryAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CategoryAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    @Override
    protected void replaceText(CharSequence text) {
        categoryId = Category.CategoryId.valueOf(text.toString());
        String translatedCategory = categoryId.getTranslation(getContext());
        super.replaceText(translatedCategory);
    }

    public Category.CategoryId getCategoryId() {
        return categoryId;
    }
}
