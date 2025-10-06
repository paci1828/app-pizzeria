package simone.pizzeria4.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.UUID;

import simone.pizzeria4.app.models.LoyaltyCard;

public class LoyaltyManager {
    private static final String PREFS_NAME = "loyalty_prefs";
    private static final String KEY_LOYALTY_CARD = "loyalty_card";
    private static final String KEY_USER_ID = "user_id";

    public static LoyaltyCard getLoyaltyCard(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_LOYALTY_CARD, null);

        if (json != null) {
            try {
                return new Gson().fromJson(json, LoyaltyCard.class);
            } catch (Exception e) {
                 e.printStackTrace();
            }
        }

        // Create new card
        String userId = prefs.getString(KEY_USER_ID, null);
        if (userId == null) {
            userId = UUID.randomUUID().toString();
            prefs.edit().putString(KEY_USER_ID, userId).apply();
        }
        LoyaltyCard card = new LoyaltyCard(userId);
        saveLoyaltyCard(context, card);
        return card;
    }

    public static void saveLoyaltyCard(Context context, LoyaltyCard card) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = new Gson().toJson(card);
        prefs.edit().putString(KEY_LOYALTY_CARD, json).apply();
    }

    @SuppressLint("MissingPermission")
    public static void addPointsForPizza(Context context, String pizzaName) {
        LoyaltyCard card = getLoyaltyCard(context);
        card.addPoints(1, pizzaName);
        saveLoyaltyCard(context, card);

        // Trigger notification if reward reached
        if (card.getPoints() % 10 == 0) {
            NotificationHelper.notify(context,
                    2,
                    "🎉 Premio raggiunto!",
                    "Hai accumulato " + card.getPoints() + " punti! Riscatta una pizza gratis!");
        }
    }
}