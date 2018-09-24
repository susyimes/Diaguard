package com.faltenreich.diaguard.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.View;

import com.faltenreich.diaguard.R;
import com.faltenreich.diaguard.data.entity.Tag;
import com.faltenreich.diaguard.ui.fragment.EntrySearchFragment;
import com.faltenreich.diaguard.util.Vector2D;
import com.faltenreich.diaguard.util.ViewUtils;

public class EntrySearchActivity extends BaseSearchActivity {

    private static Intent getIntent(Context context, @Nullable View source) {
        return BaseActivity.getIntent(EntrySearchActivity.class, context, source);
    }

    public static void show(Context context, @Nullable View source) {
        context.startActivity(getIntent(context, source));
    }

    public static void show(Context context, Tag tag) {
        Intent intent = getIntent(context, null);
        intent.putExtra(EntrySearchFragment.EXTRA_TAG_ID, tag.getId());
        context.startActivity(intent);
    }

    public EntrySearchActivity() {
        super(R.layout.activity_entry_search);
    }
}