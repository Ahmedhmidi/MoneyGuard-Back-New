package com.project.un_site_de_planification_et_de_suivi_de_projets.controllers;

import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.BudgetAlert;
import com.project.un_site_de_planification_et_de_suivi_de_projets.services.BudgetAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget/alert")
public class BudgetAlertController {
    private final BudgetAlertService budgetAlertService;

    @Autowired
    public BudgetAlertController(BudgetAlertService budgetAlertService) {
        this.budgetAlertService = budgetAlertService;
    }
    @PostMapping("/add")
    @ResponseBody
    public BudgetAlert addNewBudgetAlert(@RequestBody BudgetAlert budgetAlert) {
        return budgetAlertService.addBudgetAlert(budgetAlert);
    }


    @GetMapping("/all")
    @ResponseBody
    public List<BudgetAlert> getAllBudgetAlerts(){
        return budgetAlertService.findAllBudgetAlerts();
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public BudgetAlert getBudgetAlertById(@PathVariable("id") long id){
        return budgetAlertService.findBudgetAlertById(id);
    }


    @PutMapping("/update")
    @ResponseBody
    public void updateBudgetAlert(@RequestBody BudgetAlert budgetAlert){budgetAlertService.updateBudgetAlert(budgetAlert); }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteBudgetAlert(@PathVariable("id") long id){budgetAlertService.deleteBudgetAlert(id); }
}
