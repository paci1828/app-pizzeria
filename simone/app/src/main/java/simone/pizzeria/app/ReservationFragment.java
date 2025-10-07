package simone.pizzeria.app;

import android.Manifest;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import java.util.Calendar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import simone.pizzeria.app.models.Reservation;

public class ReservationFragment extends Fragment {

    private TextInputEditText nameInput;
    private TextInputEditText phoneInput;
    private TextInputEditText guestsInput;
    private TextInputEditText dateInput;
    private TextInputEditText timeInput;
    private TextInputEditText notesInput;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_reservation, container, false);
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameInput = view.findViewById(R.id.input_name);
        phoneInput = view.findViewById(R.id.input_phone);
        guestsInput = view.findViewById(R.id.input_guests);
        dateInput = view.findViewById(R.id.input_date);
        timeInput = view.findViewById(R.id.input_time);
        notesInput = view.findViewById(R.id.input_notes);

        view.findViewById(R.id.button_confirm).setOnClickListener(v -> confirm());

        view.findViewById(R.id.input_date).setOnClickListener(v -> showDatePicker());
        view.findViewById(R.id.input_time).setOnClickListener(v -> showTimePicker());
    }

    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    private void confirm() {
        String name = textOf(nameInput);
        String phone = textOf(phoneInput);
        String guests = textOf(guestsInput);
        String date = textOf(dateInput);
        String time = textOf(timeInput);
        String notes = textOf(notesInput);

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(phone) ||
                TextUtils.isEmpty(guests) || TextUtils.isEmpty(date) ||
                TextUtils.isEmpty(time)) {
            Snackbar.make(requireView(), R.string.reservation_invalid,
                    Snackbar.LENGTH_SHORT).show();
            return;
        }

        // Crea e salva la prenotazione
        Reservation reservation = new Reservation(
                name, phone, Integer.parseInt(guests), date, time, notes
        );
        ReservationManager.addReservation(requireContext(), reservation);

        // Aggiungi punti fedeltà
        LoyaltyManager.addPointsForPizza(requireContext(), "Prenotazione");

        Snackbar.make(requireView(), R.string.reservation_success,
                Snackbar.LENGTH_SHORT).show();
    }

    private String textOf(TextInputEditText editText) {
        return editText.getText() == null ? "" : editText.getText().toString().trim();
    }

    private void showDatePicker() {
        Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(requireContext(), (view, year, month, dayOfMonth) -> {
            String dd = String.format(java.util.Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, month + 1, year);
            dateInput.setText(dd);
        }, y, m, d);
        dialog.show();
    }

    private void showTimePicker() {
        Calendar c = Calendar.getInstance();
        int h = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(requireContext(), (view, hourOfDay, minute) -> {
            String tt = String.format(java.util.Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
            timeInput.setText(tt);
        }, h, min, true);
        dialog.show();
    }
}


