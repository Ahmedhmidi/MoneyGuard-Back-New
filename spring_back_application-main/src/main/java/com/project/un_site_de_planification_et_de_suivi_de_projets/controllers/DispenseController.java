package com.project.un_site_de_planification_et_de_suivi_de_projets.controllers;

import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.Dispense;
import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.User;
import com.project.un_site_de_planification_et_de_suivi_de_projets.services.DispenseService;
import com.project.un_site_de_planification_et_de_suivi_de_projets.services.UserService;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/api/dispense")
public class DispenseController {
    private final DispenseService dispenseService;

    @Autowired
    public DispenseController(DispenseService dispenseService) {
        this.dispenseService = dispenseService;
    }
    @PostMapping("/add")
    @ResponseBody
    public Dispense addNewDispense(@RequestBody Dispense dispense) {
        return dispenseService.addDispense(dispense);
    }


    @GetMapping("/all")
    @ResponseBody
    public List<Dispense> getAllDispenses(){
        return dispenseService.findAllDispenses();
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public Dispense getDispenseById(@PathVariable("id") long id){
        return dispenseService.findDispenseById(id);
    }


    @PutMapping("/update")
    @ResponseBody
    public void updateDispense(@RequestBody Dispense dispense){dispenseService.updateDispense(dispense); }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteDispense(@PathVariable("id") long id){dispenseService.deleteDispense(id); }

    @PostMapping("/add/{sId}")
    @ResponseBody
    public ResponseEntity<?> addNewDispense(@RequestBody Dispense dispense, @PathVariable long sId) {
        // Create a new Dispense object
        Dispense dispenseobj = new Dispense();

        // Find the user by their ID using the userService
        UserService userService = null;
        User user = userService.findUserById(sId);

        // Check if the user exists
        if (user == null) {
            // If the user does not exist, return a bad request response with an error message
            return ResponseEntity.badRequest().body("User not found");
        }

        // Get the list of dispenses associated with the user
        List<Dispense> dispenses = (List<Dispense>) user.getDispenses();

        // Add the new dispense to the list
        dispenses.add(dispense);

        // Update the user's dispenses with the modified list
        user.setDispenses(new HashSet<>(dispenses));

        // Update the user using the userService
        userService.updateUser(user);

        // Set the user for the dispenseobj
        dispenseobj.setUser(user);

        // Update the dispense using the dispenseService
        dispenseService.updateDispense(dispenseobj);

        // Return a successful response with the updated dispenseobj
        return ResponseEntity.ok(dispenseobj);
    }

}
