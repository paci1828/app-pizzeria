package simone.pizzeria.app.models;

import java.util.Date;

public class Review {
    private String id;
    private String pizzaId;
    private String userId;
    private String userName;
    private float rating; // 1-5
    private String comment;
    private Date date;
    private String imageUrl;
    private int helpfulCount;
    private boolean isVerifiedPurchase;

    public Review(String pizzaId, String userId, String userName, float rating, String comment) {
        this.id = "review_" + System.currentTimeMillis();
        this.pizzaId = pizzaId;
        this.userId = userId;
        this.userName = userName;
        this.rating = rating;
        this.comment = comment;
        this.date = new Date();
        this.helpfulCount = 0;
        this.isVerifiedPurchase = false;
    }

    public String getRatingEmoji() {
        if (rating >= 4.5f) return "😍";
        if (rating >= 3.5f) return "😊";
        if (rating >= 2.5f) return "😐";
        if (rating >= 1.5f) return "😕";
        return "😞";
    }

    public String getTimeAgo() {
        long diff = new Date().getTime() - date.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;

        if (days > 0) return days + " giorni fa";
        if (hours > 0) return hours + " ore fa";
        if (minutes > 0) return minutes + " minuti fa";
        return "Appena ora";
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getPizzaId() { return pizzaId; }
    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public float getRating() { return rating; }
    public String getComment() { return comment; }
    public Date getDate() { return date; }
    public String getImageUrl() { return imageUrl; }
    public int getHelpfulCount() { return helpfulCount; }
    public boolean isVerifiedPurchase() { return isVerifiedPurchase; }

    public void setImageUrl(String url) { this.imageUrl = url; }
    public void incrementHelpfulCount() { this.helpfulCount++; }
    public void setVerifiedPurchase(boolean verified) { this.isVerifiedPurchase = verified; }
}