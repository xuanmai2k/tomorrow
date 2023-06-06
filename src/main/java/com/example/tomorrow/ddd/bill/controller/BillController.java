package com.example.tomorrow.ddd.bill.controller;

import com.example.tomorrow.ddd.bill.model.Bill;
import com.example.tomorrow.ddd.bill.application.BillApplication;
import com.example.tomorrow.ddd.bill.command.CommandBill;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/bill")
public class BillController {
    @Autowired
    private BillApplication billApplication;
    @PostMapping("/create")
        public Optional<Bill> create (HttpServletRequest httpServletRequest, @RequestBody CommandBill commandBill){
        try{
            Bill result = billApplication.create(commandBill);
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/get/{id}")
    public Optional<Bill> getById(HttpServletRequest httpServletRequest, @PathVariable ObjectId id){
        try {
            Bill result = billApplication.getById(id);
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/delete/{id}")
    public Optional<String> delete(HttpServletRequest httpServletRequest, @PathVariable ObjectId id){
        try {
            Boolean is_delete = billApplication.delete(id);
            if(is_delete){
                return Optional.of("success");
            }else{
                return  Optional.of("fail");
            }
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @GetMapping("/getAll")
    public Optional<List<Bill>> getAll(HttpServletRequest httpServletRequest){
        try {
            List<Bill> result = billApplication.getAll();
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @PutMapping("update/{id}")
    public Optional<Bill> update(HttpServletRequest httpServletRequest, @RequestBody CommandBill commandBill, @PathVariable ObjectId id){
        try {
            Bill result = billApplication.update(commandBill, id.toHexString());
            return Optional.of(result);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
