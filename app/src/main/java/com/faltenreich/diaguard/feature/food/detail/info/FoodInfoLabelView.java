package com.faltenreich.diaguard.feature.food.detail.info;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.core.content.ContextCompat;

import com.faltenreich.diaguard.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Faltenreich on 29.10.2016.
 */

public class FoodInfoLabelView extends LinearLayout {

    private static final @DrawableRes int DEFAULT_ICON_RES_ID = R.drawable.ic_info;

    public enum Type {

        DEFAULT(R.color.gray_darker),
        WARNING(R.color.yellow),
        ERROR(R.color.red_dark);

        public @ColorRes int color;

        Type(@ColorRes int color) {
            this.color = color;
        }
    }

    @BindView(R.id.food_label) TextView label;
    @BindView(R.id.food_icon) ImageView icon;

    private String text;
    private Type type;
    private @DrawableRes int iconResId;

    public FoodInfoLabelView(Context context) {
        super(context);
        this.type = Type.DEFAULT;
        this.iconResId = DEFAULT_ICON_RES_ID;
        init();
    }

    public FoodInfoLabelView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.type = Type.DEFAULT;
        this.iconResId = DEFAULT_ICON_RES_ID;
        init();
    }

    public FoodInfoLabelView(Context context, String text) {
        super(context);
        this.text = text;
        this.type = Type.DEFAULT;
        this.iconResId = DEFAULT_ICON_RES_ID;
        init();
    }

    public FoodInfoLabelView(Context context, String text, Type type) {
        super(context);
        this.text = text;
        this.type = type;
        this.iconResId = DEFAULT_ICON_RES_ID;
        init();
    }

    public FoodInfoLabelView(Context context, String text, Type type, @DrawableRes int iconResId) {
        super(context);
        this.text = text;
        this.type = type;
        this.iconResId = iconResId;
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_food_label, this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        label.setText(text);

        icon.setImageResource(iconResId);

        int color = ContextCompat.getColor(getContext(), type.color);
        label.setTextColor(color);
        icon.setColorFilter(color);
    }
}
