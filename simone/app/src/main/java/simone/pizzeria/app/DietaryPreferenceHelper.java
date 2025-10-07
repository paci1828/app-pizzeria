package simone.pizzeria.app;

import android.content.Context;

import java.util.HashSet;
import java.util.Set;

class DietaryPreferencesHelper {
    private static final String PREFS_NAME = "dietary_prefs";
    private static final String KEY_IS_VEGAN = "is_vegan";
    private static final String KEY_IS_VEGETARIAN = "is_vegetarian";
    private static final String KEY_IS_GLUTEN_FREE = "is_gluten_free";
    private static final String KEY_EXCLUDED_ALLERGENS = "excluded_allergens";
    private static final String KEY_USER_NAME = "user_name";

    public static void setVegan(Context context, boolean vegan) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putBoolean(KEY_IS_VEGAN, vegan).apply();
    }

    public static boolean isVegan(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_IS_VEGAN, false);
    }

    public static void setVegetarian(Context context, boolean vegetarian) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putBoolean(KEY_IS_VEGETARIAN, vegetarian).apply();
    }

    public static boolean isVegetarian(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_IS_VEGETARIAN, false);
    }

    public static void setGlutenFree(Context context, boolean glutenFree) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putBoolean(KEY_IS_GLUTEN_FREE, glutenFree).apply();
    }

    public static boolean isGlutenFree(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getBoolean(KEY_IS_GLUTEN_FREE, false);
    }

    public static void setExcludedAllergens(Context context, Set<String> allergens) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putStringSet(KEY_EXCLUDED_ALLERGENS, allergens).apply();
    }

    public static Set<String> getExcludedAllergens(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getStringSet(KEY_EXCLUDED_ALLERGENS, new HashSet<>());
    }

    public static void setUserName(Context context, String name) {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .edit().putString(KEY_USER_NAME, name).apply();
    }

    public static String getUserName(Context context) {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
                .getString(KEY_USER_NAME, "Utente");
    }
}