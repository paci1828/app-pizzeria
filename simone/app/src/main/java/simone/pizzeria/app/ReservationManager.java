package simone.pizzeria.app;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.RequiresPermission;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import simone.pizzeria.app.models.Reservation;

public class ReservationManager {
    private static final String PREFS_NAME = "reservations_prefs";
    private static final String KEY_RESERVATIONS = "all_reservations";

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    public static void addReservation(Context context, Reservation reservation) {
        List<Reservation> reservations = getAllReservations(context);
        reservations.add(reservation);
        saveReservations(context, reservations);

        // Send confirmation notification
        NotificationHelper.notify(context,
                4,
                "✅ Prenotazione confermata!",
                "Tavolo per " + reservation.getGuests() + " il " +
                        reservation.getDate() + " alle " + reservation.getTime());
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    public static void updateReservation(Context context, Reservation reservation) {
        List<Reservation> reservations = getAllReservations(context);
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId().equals(reservation.getId())) {
                reservations.set(i, reservation);
                break;
            }
        }
        saveReservations(context, reservations);

        NotificationHelper.notify(context,
                5,
                "📝 Prenotazione modificata",
                "Le modifiche sono state salvate");
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    public static void cancelReservation(Context context, String reservationId) {
        List<Reservation> reservations = getAllReservations(context);
        for (Reservation reservation : reservations) {
            if (reservation.getId().equals(reservationId)) {
                reservation.setStatus(Reservation.ReservationStatus.CANCELLED);
                break;
            }
        }
        saveReservations(context, reservations);

        NotificationHelper.notify(context,
                6,
                "❌ Prenotazione cancellata",
                "La tua prenotazione è stata cancellata con successo");
    }

    public static List<Reservation> getAllReservations(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = prefs.getString(KEY_RESERVATIONS, null);

        if (json != null) {
            Type type = new TypeToken<List<Reservation>>(){}.getType();
            return new Gson().fromJson(json, type);
        }
        return new ArrayList<>();
    }

    public static List<Reservation> getActiveReservations(Context context) {
        List<Reservation> all = getAllReservations(context);
        List<Reservation> active = new ArrayList<>();
        for (Reservation r : all) {
            if (r.getStatus() != Reservation.ReservationStatus.CANCELLED &&
                    r.getStatus() != Reservation.ReservationStatus.COMPLETED) {
                active.add(r);
            }
        }
        return active;
    }

    public static Reservation getReservationById(Context context, String id) {
        List<Reservation> reservations = getAllReservations(context);
        for (Reservation r : reservations) {
            if (r.getId().equals(id)) {
                return r;
            }
        }
        return null;
    }

    private static void saveReservations(Context context, List<Reservation> reservations) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String json = new Gson().toJson(reservations);
        prefs.edit().putString(KEY_RESERVATIONS, json).apply();
    }
}