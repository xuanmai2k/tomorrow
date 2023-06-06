package com.example.tomorrow.ddd.category.controller;

import com.example.tomorrow.ddd.category.Category;
import com.example.tomorrow.ddd.category.application.CategoryApplication;
import com.example.tomorrow.ddd.category.command.CommandCategory;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {
    @Autowired
    private CategoryApplication categoryApplication;
    @PostMapping("/create")
    public Optional<Category> create (HttpServletRequest httpServletRequest, @RequestBody CommandCategory commandCategory){
        try{
            Category result = categoryApplication.create(commandCategory);
            return Optional.of(result);
        } catch(Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/get/{id}")
    public Optional<Category> getById (HttpServletRequest httpServletRequest, @PathVariable ObjectId id){
        try {
            Category result = categoryApplication.getById(id);
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("delete/{id}")
    public Optional<String> delete(HttpServletRequest httpServletRequest, @PathVariable ObjectId id){
        try {
            Boolean is_delete = categoryApplication.delete(id);
            if(is_delete){
                return Optional.of("success");
            }else {
                return Optional.of("fail");
            }
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/getAll")
    public Optional<List<Category>> getAll(HttpServletRequest httpServletRequest){
        try {
            List<Category> result = categoryApplication.getAll();
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @PutMapping("update/{id}")
    public Optional<Category> update(HttpServletRequest httpServletRequest, @RequestBody CommandCategory commandCategory, @PathVariable ObjectId id){
        try {
            Category result = categoryApplication.update(commandCategory, id.toHexString());
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
