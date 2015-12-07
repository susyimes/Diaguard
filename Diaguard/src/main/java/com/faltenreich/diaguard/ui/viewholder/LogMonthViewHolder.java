package com.faltenreich.diaguard.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.adapter.list.ListItemMonth;
import com.faltenreich.diaguard.data.PreferenceHelper;
import com.squareup.picasso.Picasso;

import org.joda.time.DateTime;

import butterknife.Bind;

/**
 * Created by Faltenreich on 17.10.2015.
 */
public class LogMonthViewHolder extends BaseViewHolder<ListItemMonth> {

    @Bind(R.id.background)
    protected ImageView background;

    @Bind(R.id.month)
    protected TextView month;

    public LogMonthViewHolder(View view) {
        super(view);
    }

    @Override
    public void bindData() {
        DateTime dateTime = getListItem().getDateTime();
        int resourceId = PreferenceHelper.getInstance().getSeasonResourceId(dateTime);
        Picasso.with(getContext()).load(resourceId).placeholder(R.color.gray_lighter).into(background);
        month.setText(dateTime.toString("MMMM YYYY"));
    }
}