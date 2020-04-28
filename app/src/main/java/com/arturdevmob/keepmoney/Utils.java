package com.arturdevmob.keepmoney;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;

import com.arturdevmob.keepmoney.data.AppDataManager;
import com.arturdevmob.keepmoney.data.DataManager;
import com.arturdevmob.keepmoney.data.database.AppDbHelper;
import com.arturdevmob.keepmoney.data.database.models.CurrencyType;
import com.arturdevmob.keepmoney.data.local.AppLocalHelper;
import com.arturdevmob.keepmoney.data.network.AppNetworkHelper;
import com.arturdevmob.keepmoney.data.preferences.AppPreferencesHelper;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String amountFormat(double amount, CurrencyType currencyType) {
        String strAmount = String.format("%.2f %s", amount, currencyType.toString());

        return strAmount;
    }

    public static String amountFormatNoCurrencyType(double amount) {
        String strAmount = String.format("%.2f", amount);

        return strAmount;
    }

    public static double amountParseFromString(String amountStr) {
        String parseAmountStr = amountStr.replace(",", ".");

        return Double.parseDouble(parseAmountStr);
    }

    public static SimpleDateFormat getDatePattern() {
        return new SimpleDateFormat("dd MMM yyyy г. в HH:mm");
    }

    public static String dateLongFormatForString(long time) {
        SimpleDateFormat dateFormat = getDatePattern();

        return dateFormat.format(new Date(time));
    }

    public static long dateLongParseFromString(String dateStr) {
        SimpleDateFormat dateFormat = getDatePattern();
        Date date = new Date();

        try {
            date.setTime(dateFormat.parse(dateStr).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date.getTime();
    }

    public static boolean clearData(Context context) {
        DataManager dataManager = new AppDataManager(new AppDbHelper(context), new AppPreferencesHelper(context), new AppNetworkHelper(), new AppLocalHelper(context));
        dataManager.deleteAllData();

        return true;
    }

    public static Drawable getDrawableFromAssets(Context context, String path) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(path);
            Drawable drawable = Drawable.createFromStream(inputStream, null);

            return drawable;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
