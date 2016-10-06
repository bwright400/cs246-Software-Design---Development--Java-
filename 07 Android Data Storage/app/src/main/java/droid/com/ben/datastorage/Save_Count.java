package droid.com.ben.datastorage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ben on 6/18/2016.
 */
public class Save_Count {
    private SharedPreferences sharedPreferences;
    private int data;
    public final String MyPREFERENCES = "MyPrefs";

    public Save_Count(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public Save_Count(SharedPreferences sharedPreferences, int num) {
        this.sharedPreferences = sharedPreferences;
        data = num;

    }
    public void saveData() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String key = "Saved key" + data;
        editor.putInt(key, data);
        editor.commit();

    }

    public void getData() {

    }

}
