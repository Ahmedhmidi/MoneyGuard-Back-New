package com.project.un_site_de_planification_et_de_suivi_de_projets.controllers;

import com.project.un_site_de_planification_et_de_suivi_de_projets.entities.Category;
import com.project.un_site_de_planification_et_de_suivi_de_projets.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categorie")
public class CategoryController {
    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
    @PostMapping("/add")
    @ResponseBody
    public Category addNewCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }


    @GetMapping("/all")
    @ResponseBody
    public List<Category> getAllCategories(){
        return categoryService.findAllCategories();
    }


    @GetMapping("/id/{id}")
    @ResponseBody
    public Category getCategoryById(@PathVariable("id") long id){
        return categoryService.findCategoryById(id);
    }


    @PutMapping("/update")
    @ResponseBody
    public void updateCategory(@RequestBody Category category){categoryService.updateCategory(category); }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public void deleteCategory(@PathVariable("id") long id){categoryService.deleteCategory(id); }
}
