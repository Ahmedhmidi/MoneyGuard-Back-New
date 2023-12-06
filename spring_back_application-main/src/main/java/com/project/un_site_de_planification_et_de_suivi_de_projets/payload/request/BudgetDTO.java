package com.project.un_site_de_planification_et_de_suivi_de_projets.payload.request;

import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.Period;

import java.time.LocalDate;

public class BudgetDTO {

    private Long id;

    private double amount;
    private Period period;
    private String startDate;
    private String creationDate;

    public BudgetDTO(Long id, double amount, Period period, String startDate, String creationDate) {
        this.id = id;
        this.amount = amount;
        this.period = period;
        this.startDate = startDate;
        this.creationDate = creationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
