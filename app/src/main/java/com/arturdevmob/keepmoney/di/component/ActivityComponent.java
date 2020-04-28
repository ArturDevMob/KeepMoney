package com.arturdevmob.keepmoney.di.component;

import com.arturdevmob.keepmoney.di.PerActivity;
import com.arturdevmob.keepmoney.di.module.ActivityModule;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.TransactionEditActivity;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.choicecategory.ChoiceCategoryActivity;
import com.arturdevmob.keepmoney.ui.accounts.edit.AccountsEditActivity;
import com.arturdevmob.keepmoney.ui.accounts.list.AccountsListFragment;
import com.arturdevmob.keepmoney.ui.accounts.detail.AccountsDetailFragment;
import com.arturdevmob.keepmoney.ui.about.AboutFragment;
import com.arturdevmob.keepmoney.ui.settings.SettingsFragment;
import com.arturdevmob.keepmoney.ui.settings.category.edit.CategoryEditActivity;
import com.arturdevmob.keepmoney.ui.settings.category.list.CategoryListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = {ApplicationComponent.class}, modules = {ActivityModule.class})
public interface ActivityComponent {
    void inject(AccountsListFragment accountsListFragment);
    void inject(AccountsEditActivity accountEditActivity);
    void inject(AboutFragment reportsFragment);
    void inject(SettingsFragment settingsFragment);
    void inject(AccountsDetailFragment detailFragment);
    void inject(TransactionEditActivity transactionEditActivity);
    void inject(CategoryEditActivity categoryEditActivity);
    void inject(CategoryListFragment categoryListFragment);
    void inject(ChoiceCategoryActivity choiceCategoryActivity);
}
