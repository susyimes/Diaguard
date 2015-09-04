package com.faltenreich.diaguard;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.faltenreich.diaguard.database.DatabaseFacade;
import com.faltenreich.diaguard.database.Entry;
import com.faltenreich.diaguard.fragments.EntryDetailFragment;

import org.joda.time.format.DateTimeFormat;

import java.sql.SQLException;


public class EntryDetailActivity extends BaseActivity {

    private long entryId;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_entry_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle arguments = new Bundle();
        entryId = getIntent().getLongExtra(EntryDetailFragment.EXTRA_ENTRY, 0);
        arguments.putLong(EntryDetailFragment.EXTRA_ENTRY, entryId);
        EntryDetailFragment fragment = new EntryDetailFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.entry_detail, fragment)
                .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            // Display date and time of entry in ActionBar
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            Entry entry = DatabaseFacade.getInstance().getDao(Entry.class).queryForId(entryId);
            if (entry != null && toolbar != null){
                String weekDay = entry.getDate().dayOfWeek().getAsShortText();
                String dateTime = entry.getDate().toString(DateTimeFormat.shortDateTime());
                toolbar.setTitle(String.format("%s, %s", weekDay, dateTime));
            }
        } catch (SQLException exception) {
            Log.e("EntryDetailFragment", exception.getMessage());
        }
    }
}
