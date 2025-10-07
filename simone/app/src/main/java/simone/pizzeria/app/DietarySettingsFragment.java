package simone.pizzeria.app;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.HashSet;
import java.util.Set;


public class DietarySettingsFragment extends Fragment {

    private SwitchMaterial switchVegan;
    private SwitchMaterial switchVegetarian;
    private ChipGroup chipGroupAllergens;
    private Set<String> selectedAllergens;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dietary_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        switchVegan = view.findViewById(R.id.switch_vegan);
        switchVegetarian = view.findViewById(R.id.switch_vegetarian);
        SwitchMaterial switchGlutenFree = view.findViewById(R.id.switch_gluten_free);
        chipGroupAllergens = view.findViewById(R.id.chip_group_allergens);

        selectedAllergens = new HashSet<>(DietaryPreferencesHelper.getExcludedAllergens(requireContext()));

        // Load saved preferences
        switchVegan.setChecked(DietaryPreferencesHelper.isVegan(requireContext()));
        switchVegetarian.setChecked(DietaryPreferencesHelper.isVegetarian(requireContext()));
        switchGlutenFree.setChecked(DietaryPreferencesHelper.isGlutenFree(requireContext()));

        // Setup allergen chips
        setupAllergenChips();

        // Save preferences on change
        switchVegan.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DietaryPreferencesHelper.setVegan(requireContext(), isChecked);
            if (isChecked) {
                switchVegetarian.setChecked(true);
            }
            showSavedMessage();
        });

        switchVegetarian.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DietaryPreferencesHelper.setVegetarian(requireContext(), isChecked);
            if (!isChecked) {
                switchVegan.setChecked(false);
            }
            showSavedMessage();
        });

        switchGlutenFree.setOnCheckedChangeListener((buttonView, isChecked) -> {
            DietaryPreferencesHelper.setGlutenFree(requireContext(), isChecked);
            showSavedMessage();
        });

        view.findViewById(R.id.btn_save).setOnClickListener(v -> {
            DietaryPreferencesHelper.setExcludedAllergens(requireContext(), selectedAllergens);
            Toast.makeText(requireContext(), "✅ Preferenze salvate!", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupAllergenChips() {
        String[] allergens = {
                "Lattosio", "Glutine", "Frutta a guscio", "Arachidi",
                "Soia", "Pesce", "Crostacei", "Uova", "Sedano", "Senape"
        };

        for (String allergen : allergens) {
            Chip chip = new Chip(requireContext());
            chip.setText(allergen);
            chip.setCheckable(true);
            chip.setChecked(selectedAllergens.contains(allergen));

            chip.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedAllergens.add(allergen);
                } else {
                    selectedAllergens.remove(allergen);
                }
            });

            chipGroupAllergens.addView(chip);
        }
    }

    private void showSavedMessage() {
        Toast.makeText(requireContext(), "Preferenza salvata", Toast.LENGTH_SHORT).show();
    }
}