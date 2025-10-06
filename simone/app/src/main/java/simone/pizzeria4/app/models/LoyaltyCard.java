package simone.pizzeria4.app.models;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class LoyaltyCard {
    private String userId;
    private int points;
    private int totalPizzasOrdered;
    private Date createdAt;
    private Date lastUpdated;
    private List<Transaction> transactions;
    private int tier; // 0=Bronze, 1=Silver, 2=Gold, 3=Platinum

    public LoyaltyCard(String userId) {
        this.userId = userId;
        this.points = 0;
        this.totalPizzasOrdered = 0;
        this.createdAt = new Date();
        this.lastUpdated = new Date();
        this.transactions = new ArrayList<>();
        this.tier = 0;
    }

    public void addPoints(int points, String description) {
        this.points += points;
        this.totalPizzasOrdered++;
        this.lastUpdated = new Date();
        transactions.add(new Transaction(points, description, new Date()));
        updateTier();
    }

    public boolean redeemReward(int pointsCost) {
        if (points >= pointsCost) {
            points -= pointsCost;
            lastUpdated = new Date();
            transactions.add(new Transaction(-pointsCost, "Reward Redeemed", new Date()));
            return true;
        }
        return false;
    }

    private void updateTier() {
        if (totalPizzasOrdered >= 50) tier = 3; // Platinum
        else if (totalPizzasOrdered >= 30) tier = 2; // Gold
        else if (totalPizzasOrdered >= 15) tier = 1; // Silver
        else tier = 0; // Bronze
    }

    public String getTierName() {
        switch (tier) {
            case 3: return "Platinum";
            case 2: return "Gold";
            case 1: return "Silver";
            default: return "Bronze";
        }
    }

    public String getTierEmoji() {
        switch (tier) {
            case 3: return "💎";
            case 2: return "🏆";
            case 1: return "🥈";
            default: return "🥉";
        }
    }

    public int getPointsToNextReward() {
        return 10 - (points % 10);
    }

    public int getProgressPercentage() {
        return (points % 10) * 10;
    }

    // Getters
    public String getUserId() { return userId; }
    public int getPoints() { return points; }
    public int getTotalPizzasOrdered() { return totalPizzasOrdered; }
    public Date getCreatedAt() { return createdAt; }
    public Date getLastUpdated() { return lastUpdated; }
    public List<Transaction> getTransactions() { return transactions; }
    public int getTier() { return tier; }

    public static class Transaction {
        private int points;
        private String description;
        private Date date;

        public Transaction(int points, String description, Date date) {
            this.points = points;
            this.description = description;
            this.date = date;
        }

        public int getPoints() { return points; }
        public String getDescription() { return description; }
        public Date getDate() { return date; }
    }
}