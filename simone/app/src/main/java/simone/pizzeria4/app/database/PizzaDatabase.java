package simone.pizzeria4.app.database;

import java.util.ArrayList;
import java.util.List;

import simone.pizzeria4.app.models.Ingredient;
import simone.pizzeria4.app.models.Pizza;

public class PizzaDatabase {

    public static List<Pizza> getDefaultPizzas() {
        List<Pizza> pizzas = new ArrayList<>();

        // Margherita
        Pizza margherita = new Pizza("margherita", "Margherita",
                "Pomodoro, mozzarella, basilico", 6.50, "🍅");
        margherita.addIngredient(Ingredient.createTomato());
        margherita.addIngredient(Ingredient.createMozzarella());
        margherita.addIngredient(Ingredient.createBasil());
        margherita.setNutritionalInfo(new Pizza.NutritionalInfo(
                800, 35, 100, 25, 5, 1200));
        pizzas.add(margherita);

        // Diavola
        Pizza diavola = new Pizza("diavola", "Diavola",
                "Pomodoro, mozzarella, salame piccante", 8.00, "🌶️");
        diavola.addIngredient(Ingredient.createTomato());
        diavola.addIngredient(Ingredient.createMozzarella());
        diavola.addIngredient(Ingredient.createSalami());
        diavola.setSpicy(true);
        diavola.setNutritionalInfo(new Pizza.NutritionalInfo(
                950, 42, 95, 35, 4, 1500));
        pizzas.add(diavola);

        // Quattro Formaggi
        Pizza formaggi = new Pizza("4formaggi", "Quattro Formaggi",
                "Mozzarella, gorgonzola, fontina, parmigiano", 9.00, "🧀");
        formaggi.addIngredient(Ingredient.createMozzarella());
        formaggi.addIngredient(Ingredient.createGorgonzola());
        formaggi.setNutritionalInfo(new Pizza.NutritionalInfo(
                1050, 48, 92, 45, 3, 1800));
        pizzas.add(formaggi);

        // Marinara (Vegana)
        Pizza marinara = new Pizza("marinara", "Marinara",
                "Pomodoro, aglio, origano, olio EVO", 5.50, "🧄");
        marinara.addIngredient(Ingredient.createTomato());
        marinara.setNutritionalInfo(new Pizza.NutritionalInfo(
                650, 20, 110, 15, 6, 900));
        pizzas.add(marinara);

        // Capricciosa
        Pizza capricciosa = new Pizza("capricciosa", "Capricciosa",
                "Pomodoro, mozzarella, prosciutto, funghi, carciofi, olive", 9.50, "🍄");
        capricciosa.addIngredient(Ingredient.createTomato());
        capricciosa.addIngredient(Ingredient.createMozzarella());
        capricciosa.addIngredient(Ingredient.createHam());
        capricciosa.addIngredient(Ingredient.createMushrooms());
        capricciosa.addIngredient(Ingredient.createArtichokes());
        capricciosa.addIngredient(Ingredient.createOlives());
        capricciosa.setNutritionalInfo(new Pizza.NutritionalInfo(
                980, 45, 98, 32, 7, 1600));
        pizzas.add(capricciosa);

        return pizzas;
    }
}