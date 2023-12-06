package com.project.un_site_de_planification_et_de_suivi_de_projets.services;

import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.BudgetAlert;
import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.Category;
import com.project.un_site_de_planification_et_de_suivi_de_projets.exception.UserNotFoundException;
import com.project.un_site_de_planification_et_de_suivi_de_projets.repos.BudgetAlertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BudgetAlertService {
    private  final  BudgetAlertRepository budgetAlertRepository;
    @Autowired
    public BudgetAlertService(BudgetAlertRepository budgetAlertRepository) {
        this.budgetAlertRepository = budgetAlertRepository;
    }
    public BudgetAlert addBudgetAlert(BudgetAlert budgetAlert) {
        return budgetAlertRepository.save(budgetAlert);
    }

    public List<BudgetAlert> findAllBudgetAlerts() {
        return budgetAlertRepository.findAll();
    }

    public BudgetAlert updateBudgetAlert(BudgetAlert budgetAlert) {
        return budgetAlertRepository.save(budgetAlert);
    }

    public BudgetAlert findBudgetAlertById(Long id) {
        return budgetAlertRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("BudgetAlert by id : " + id + " was not found"));
    }

    public void deleteBudgetAlert(Long id){
        budgetAlertRepository.deleteById(id);
    }
}
