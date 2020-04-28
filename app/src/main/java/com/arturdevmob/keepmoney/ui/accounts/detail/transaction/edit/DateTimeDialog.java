package com.arturdevmob.keepmoney.ui.accounts.detail.transaction.edit;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.arturdevmob.keepmoney.R;

import java.util.Calendar;

public class DateTimeDialog {
    private Context mContext;
    private DateTimeDialog.OnCallback mCallback;
    private Calendar mCalendar;

    interface OnCallback {
        void onCallbackDate(long date);
    }

    public DateTimeDialog(Context context) {
        mContext = context;
        mCalendar = Calendar.getInstance();

        this.showDialogDatePicker();
    }

    public void setCallback(DateTimeDialog.OnCallback callback) {
        mCallback = callback;
    }

    private void showDialogDatePicker() {
        DatePicker datePicker = new DatePicker(mContext);

        AlertDialog.Builder adb = new AlertDialog.Builder(mContext)
                .setView(datePicker)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(R.string.accept, (dialog, which) -> {
                    mCalendar.set(Calendar.YEAR, datePicker.getYear());
                    mCalendar.set(Calendar.MONTH, datePicker.getMonth());
                    mCalendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                    showDialogTimePicker();
                });

        adb.create().show();
    }

    private void showDialogTimePicker() {
        TimePicker timePicker = new TimePicker(mContext);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            mCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            mCalendar.set(Calendar.MINUTE, minute);
        });

        AlertDialog.Builder adb = new AlertDialog.Builder(mContext)
                .setView(timePicker)
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .setPositiveButton(R.string.accept, (dialog, which) -> {
                    mCallback.onCallbackDate(mCalendar.getTimeInMillis());
                });

        adb.create().show();
    }
}
