package simone.pizzeria4.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import simone.pizzeria4.app.database.PizzaDatabase;
import simone.pizzeria4.app.models.Pizza;
import simone.pizzeria4.app.PizzasAdapter;


public class PizzasFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pizzas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ChipGroup chipGroup = view.findViewById(R.id.chip_group_filters);
        Chip chipVegan = view.findViewById(R.id.chip_vegan);
        Chip chipVegetarian = view.findViewById(R.id.chip_vegetarian);
        Chip chipGlutenFree = view.findViewById(R.id.chip_gluten_free);

        RecyclerView rv = view.findViewById(R.id.rv_pizzas);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));
        List<Pizza> pizzas = PizzaDatabase.getDefaultPizzas();
        PizzasAdapter adapter = new PizzasAdapter(pizzas);
        rv.setAdapter(adapter);

        // Carica preferenze salvate
        chipVegan.setChecked(DietaryPreferencesHelper.isVegan(requireContext()));
        chipVegetarian.setChecked(DietaryPreferencesHelper.isVegetarian(requireContext()));
        chipGlutenFree.setChecked(DietaryPreferencesHelper.isGlutenFree(requireContext()));

        // Filtra le pizze quando cambiano i filtri
        chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            boolean vegan = chipVegan.isChecked();
            boolean vegetarian = chipVegetarian.isChecked();
            boolean gf = chipGlutenFree.isChecked();
            adapter.applyFilter(vegan, vegetarian, gf);
        });
    }

    private void filterPizzas(boolean vegan, boolean vegetarian, boolean glutenFree) {
        // Deprecated: now handled by PizzasAdapter.applyFilter
    }
}



