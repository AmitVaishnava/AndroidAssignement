package com.assignement.util;

import com.assignement.App;

import java.io.IOException;
import java.io.InputStream;

public class JsonUtil {

    public static String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = App.getInstance().getAssets().open("user.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
