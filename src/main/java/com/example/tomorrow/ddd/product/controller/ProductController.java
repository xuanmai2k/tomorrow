package com.example.tomorrow.ddd.product.controller;

import com.example.tomorrow.ddd.product.Product;
import com.example.tomorrow.ddd.product.application.ProductApplication;
import com.example.tomorrow.ddd.product.command.CommandProduct;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductApplication productApplication;
    @PostMapping("/create")
    public Optional<Product> create (HttpServletRequest httpServletRequest, @RequestBody CommandProduct commandProduct){
        try {
            Product result = productApplication.create(commandProduct);
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/get/{id}")
    public Optional<Product> getById (HttpServletRequest httpServletRequest, @PathVariable ObjectId id){
        try {
            Product result = productApplication.getById(id);
            return Optional.of(result);
        } catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/delete/{id}")
    public Optional<String> delete (HttpServletRequest httpServletRequest, @PathVariable ObjectId id){
        try {
            Boolean is_delete = productApplication.delete(id);
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
    public Optional<List<Product>> getAll (HttpServletRequest httpServletRequest){
        try {
            List<Product> result = productApplication.getAll();
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @PutMapping("/update/{id}")
    public Optional<Product> update(HttpServletRequest httpServletRequest, @RequestBody CommandProduct commandProduct, @PathVariable ObjectId id){
        try {
            Product result = productApplication.update(commandProduct, id.toHexString());
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @PostMapping("/search")
    public Optional<List<Product>> search (HttpServletRequest httpServletRequest,@RequestBody CommandProduct commandProduct, @RequestParam("page") Integer page,@RequestParam("size") Integer size){
        try {
            Page<Product> result = productApplication.search(commandProduct, page, size);
            List<Product> list = result.getContent();
            return Optional.of(list);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
