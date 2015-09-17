package com.faltenreich.diaguard.ui.view;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.faltenreich.diaguard.adapter.ChartPagerAdapter;
import com.faltenreich.diaguard.ui.fragments.ChartDayFragment;

import org.joda.time.DateTime;

/**
 * Created by Faltenreich on 02.08.2015.
 */
public class ChartViewPager extends ViewPager {

    private ChartPagerAdapter adapter;

    public ChartViewPager(Context context) {
        super(context);
    }

    public ChartViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setup(final FragmentManager fragmentManager, final ChartViewPagerCallback callback) {
        adapter = new ChartPagerAdapter(fragmentManager, DateTime.now());
        setAdapter(adapter);
        setCurrentItem(adapter.getMiddle(), false);

        addOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    switch (getCurrentItem()) {
                        case 0:
                            adapter.previousDay();
                            break;
                        case 2:
                            adapter.nextDay();
                            break;
                    }
                    setCurrentItem(adapter.getMiddle(), false);
                }
            }
            @Override
            public void onPageSelected(int position) {
                ChartDayFragment selectedFragment = (ChartDayFragment) adapter.getItem(position);
                callback.onDaySelected(selectedFragment.getDay());
            }
        });
    }

    public void setDay(DateTime day) {
        adapter.setDay(day);
    }

    public interface ChartViewPagerCallback {
        void onDaySelected(DateTime day);
    }
}