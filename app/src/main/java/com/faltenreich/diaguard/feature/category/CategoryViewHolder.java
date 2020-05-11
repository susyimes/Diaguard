package com.faltenreich.diaguard.feature.category;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.shared.data.preference.PreferenceHelper;
import com.faltenreich.diaguard.shared.data.database.entity.Category;
import com.faltenreich.diaguard.shared.view.recyclerview.drag.Draggable;
import com.faltenreich.diaguard.shared.view.recyclerview.viewholder.BaseViewHolder;
import com.faltenreich.diaguard.shared.view.resource.ColorUtils;

import butterknife.BindView;

class CategoryViewHolder extends BaseViewHolder<Category> implements Draggable {

    @BindView(R.id.background) ViewGroup background;
    @BindView(R.id.titleLabel) TextView titleLabel;
    @BindView(R.id.checkBoxActive) CheckBox activeCheckBox;
    @BindView(R.id.checkBoxPinned) CheckBox pinnedCheckBox;
    @BindView(R.id.dragView) View dragView;

    CategoryViewHolder(ViewGroup parent, CategoryListAdapter.Listener listener) {
        super(parent, R.layout.list_item_category);
        activeCheckBox.setOnCheckedChangeListener((v, isChecked) -> {
            PreferenceHelper.getInstance().setCategoryActive(getItem(), isChecked);
            listener.onCheckedChange();
        });
        pinnedCheckBox.setOnCheckedChangeListener((v, isChecked) -> PreferenceHelper.getInstance().setCategoryPinned(getItem(), isChecked));
        dragView.setOnTouchListener((v, event) -> {
            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                listener.onReorderStart(CategoryViewHolder.this);
                v.performClick();
            }
            return false;
        });
    }

    @Override
    protected void onBind(Category item) {
        Category category = getItem();
        titleLabel.setText(getContext().getString(category.getStringResId()));
        activeCheckBox.setEnabled(category.isOptional());
        activeCheckBox.setChecked(PreferenceHelper.getInstance().isCategoryActive(category));
        pinnedCheckBox.setChecked(PreferenceHelper.getInstance().isCategoryPinned(category));
        dragView.setVisibility(category.isOptional() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public boolean isDraggable() {
        return getItem() != Category.BLOODSUGAR;
    }

    @Override
    public void onDrag(boolean isDragged) {
        background.setBackgroundColor(isDragged ? ColorUtils.getBackgroundSecondary(getContext()) : ColorUtils.getBackgroundPrimary(getContext()));
    }
}
