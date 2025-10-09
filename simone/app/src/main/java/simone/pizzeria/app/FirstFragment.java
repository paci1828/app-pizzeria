package simone.pizzeria.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_buttons);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        List<MenuButton> buttons = getMenuButtons();
        MenuButtonAdapter adapter = new MenuButtonAdapter(buttons, this);
        recyclerView.setAdapter(adapter);
    }

    private List<MenuButton> getMenuButtons() {
        List<MenuButton> buttons = new ArrayList<>();
        buttons.add(new MenuButton("📋", "Prenota", R.id.action_FirstFragment_to_ReservationFragment));
        buttons.add(new MenuButton("🍕", "Menu", R.id.action_FirstFragment_to_PizzasFragment));
        buttons.add(new MenuButton("🖼️", "Galleria", R.id.action_FirstFragment_to_GalleryFragment));
        buttons.add(new MenuButton("☎️", "Contatti", R.id.action_FirstFragment_to_ContactsFragment));
        buttons.add(new MenuButton("⭐", "Fidelity", R.id.action_FirstFragment_to_LoyaltyFragment));
        buttons.add(new MenuButton("✨", "Crea Pizza", R.id.action_FirstFragment_to_PizzaBuilderFragment));
        buttons.add(new MenuButton("📅", "Le mie prenotazioni", R.id.action_FirstFragment_to_MyReservationsFragment));
        return buttons;
    }

    public void navigateTo(int actionId) {
        NavHostFragment.findNavController(FirstFragment.this).navigate(actionId);
    }
}

// MenuButton.java - Modello dati
class MenuButton {
    public String icon;
    public String label;
    public int actionId;

    public MenuButton(String icon, String label, int actionId) {
        this.icon = icon;
        this.label = label;
        this.actionId = actionId;
    }
}

// MenuButtonAdapter.java
class MenuButtonAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<MenuButtonAdapter.ViewHolder> {
    private final List<MenuButton> buttons;
    private final FirstFragment fragment;

    public MenuButtonAdapter(List<MenuButton> buttons, FirstFragment fragment) {
        this.buttons = buttons;
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        android.widget.FrameLayout card = new android.widget.FrameLayout(parent.getContext());
        card.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                400
        ));
        return new ViewHolder(card);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(buttons.get(position));
    }

    @Override
    public int getItemCount() {
        return buttons.size();
    }

    class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final android.widget.FrameLayout card;

        ViewHolder(android.widget.FrameLayout itemView) {
            super(itemView);
            this.card = itemView;
        }

        void bind(MenuButton button) {
            card.removeAllViews();

            android.widget.LinearLayout container = new android.widget.LinearLayout(card.getContext());
            container.setLayoutParams(new android.widget.FrameLayout.LayoutParams(
                    android.widget.FrameLayout.LayoutParams.MATCH_PARENT,
                    android.widget.FrameLayout.LayoutParams.MATCH_PARENT
            ));
            container.setOrientation(android.widget.LinearLayout.VERTICAL);
            container.setGravity(android.view.Gravity.CENTER);
            container.setBackgroundColor(getButtonColor(getBindingAdapterPosition()));

            android.widget.TextView iconView = new android.widget.TextView(card.getContext());
            iconView.setText(button.icon);
            iconView.setTextSize(32);
            iconView.setGravity(android.view.Gravity.CENTER);

            android.widget.TextView labelView = new android.widget.TextView(card.getContext());
            labelView.setText(button.label);
            labelView.setTextColor(android.graphics.Color.WHITE);
            labelView.setTextSize(14);
            labelView.setTypeface(android.graphics.Typeface.defaultFromStyle(android.graphics.Typeface.BOLD));
            labelView.setGravity(android.view.Gravity.CENTER);
            labelView.setPadding(0, 16, 0, 0);

            container.addView(iconView);
            container.addView(labelView);
            card.addView(container);

            card.setOnClickListener(v -> fragment.navigateTo(button.actionId));
        }

        private int getButtonColor(int position) {
            int[] colors = {
                    0xFFFF6B6B, // Rosso
                    0xFFFFA726, // Arancione
                    0xFF66BB6A, // Verde
                    0xFF42A5F5, // Blu
                    0xFFAB47BC, // Viola
                    0xFFEC407A, // Rosa
                    0xFF26C6DA  // Ciano
            };
            return colors[position % colors.length];
        }
    }
}