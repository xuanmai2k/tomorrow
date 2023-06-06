package com.example.tomorrow.ddd.brand.controller;

import com.example.tomorrow.ddd.brand.Brand;
import com.example.tomorrow.ddd.brand.application.BrandApplication;
import com.example.tomorrow.ddd.brand.command.CommandBrand;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandApplication brandApplication;
    @PostMapping("/create")
    public Optional<Brand> create (HttpServletRequest httpServletRequest, @RequestBody CommandBrand commandBrand){
        try{
            Brand result = brandApplication.create(commandBrand);
            return Optional.of(result);
        }catch(Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/get/{id}")
    public Optional<Brand> getById (HttpServletRequest httpServletRequest, @PathVariable ObjectId id){
        try {
            Brand result = brandApplication.getById(id);
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/delete/{id}")
    public Optional<String> delete (HttpServletRequest httpServletRequest, @PathVariable ObjectId id){
        try{
            Boolean is_delete = brandApplication.delete(id);
            if(is_delete){
                return Optional.of("success");
            }else{
                return Optional.of("fail");
            }
        } catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/getAll")
    public Optional<List<Brand>> getAll(HttpServletRequest httpServletRequest){
        try {
            List<Brand> result = brandApplication.getAll();
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @PutMapping("update/{id}")
    public Optional<Brand> update(HttpServletRequest httpServletRequest, @RequestBody CommandBrand commandBrand, @PathVariable ObjectId id){
        try {
            Brand result = brandApplication.update(commandBrand, id.toHexString());
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
