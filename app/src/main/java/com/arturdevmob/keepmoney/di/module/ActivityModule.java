package com.arturdevmob.keepmoney.di.module;

import android.content.Context;

import com.arturdevmob.keepmoney.di.AplicationContext;
import com.arturdevmob.keepmoney.ui.about.AboutMvpPresenter;
import com.arturdevmob.keepmoney.ui.about.AboutMvpView;
import com.arturdevmob.keepmoney.ui.about.AboutPresenter;
import com.arturdevmob.keepmoney.ui.accounts.detail.AccountsDetailMvpView;
import com.arturdevmob.keepmoney.ui.accounts.detail.AccountsDetailPresenter;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.TransactionAdapter;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.TransactionEditMvpPresenter;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.TransactionEditMvpView;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.TransactionEditPresenter;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.choicecategory.ChoiceCategoryMvpPresenter;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.choicecategory.ChoiceCategoryMvpView;
import com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit.choicecategory.ChoiceCategoryPresenter;
import com.arturdevmob.keepmoney.ui.accounts.edit.AccountsEditMvpPresenter;
import com.arturdevmob.keepmoney.ui.accounts.edit.AccountsEditPresenter;
import com.arturdevmob.keepmoney.ui.accounts.list.AccountsListMvpPresenter;
import com.arturdevmob.keepmoney.ui.accounts.list.AccountsListMvpView;
import com.arturdevmob.keepmoney.ui.accounts.list.AccountsListPresenter;
import com.arturdevmob.keepmoney.ui.accounts.edit.AccountsListEditMvpView;
import com.arturdevmob.keepmoney.ui.accounts.detail.AccountsDetailMvpPresenter;
import com.arturdevmob.keepmoney.ui.settings.SettingsMvpPresenter;
import com.arturdevmob.keepmoney.ui.settings.SettingsMvpView;
import com.arturdevmob.keepmoney.ui.settings.SettingsPresenter;
import com.arturdevmob.keepmoney.ui.settings.category.edit.AdapterCategoryImage;
import com.arturdevmob.keepmoney.ui.settings.category.edit.CategoryEditMvpPresenter;
import com.arturdevmob.keepmoney.ui.settings.category.edit.CategoryEditMvpView;
import com.arturdevmob.keepmoney.ui.settings.category.edit.CategoryEditPresenter;
import com.arturdevmob.keepmoney.ui.settings.category.list.CategoryAdapter;
import com.arturdevmob.keepmoney.ui.settings.category.list.CategoryListMvpPresenter;
import com.arturdevmob.keepmoney.ui.settings.category.list.CategoryListMvpView;
import com.arturdevmob.keepmoney.ui.settings.category.list.CategoryListPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {
    @Provides
    AccountsListMvpPresenter<AccountsListMvpView> provideAccountPresenter(AccountsListPresenter<AccountsListMvpView> presenter) {
        return presenter;
    }

    @Provides
    AccountsEditMvpPresenter<AccountsListEditMvpView> provideAccountEditPresenter(AccountsEditPresenter<AccountsListEditMvpView> presenter) {
        return presenter;
    }

    @Provides
    AboutMvpPresenter<AboutMvpView> provideReportsPresenter(AboutPresenter<AboutMvpView> presenter) {
        return presenter;
    }

    @Provides
    SettingsMvpPresenter<SettingsMvpView> provideSettingsPresenter(SettingsPresenter<SettingsMvpView> presenter) {
        return presenter;
    }

    @Provides
    AccountsDetailMvpPresenter<AccountsDetailMvpView> provideTransactionsPresenter(AccountsDetailPresenter<AccountsDetailMvpView> presenter) {
        return presenter;
    }

    @Provides
    TransactionEditMvpPresenter<TransactionEditMvpView> provideTransactionEditPresenter (TransactionEditPresenter<TransactionEditMvpView> presenter) {
        return presenter;
    }

    @Provides
    CategoryEditMvpPresenter<CategoryEditMvpView> provideCategoryEditPresenter(CategoryEditPresenter<CategoryEditMvpView> presenter) {
        return presenter;
    }

    @Provides
    CategoryListMvpPresenter<CategoryListMvpView> provideCategoryLisPresenter(CategoryListPresenter<CategoryListMvpView> presenter) {
        return presenter;
    }

    @Provides
    ChoiceCategoryMvpPresenter<ChoiceCategoryMvpView> provideChoiceCategoryPresenter(ChoiceCategoryPresenter<ChoiceCategoryMvpView> presenter) {
        return presenter;
    }

    @Provides
    CategoryAdapter provideCategoryAdapter(@AplicationContext Context context) {
        return new CategoryAdapter(context);
    }

    @Provides
    AdapterCategoryImage provideAdapterCategoryImage(@AplicationContext Context context) {
        return new AdapterCategoryImage(context);
    }

    @Provides
    TransactionAdapter provideTransactionAdapter(@AplicationContext Context context) {
        return new TransactionAdapter(context);
    }
}
