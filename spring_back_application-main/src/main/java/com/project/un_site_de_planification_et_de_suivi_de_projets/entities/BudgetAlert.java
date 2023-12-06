package com.project.un_site_de_planification_et_de_suivi_de_projets.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "budget_alerts")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BudgetAlert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double budgetThreshold;
    private LocalDateTime activationDate;
    private NotificationEnum method;
    private Notification_Status status;
    private String message;

    @JsonIgnore
    @OneToOne(mappedBy = "image")
    Category category;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BudgetAlert(Long id, double budgetThreshold, LocalDateTime activationDate, NotificationEnum method, Notification_Status status, String message) {
        this.id = id;
        this.budgetThreshold = budgetThreshold;
        this.activationDate = activationDate;
        this.method = method;
        this.status = status;
        this.message = message;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getBudgetThreshold() {
        return budgetThreshold;
    }

    public void setBudgetThreshold(double budgetThreshold) {
        this.budgetThreshold = budgetThreshold;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public NotificationEnum getMethod() {
        return method;
    }

    public void setMethod(NotificationEnum method) {
        this.method = method;
    }

    public Notification_Status getStatus() {
        return status;
    }

    public void setStatus(Notification_Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
