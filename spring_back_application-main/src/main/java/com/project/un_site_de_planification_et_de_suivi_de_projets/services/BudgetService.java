package com.project.un_site_de_planification_et_de_suivi_de_projets.services;

import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.Budget;
import com.project.un_site_de_planification_et_de_suivi_de_projets.exception.UserNotFoundException;
import com.project.un_site_de_planification_et_de_suivi_de_projets.repos.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }
    public Budget addBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public List<Budget> findAllBudgets() {
        return budgetRepository.findAll();
    }

    public Budget updateBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Budget findBudgetById(Long id) {
        return budgetRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Budget by id : " + id + " was not found"));
    }

    public void deleteBudget(Long id){
        budgetRepository.deleteById(id);
    }
}
