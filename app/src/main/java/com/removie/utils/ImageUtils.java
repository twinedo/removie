package com.removie.utils;

import android.net.Uri;
import com.removie.api.service.ServiceConfig;

/*
Created by twin on May 11, 2019
*/

public class ImageUtils {
    public static Uri movieUrl(String size, String posterPath) {
        posterPath = posterPath.replace("/", "");
        return Uri.parse(ServiceConfig.BASE_IMAGE_URL).buildUpon()
                .appendPath(size)
                .appendPath(posterPath)
                .build();
    }

    public static Uri profileUrl(String size, String profilePath) {
        profilePath = profilePath.replace("/", "");
        return Uri.parse(ServiceConfig.BASE_IMAGE_URL).buildUpon()
                .appendPath(size)
                .appendPath(profilePath)
                .build();
    }
}