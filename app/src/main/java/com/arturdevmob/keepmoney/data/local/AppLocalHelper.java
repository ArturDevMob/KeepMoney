package com.arturdevmob.keepmoney.data.local;

import android.content.Context;
import android.content.res.AssetManager;

import com.arturdevmob.keepmoney.di.AplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AppLocalHelper implements LocalHelper {
    private Context mContext;

    @Inject
    public AppLocalHelper(@AplicationContext Context context) {
        mContext = context;
    }

    @Override
    public List<String> getPathsToImagesCategory() {
        String pathAssets = "file:///android_asset";
        String directoryImages = "category_img";
        List<String> list = new ArrayList<>();

        AssetManager assetManager = mContext.getAssets();

        try {
            for (String fileImage : assetManager.list(directoryImages)) {
                String imageFilePath = String.format("%s/%s/%s", pathAssets, directoryImages, fileImage);

                list.add(imageFilePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
