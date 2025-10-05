package simone.pizzeria4.myapplicationnotification;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesHelper {

    private static final String PREFS = "app_prefs";
    private static final String KEY_NOTIFICATIONS_ENABLED = "notifications_enabled";

    public static boolean areNotificationsEnabled(Context context) {
        SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        return sp.getBoolean(KEY_NOTIFICATIONS_ENABLED, true);
    }

    public static void setNotificationsEnabled(Context context, boolean enabled) {
        SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
        sp.edit().putBoolean(KEY_NOTIFICATIONS_ENABLED, enabled).apply();
    }
}



