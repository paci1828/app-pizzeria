package simone.pizzeria4.app.models;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private String id;
    private String name;
    private String description;
    private double price;
    private String emoji;
    private List<Ingredient> ingredients;
    private NutritionalInfo nutritionalInfo;
    private List<String> allergens;
    private boolean isVegan;
    private boolean isVegetarian;
    private boolean isGlutenFree;
    private boolean isSpicy;
    private float rating;
    private int reviewCount;
    private String imageUrl;

    public Pizza(String id, String name, String description, double price, String emoji) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.emoji = emoji;
        this.ingredients = new ArrayList<>();
        this.allergens = new ArrayList<>();
        this.rating = 0.0f;
        this.reviewCount = 0;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        // Auto-detect allergens
        allergens.addAll(ingredient.getAllergens());
        // Auto-detect dietary tags
        updateDietaryTags();
    }

    private void updateDietaryTags() {
        isVegan = true;
        isVegetarian = true;
        isGlutenFree = true;

        for (Ingredient ing : ingredients) {
            if (!ing.isVegan()) isVegan = false;
            if (!ing.isVegetarian()) isVegetarian = false;
            if (ing.containsGluten()) isGlutenFree = false;
        }
    }

    public boolean matchesFilter(DietaryFilter filter) {
        if (filter.isVeganOnly() && !isVegan) return false;
        if (filter.isVegetarianOnly() && !isVegetarian) return false;
        if (filter.isGlutenFreeOnly() && !isGlutenFree) return false;

        // Check excluded allergens
        for (String allergen : filter.getExcludedAllergens()) {
            if (allergens.contains(allergen)) return false;
        }

        return true;
    }

    public String getDietaryTags() {
        List<String> tags = new ArrayList<>();
        if (isVegan) tags.add("🌱 Vegana");
        else if (isVegetarian) tags.add("🥬 Vegetariana");
        if (isGlutenFree) tags.add("🌾 Senza glutine");
        if (isSpicy) tags.add("🌶️ Piccante");
        return String.join(" • ", tags);
    }

    public String getAllergensString() {
        if (allergens.isEmpty()) return "Nessun allergene";
        return "⚠️ " + String.join(", ", allergens);
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getEmoji() { return emoji; }
    public List<Ingredient> getIngredients() { return ingredients; }
    public NutritionalInfo getNutritionalInfo() { return nutritionalInfo; }
    public List<String> getAllergens() { return allergens; }
    public boolean isVegan() { return isVegan; }
    public boolean isVegetarian() { return isVegetarian; }
    public boolean isGlutenFree() { return isGlutenFree; }
    public boolean isSpicy() { return isSpicy; }
    public float getRating() { return rating; }
    public int getReviewCount() { return reviewCount; }
    public String getImageUrl() { return imageUrl; }

    public void setNutritionalInfo(NutritionalInfo info) { this.nutritionalInfo = info; }
    public void setSpicy(boolean spicy) { this.isSpicy = spicy; }
    public void setRating(float rating) { this.rating = rating; }
    public void setReviewCount(int count) { this.reviewCount = count; }
    public void setImageUrl(String url) { this.imageUrl = url; }

    public static class NutritionalInfo {
        private int calories;
        private double proteins; // g
        private double carbs; // g
        private double fats; // g
        private double fiber; // g
        private double sodium; // mg

        public NutritionalInfo(int calories, double proteins, double carbs, double fats, double fiber, double sodium) {
            this.calories = calories;
            this.proteins = proteins;
            this.carbs = carbs;
            this.fats = fats;
            this.fiber = fiber;
            this.sodium = sodium;
        }

        public int getCalories() { return calories; }
        public double getProteins() { return proteins; }
        public double getCarbs() { return carbs; }
        public double getFats() { return fats; }
        public double getFiber() { return fiber; }
        public double getSodium() { return sodium; }
    }

    public static class DietaryFilter {
        private boolean veganOnly;
        private boolean vegetarianOnly;
        private boolean glutenFreeOnly;
        private List<String> excludedAllergens;

        public DietaryFilter() {
            excludedAllergens = new ArrayList<>();
        }

        public boolean isVeganOnly() { return veganOnly; }
        public void setVeganOnly(boolean vegan) { this.veganOnly = vegan; }

        public boolean isVegetarianOnly() { return vegetarianOnly; }
        public void setVegetarianOnly(boolean vegetarian) { this.vegetarianOnly = vegetarian; }

        public boolean isGlutenFreeOnly() { return glutenFreeOnly; }
        public void setGlutenFreeOnly(boolean glutenFree) { this.glutenFreeOnly = glutenFree; }

        public List<String> getExcludedAllergens() { return excludedAllergens; }
        public void addExcludedAllergen(String allergen) { excludedAllergens.add(allergen); }
    }
}