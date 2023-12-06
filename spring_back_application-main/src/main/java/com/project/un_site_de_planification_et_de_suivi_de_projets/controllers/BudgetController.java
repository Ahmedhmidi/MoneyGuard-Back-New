package com.project.un_site_de_planification_et_de_suivi_de_projets.controllers;

import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.Budget;
import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.User;
import com.project.un_site_de_planification_et_de_suivi_de_projets.payload.request.BudgetDTO;
import com.project.un_site_de_planification_et_de_suivi_de_projets.services.BudgetService;
import com.project.un_site_de_planification_et_de_suivi_de_projets.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService ;

    @Autowired
    public BudgetController(BudgetService budgetService, UserService userService) {

        this.budgetService = budgetService;
        this.userService = userService;
    }
    @PostMapping("/add")
    @ResponseBody
    public Budget addNewBudget(@RequestBody Budget budget) {
        return budgetService.addBudget(budget);
    }


    @GetMapping("/all")
    @ResponseBody
    public List<Budget> getAllBudgets(){
        return budgetService.findAllBudgets();
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public Budget getBudgetById(@PathVariable("id") long id){
        return budgetService.findBudgetById(id);
    }


    @GetMapping("/id/{id}/userId")
    @ResponseBody
    public ResponseEntity<?> getBudgetByUserId(@PathVariable("id") long id){
        User user = userService.findUserById(id);
        if (user == null){
            return ResponseEntity.badRequest().body("User not found with this id ");
        }
        if (user.getBudget() == null){
            return ResponseEntity.badRequest().body("The current user doesn't have any budget yet");
        }
        return ResponseEntity.ok(user.getBudget());
    }


    @PutMapping("/update")
    @ResponseBody
    public void updateBudget(@RequestBody Budget budget){budgetService.updateBudget(budget); }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteBudget(@PathVariable("id") long id){budgetService.deleteBudget(id); }


    @PostMapping("/add/{userId}")
    @ResponseBody
    public  ResponseEntity<?> addBudgetByUserId(@RequestBody BudgetDTO budget, @PathVariable long userId){
        User user = userService.findUserById(userId);
        Budget budgetobj = new Budget(budget.getId(),
                budget.getAmount(),
                budget.getPeriod(),
                LocalDate.parse(budget.getStartDate()).atStartOfDay(),
                LocalDate.parse(budget.getCreationDate()).atStartOfDay());

        budgetobj.setUser(user);
        Budget newBudget = budgetService.addBudget(budgetobj);
        user.setBudget(newBudget);
        user = userService.updateUser(user);
        if (user != null) {
            return ResponseEntity.ok("Budget added successfully");
        }
        else {
            return ResponseEntity.badRequest().body("Error occurred during the addition of the budget. Please check.");
        }
    }
}

