package com.arturdevmob.keepmoney.ui.settings;

import android.os.Bundle;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.Utils;
import com.arturdevmob.keepmoney.ui.settings.category.list.CategoryListFragment;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.widget.Toolbar;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import butterknife.BindView;

public class SettingsFragment extends PreferenceFragmentCompat {
    public static final String TAG = "SettingsFragment";

    @BindView(R.id.include_toolbar) Toolbar mToolbar;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);

        setupToolbar();
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        switch (preference.getKey()) {
            case "start_activity_category":
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, CategoryListFragment.newInstance(), CategoryListFragment.TAG)
                        .addToBackStack(SettingsFragment.TAG)
                        .commit();
            break;

            case "clear_data":
                boolean clearData = Utils.clearData(getContext());

                if (clearData) {
                    Snackbar.make(getView(), "Все данные приложения успешно удалены!", Snackbar.LENGTH_LONG).show();
                }
            break;
        }

        return super.onPreferenceTreeClick(preference);
    }

    private void setupToolbar() {

    }
}
