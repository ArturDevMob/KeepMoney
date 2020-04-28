package com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit;

import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.data.database.models.AccountModels;
import com.arturdevmob.keepmoney.data.database.models.CategoryModels;
import com.arturdevmob.keepmoney.data.database.models.CurrencyType;
import com.arturdevmob.keepmoney.data.database.models.TransactionModels;
import com.arturdevmob.keepmoney.data.database.models.TransactionType;
import com.arturdevmob.keepmoney.ui.base.BasePresenter;

import javax.inject.Inject;

public class TransactionEditPresenter<V extends TransactionEditMvpView> extends BasePresenter<V> implements TransactionEditMvpPresenter<V> {
    @Inject
    public TransactionEditPresenter(DataManager dataManager) {
        super(dataManager);
    }

    @Override
    public void loadTransaction(long transactionId) {
        TransactionModels transactionModels = getDataManager().getTransactionById(transactionId);

        getView().fillFieldsEdit(transactionModels);
    }

    @Override
    public void addTransaction(boolean isEditMode) {
        TransactionModels transactionModels = getView().getDataTransaction();

        AccountModels accountModels = getDataManager().getAccountById(transactionModels.getAccountId());
        transactionModels.setAccountModels(accountModels);

        if (transactionModels.getCategoryId() > 0) {
            CategoryModels categoryModels = getDataManager().getCategoryById(transactionModels.getCategoryId());
            transactionModels.setCategoryModels(categoryModels);
        }

        if (! verificationsTransaction(transactionModels)) {
            return;
        }

        if (isEditMode) {
            getDataManager().updateTransaction(transactionModels);
        } else {
            getDataManager().insertTransaction(transactionModels);
        }

        getView().finishActivity();
    }

    @Override
    public void selectingDateTime() {
        getView().showDialogChoiceDateTime();
    }

    @Override
    public void loadCurrencyType(long accountId) {
        AccountModels accountModels = getDataManager().getAccountById(accountId);
        CurrencyType currencyType = accountModels.getCurrencyType();

        getView().setCurrencyTypeTextView(currencyType.name());
    }

    @Override
    public void selectingCategory(boolean isExpenseCategory) {
        getView().startActivityChoiceCategory(isExpenseCategory);
    }

    @Override
    public void selectingCategory(boolean isExpenseCategory, long categoryId) {
        getView().startActivityChangeCategory(isExpenseCategory, categoryId);
    }

    @Override
    public void selectedCategory(long categoryId) {
        CategoryModels categoryModels = getDataManager().getCategoryById(categoryId);

        getView().setCategoryIdInField(categoryModels.getId());
        getView().showCategoryTitleTextView(categoryModels.getTitle());
    }

    @Override
    public void deselectedCategory() {
        getView().setCategoryIdInField(0);
        getView().showNotCategoryTitleTextView();
    }

    private boolean verificationsTransaction(TransactionModels transactionModels) {
        if (transactionModels.getTitle() == null || transactionModels.getTitle().isEmpty()) {
            getView().showSnackBar("Заполните поле заголовок!");
            return false;
        }

        if (transactionModels.getAmount() == 0) {
            getView().showSnackBar("Введите сумму транзакции!");
            return false;
        }

        if (transactionModels.getCategoryModels() != null && transactionModels.getTransactionType() == TransactionType.EXPENSE && ! transactionModels.getCategoryModels().isExpense()) {
            getView().showSnackBar("Транзакция расхода, не может относиться к категории дохода!");
            return false;
        }

        if (transactionModels.getCategoryModels() != null && transactionModels.getTransactionType() == TransactionType.INCOME && transactionModels.getCategoryModels().isExpense()) {
            getView().showSnackBar("Транзакция дохода, не может относиться к категории расхода!");
            return false;
        }

        return true;
    }
}
