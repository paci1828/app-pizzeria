package simone.pizzeria4.app.models;

import java.util.Date;

public class Reservation {
    private String id;
    private String name;
    private String phone;
    private int guests;
    private String date;
    private String time;
    private String notes;
    private Date createdAt;
    private ReservationStatus status;

    public Reservation(String name, String phone, int guests, String date, String time, String notes) {
        this.id = "res_" + System.currentTimeMillis();
        this.name = name;
        this.phone = phone;
        this.guests = guests;
        this.date = date;
        this.time = time;
        this.notes = notes;
        this.createdAt = new Date();
        this.status = ReservationStatus.PENDING;
    }

    public String getStatusEmoji() {
        switch (status) {
            case CONFIRMED: return "✅";
            case CANCELLED: return "❌";
            case COMPLETED: return "🎉";
            default: return "⏳";
        }
    }

    public String getStatusText() {
        switch (status) {
            case CONFIRMED: return "Confermata";
            case CANCELLED: return "Cancellata";
            case COMPLETED: return "Completata";
            default: return "In attesa";
        }
    }

    public boolean canModify() {
        return status == ReservationStatus.PENDING || status == ReservationStatus.CONFIRMED;
    }

    public boolean canCancel() {
        return status == ReservationStatus.PENDING || status == ReservationStatus.CONFIRMED;
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getPhone() { return phone; }
    public int getGuests() { return guests; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getNotes() { return notes; }
    public Date getCreatedAt() { return createdAt; }
    public ReservationStatus getStatus() { return status; }

    public void setName(String name) { this.name = name; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setGuests(int guests) { this.guests = guests; }
    public void setDate(String date) { this.date = date; }
    public void setTime(String time) { this.time = time; }
    public void setNotes(String notes) { this.notes = notes; }
    public void setStatus(ReservationStatus status) { this.status = status; }

    public enum ReservationStatus {
        PENDING,
        CONFIRMED,
        CANCELLED,
        COMPLETED
    }
}