package com.assignement.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class FragmentHelper {

    public static void replace(FragmentActivity activity, Fragment fragment, int container, String tag) {
        FragmentTransaction fragmentTransaction = activity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(container, fragment, tag).commit();
    }

    public static Fragment get(FragmentActivity activity,String tag){
         return activity.getSupportFragmentManager().findFragmentByTag(tag);
    }
}
