package simone.pizzeria.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import simone.pizzeria.app.models.Ingredient;
import simone.pizzeria.app.models.Pizza;

public class PizzaBuilderFragment extends Fragment {

    private List<Ingredient> selectedIngredients;
    private List<Ingredient> availableIngredients;
    private TextView tvTotalPrice, tvIngredientCount;
    private RecyclerView rvIngredients;
    private ChipGroup chipGroupSelected;
    private double basePrice = 5.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pizza_builder, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        selectedIngredients = new ArrayList<>();
        availableIngredients = Ingredient.getAllAvailableIngredients();

        tvTotalPrice = view.findViewById(R.id.tv_total_price);
        tvIngredientCount = view.findViewById(R.id.tv_ingredient_count);
        chipGroupSelected = view.findViewById(R.id.chip_group_selected);
        rvIngredients = view.findViewById(R.id.rv_ingredients);

        setupRecyclerView();
        updatePriceAndCount();

        view.findViewById(R.id.btn_save_pizza).setOnClickListener(v -> savePizza());
        view.findViewById(R.id.btn_share_pizza).setOnClickListener(v -> sharePizza());
        view.findViewById(R.id.btn_add_to_cart).setOnClickListener(v -> addToCart());
    }

    private void setupRecyclerView() {
        rvIngredients.setLayoutManager(new GridLayoutManager(requireContext(), 3));
        IngredientsAdapter adapter = new IngredientsAdapter(availableIngredients, ingredient -> {
            if (selectedIngredients.contains(ingredient)) {
                selectedIngredients.remove(ingredient);
            } else {
                selectedIngredients.add(ingredient);
            }
            updateSelectedChips();
            updatePriceAndCount();
        });
        rvIngredients.setAdapter(adapter);
    }

    private void updateSelectedChips() {
        chipGroupSelected.removeAllViews();
        for (Ingredient ingredient : selectedIngredients) {
            Chip chip = new Chip(requireContext());
            chip.setText(ingredient.getEmoji() + " " + ingredient.getName());
            chip.setCloseIconVisible(true);
            chip.setOnCloseIconClickListener(v -> {
                selectedIngredients.remove(ingredient);
                updateSelectedChips();
                updatePriceAndCount();
            });
            chipGroupSelected.addView(chip);
        }
    }

    private void updatePriceAndCount() {
        double totalPrice = basePrice;
        for (Ingredient ingredient : selectedIngredients) {
            totalPrice += ingredient.getPrice();
        }
        tvTotalPrice.setText(String.format("€%.2f", totalPrice));
        tvIngredientCount.setText(selectedIngredients.size() + " ingredienti selezionati");
    }

    private void savePizza() {
        if (selectedIngredients.isEmpty()) {
            Toast.makeText(requireContext(), "Aggiungi almeno un ingrediente!", Toast.LENGTH_SHORT).show();
            return;
        }

        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_save_pizza, null);
        TextInputEditText etPizzaName = dialogView.findViewById(R.id.et_pizza_name);

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Salva la tua pizza")
                .setView(dialogView)
                .setPositiveButton("Salva", (dialog, which) -> {
                    String name = Objects.requireNonNull(etPizzaName.getText()).toString().trim();
                    if (name.isEmpty()) {
                        name = "La mia pizza personalizzata";
                    }

                    Pizza customPizza = createCustomPizza(name);
                   CustomPizzaManager.saveCustomPizza(requireContext(), customPizza);
                    Toast.makeText(requireContext(), "Pizza salvata nei preferiti!", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Annulla", null)
                .show();
    }

    private void sharePizza() {
        if (selectedIngredients.isEmpty()) {
            Toast.makeText(requireContext(), "Aggiungi almeno un ingrediente!", Toast.LENGTH_SHORT).show();
            return;
        }

        Pizza customPizza = createCustomPizza("La mia pizza");
        String shareText = "🍕 Ho creato la mia pizza su Pizzeria 4!\n\n" +
                "Ingredienti: " + getIngredientsText() + "\n" +
                "Prezzo: €" + String.format("%.2f", calculateTotalPrice()) + "\n\n" +
                "Scarica l'app per creare la tua!";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        startActivity(Intent.createChooser(shareIntent, "Condividi la tua pizza"));
    }

    private void addToCart() {
        if (selectedIngredients.isEmpty()) {
            Toast.makeText(requireContext(), "Aggiungi almeno un ingrediente!", Toast.LENGTH_SHORT).show();
            return;
        }

        Pizza customPizza = createCustomPizza("Pizza personalizzata");
        // Add to cart logic here
        Toast.makeText(requireContext(), "Pizza aggiunta al carrello!", Toast.LENGTH_SHORT).show();
    }

    private Pizza createCustomPizza(String name) {
        double totalPrice = calculateTotalPrice();
        Pizza pizza = new Pizza("custom_" + System.currentTimeMillis(),
                name,
                getIngredientsText(),
                totalPrice,
                "🎨");

        for (Ingredient ingredient : selectedIngredients) {
            pizza.addIngredient(ingredient);
        }

        return pizza;
    }

    private String getIngredientsText() {
        List<String> names = new ArrayList<>();
        for (Ingredient ing : selectedIngredients) {
            names.add(ing.getName());
        }
        return String.join(", ", names);
    }

    private double calculateTotalPrice() {
        double total = basePrice;
        for (Ingredient ingredient : selectedIngredients) {
            total += ingredient.getPrice();
        }
        return total;
    }

    private static class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
        private final List<Ingredient> ingredients;
        private final OnIngredientClickListener listener;

        interface OnIngredientClickListener {
            void onIngredientClick(Ingredient ingredient);
        }

        IngredientsAdapter(List<Ingredient> ingredients, OnIngredientClickListener listener) {
            this.ingredients = ingredients;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ingredient, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Ingredient ingredient = ingredients.get(position);
            holder.tvEmoji.setText(ingredient.getEmoji());
            holder.tvName.setText(ingredient.getName());

            if (ingredient.getPrice() > 0) {
                holder.tvPrice.setText("+€" + String.format("%.2f", ingredient.getPrice()));
                holder.tvPrice.setVisibility(View.VISIBLE);
            } else {
                holder.tvPrice.setVisibility(View.GONE);
            }

            holder.itemView.setOnClickListener(v -> listener.onIngredientClick(ingredient));
        }

        @Override
        public int getItemCount() {
            return ingredients.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvEmoji, tvName, tvPrice;

            ViewHolder(View view) {
                super(view);
                tvEmoji = view.findViewById(R.id.tv_emoji);
                tvName = view.findViewById(R.id.tv_name);
                tvPrice = view.findViewById(R.id.tv_price);
            }
        }
    }
}