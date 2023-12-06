package com.project.un_site_de_planification_et_de_suivi_de_projets.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categorys")
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @OneToOne
    Image image;

    @OneToOne
    BudgetAlert budgetAlert;

    public Category(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @OneToMany(mappedBy="category")
    private Set<Dispense> dispenses;

    public Set<Dispense> getDispenses() {
        return dispenses;
    }

    public void setDispenses(Set<Dispense> dispenses) {
        this.dispenses = dispenses;
    }

    public BudgetAlert getBudgetAlert() {
        return budgetAlert;
    }

    public void setBudgetAlert(BudgetAlert budgetAlert) {
        this.budgetAlert = budgetAlert;
    }
}
