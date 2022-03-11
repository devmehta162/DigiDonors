package com.dm.digidonors.Utils;

import android.app.Application;

public class Helpers extends Application {
    public static String SHARED_PREF = "shared_preference";

    public static boolean isEmpty(Object object) {
        if (object == null) {
            return true;
        }
        return false;
    }
}
