package com.arturdevmob.keepmoney.ui;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.arturdevmob.keepmoney.R;
import com.arturdevmob.keepmoney.ui.about.AboutFragment;
import com.arturdevmob.keepmoney.ui.accounts.list.AccountsListFragment;
import com.arturdevmob.keepmoney.ui.base.BaseActivity;
import com.arturdevmob.keepmoney.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SingleActivity extends BaseActivity {
    @BindView(R.id.fragment_container) FrameLayout mFragmentContainer;
    @BindView(R.id.bottom_navigation) BottomNavigationView mBottomNavigation;

    @Override
    public void setupToolbar() {
        // Это сингл активити, в ней отображаются фрагменты в которых и переопределяется тулбар
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single);

        ButterKnife.bind(this);

        loadFragment(AccountsListFragment.newInstance(), AccountsListFragment.TAG);

        mBottomNavigation.setOnNavigationItemSelectedListener((itemMenu) -> {
            switch (itemMenu.getItemId()) {
                case R.id.accounts_action:
                    loadFragment(AccountsListFragment.newInstance(), AccountsListFragment.TAG);
                break;

                case R.id.settings_action:
                    loadFragment(SettingsFragment.newInstance(), SettingsFragment.TAG);
                break;

                case R.id.about_app_action:
                    loadFragment(AboutFragment.newInstance(), AboutFragment.TAG);
                break;
            }

            return true;
        });
    }

    private void loadFragment(Fragment fragment, String tag) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, tag).commit();
    }
}
