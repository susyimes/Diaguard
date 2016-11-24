package com.faltenreich.diaguard.ui.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.data.PreferenceHelper;
import com.faltenreich.diaguard.data.entity.Food;
import com.faltenreich.diaguard.data.entity.Measurement;
import com.faltenreich.diaguard.event.Events;
import com.faltenreich.diaguard.event.data.FoodDeletedEvent;
import com.faltenreich.diaguard.ui.view.FoodLabelView;
import com.faltenreich.diaguard.util.Helper;

import butterknife.BindView;

/**
 * Created by Faltenreich on 28.10.2016.
 */

public class FoodDetailFragment extends BaseFoodFragment {

    @BindView(R.id.food_brand) TextView brand;
    @BindView(R.id.food_ingredients) TextView ingredients;
    @BindView(R.id.food_value) TextView value;
    @BindView(R.id.food_labels) ViewGroup labels;

    public FoodDetailFragment() {
        super(R.layout.fragment_food_detail, R.string.info, R.drawable.ic_category_meal);
    }

    @Override
    public void onResume() {
        super.onResume();
        Events.register(this);
        init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Events.unregister(this);
    }

    private void init() {

        ingredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredients.setMaxLines(Integer.MAX_VALUE);
            }
        });

        Food food = getFood();
        if (food != null) {
            String placeholder = getString(R.string.placeholder);
            brand.setText(TextUtils.isEmpty(food.getBrand()) ? placeholder : food.getBrand());
            ingredients.setText(TextUtils.isEmpty(food.getIngredients()) ? placeholder : food.getIngredients());

            float mealValue = PreferenceHelper.getInstance().formatDefaultToCustomUnit(
                    Measurement.Category.MEAL,
                    food.getCarbohydrates());
            String mealUnit = PreferenceHelper.getInstance().getUnitAcronym(Measurement.Category.MEAL);
            value.setText(String.format("%s %s %s 100g", Helper.parseFloat(mealValue), mealUnit, getString(R.string.per)));

            if (food.getSugarLevel() != null) {
                switch (food.getSugarLevel()) {
                    case MODERATE:
                        labels.addView(new FoodLabelView(
                                getContext(),
                                getString(R.string.sugar_level_moderate),
                                FoodLabelView.Type.WARNING,
                                R.drawable.ic_error));
                        break;
                    case HIGH:
                        labels.addView(new FoodLabelView(
                                getContext(),
                                getString(R.string.sugar_level_high),
                                FoodLabelView.Type.ERROR,
                                R.drawable.ic_error));
                        break;
                    default:
                }
            }

            if (food.getCarbohydrates() > 0) {
                // TODO: Determine quick- and slow-acting sugar
                if (false) {
                    labels.addView(new FoodLabelView(
                            getContext(),
                            getString(R.string.acting_quick),
                            FoodLabelView.Type.ERROR,
                            R.drawable.ic_error));
                } else if (false) {
                    labels.addView(new FoodLabelView(
                            getContext(),
                            getString(R.string.acting_slow),
                            FoodLabelView.Type.ERROR,
                            R.drawable.ic_error));
                }
            }

            if (food.getLabels() != null && food.getLabels().length() > 0) {
                for (String label : food.getLabels().split(",")) {
                    labels.addView(new FoodLabelView(getContext(), label));
                }
            }
        }
    }

    @SuppressWarnings("unused")
    public void onEvent(FoodDeletedEvent event) {
        if (getFood().equals(event.context)) {
            finish();
        }
    }
}
