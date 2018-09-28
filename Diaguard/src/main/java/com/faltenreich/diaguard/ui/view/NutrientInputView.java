package com.faltenreich.diaguard.ui.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.data.entity.Food;
import com.faltenreich.diaguard.util.Helper;

@SuppressLint("ViewConstructor")
public class NutrientInputView extends StickyHintInput {

    private Food.Nutrient nutrient;

    public NutrientInputView(Context context, @NonNull Food.Nutrient nutrient, @Nullable Float value) {
        super(context);
        this.nutrient = nutrient;
        init();
        if (value != null) {
            setValue(value);
        }
    }

    @NonNull
    public Food.Nutrient getNutrient() {
        return nutrient;
    }

    @Nullable
    public Float getValue() {
        return getInputView().getNumber();
    }

    public void setValue(float value) {
        getInputView().setText(Helper.parseFloat(value));
    }

    private void init() {
        // TODO: Fix input type
        setHint(String.format("%s %s 100g", nutrient.getLabel(), getContext().getString(R.string.per)));
    }
}
