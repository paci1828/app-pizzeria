package simone.pizzeria.app.notifiche;

import android.annotation.SuppressLint;
import android.content.Context;

import simone.pizzeria.app.NotificationHelper;
import simone.pizzeria.app.PreferencesHelper;
import simone.pizzeria.app.models.Pizza;


public class NewPizzaNotifier {

    @SuppressLint("MissingPermission")
    public static void notifyNewPizza(Context context, Pizza pizza) {
        if (!PreferencesHelper.areNotificationsEnabled(context)) {
            return;
        }

        NotificationHelper.notify(context,
                10, // notification ID
                "🍕 Nuova pizza disponibile!",
                pizza.getName() + " - " + pizza.getDescription());
    }

    public static void scheduleWeeklySpecial(Context context) {
        // Implementa con WorkManager per notifiche settimanali
        // delle pizze speciali
    }
}