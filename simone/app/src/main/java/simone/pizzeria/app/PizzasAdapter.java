package simone.pizzeria.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import simone.pizzeria.app.models.Pizza;


public class PizzasAdapter extends RecyclerView.Adapter<PizzasAdapter.ViewHolder> {
    private final List<Pizza> allPizzas;
    private final List<Pizza> visiblePizzas;

    public PizzasAdapter(List<Pizza> pizzas) {
        this.allPizzas = new ArrayList<>(pizzas);
        this.visiblePizzas = new ArrayList<>(pizzas);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pizza, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pizza p = visiblePizzas.get(position);
        holder.tvName.setText(p.getName());
        holder.tvDesc.setText(p.getDescription());
        holder.tvEmoji.setText(p.getEmoji());
        holder.tvPrice.setText(String.format("€%.2f", p.getPrice()));
    }

    @Override
    public int getItemCount() {
        return visiblePizzas.size();
    }

    public void applyFilter(boolean vegan, boolean vegetarian, boolean glutenFree) {
        visiblePizzas.clear();
        for (Pizza p : allPizzas) {
            Pizza.DietaryFilter f = new Pizza.DietaryFilter();
            f.setVeganOnly(vegan);
            f.setVegetarianOnly(vegetarian);
            f.setGlutenFreeOnly(glutenFree);
            if (p.matchesFilter(f)) visiblePizzas.add(p);
        }
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmoji, tvName, tvDesc, tvPrice;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvEmoji = itemView.findViewById(R.id.tv_pizza_icon);
            tvName = itemView.findViewById(R.id.tv_pizza_name);
            tvDesc = itemView.findViewById(R.id.tv_pizza_description);
            tvPrice = itemView.findViewById(R.id.tv_pizza_price);
        }
    }
}
