package simone.pizzeria.app.models;

import java.util.ArrayList;
import java.util.List;

public class Ingredient {
    private String id;
    private String name;
    private String emoji;
    private double price; // extra cost
    private boolean isVegan;
    private boolean isVegetarian;
    private boolean containsGluten;
    private List<String> allergens;
    private IngredientCategory category;

    public Ingredient(String id, String name, String emoji, double price, IngredientCategory category) {
        this.id = id;
        this.name = name;
        this.emoji = emoji;
        this.price = price;
        this.category = category;
        this.allergens = new ArrayList<>();
        this.isVegetarian = true; // Default
    }

    public void setDietaryInfo(boolean vegan, boolean vegetarian, boolean gluten) {
        this.isVegan = vegan;
        this.isVegetarian = vegetarian;
        this.containsGluten = gluten;
    }

    public void addAllergen(String allergen) {
        allergens.add(allergen);
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmoji() { return emoji; }
    public double getPrice() { return price; }
    public boolean isVegan() { return isVegan; }
    public boolean isVegetarian() { return isVegetarian; }
    public boolean containsGluten() { return containsGluten; }
    public List<String> getAllergens() { return allergens; }
    public IngredientCategory getCategory() { return category; }

    public enum IngredientCategory {
        BASE("Base"),
        CHEESE("Formaggi"),
        MEAT("Carne"),
        VEGETABLES("Verdure"),
        SEAFOOD("Mare"),
        EXTRAS("Extra");

        private final String displayName;

        IngredientCategory(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Static factory methods for common ingredients
    public static Ingredient createMozzarella() {
        Ingredient ing = new Ingredient("mozzarella", "Mozzarella", "🧀", 0, IngredientCategory.CHEESE);
        ing.setDietaryInfo(false, true, false);
        ing.addAllergen("Lattosio");
        return ing;
    }

    public static Ingredient createTomato() {
        Ingredient ing = new Ingredient("tomato", "Pomodoro", "🍅", 0, IngredientCategory.BASE);
        ing.setDietaryInfo(true, true, false);
        return ing;
    }

    public static Ingredient createBasil() {
        Ingredient ing = new Ingredient("basil", "Basilico", "🌿", 0, IngredientCategory.VEGETABLES);
        ing.setDietaryInfo(true, true, false);
        return ing;
    }

    public static Ingredient createSalami() {
        Ingredient ing = new Ingredient("salami", "Salame piccante", "🌶️", 1.5, IngredientCategory.MEAT);
        ing.setDietaryInfo(false, false, false);
        return ing;
    }

    public static Ingredient createMushrooms() {
        Ingredient ing = new Ingredient("mushrooms", "Funghi", "🍄", 1.0, IngredientCategory.VEGETABLES);
        ing.setDietaryInfo(true, true, false);
        return ing;
    }

    public static Ingredient createOlives() {
        Ingredient ing = new Ingredient("olives", "Olive", "🫒", 0.5, IngredientCategory.VEGETABLES);
        ing.setDietaryInfo(true, true, false);
        return ing;
    }

    public static Ingredient createGorgonzola() {
        Ingredient ing = new Ingredient("gorgonzola", "Gorgonzola", "🧀", 1.5, IngredientCategory.CHEESE);
        ing.setDietaryInfo(false, true, false);
        ing.addAllergen("Lattosio");
        return ing;
    }

    public static Ingredient createHam() {
        Ingredient ing = new Ingredient("ham", "Prosciutto cotto", "🥓", 1.5, IngredientCategory.MEAT);
        ing.setDietaryInfo(false, false, false);
        return ing;
    }

    public static Ingredient createArtichokes() {
        Ingredient ing = new Ingredient("artichokes", "Carciofi", "🌱", 1.0, IngredientCategory.VEGETABLES);
        ing.setDietaryInfo(true, true, false);
        return ing;
    }

    public static List<Ingredient> getAllAvailableIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        ingredients.add(createMozzarella());
        ingredients.add(createTomato());
        ingredients.add(createBasil());
        ingredients.add(createSalami());
        ingredients.add(createMushrooms());
        ingredients.add(createOlives());
        ingredients.add(createGorgonzola());
        ingredients.add(createHam());
        ingredients.add(createArtichokes());

        // Add more ingredients as needed
        ingredients.add(new Ingredient("peppers", "Peperoni", "🫑", 0.8, IngredientCategory.VEGETABLES));
        ingredients.add(new Ingredient("onions", "Cipolle", "🧅", 0.5, IngredientCategory.VEGETABLES));
        ingredients.add(new Ingredient("sausage", "Salsiccia", "🌭", 2.0, IngredientCategory.MEAT));
        ingredients.add(new Ingredient("tuna", "Tonno", "🐟", 2.0, IngredientCategory.SEAFOOD));
        ingredients.add(new Ingredient("anchovies", "Acciughe", "🐟", 1.5, IngredientCategory.SEAFOOD));
        ingredients.add(new Ingredient("egg", "Uovo", "🥚", 0.8, IngredientCategory.EXTRAS));
        ingredients.add(new Ingredient("parmesan", "Parmigiano", "🧀", 1.0, IngredientCategory.CHEESE));
        ingredients.add(new Ingredient("arugula", "Rucola", "🥬", 0.5, IngredientCategory.VEGETABLES));

        return ingredients;
    }
}