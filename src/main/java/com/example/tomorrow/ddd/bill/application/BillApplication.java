package com.example.tomorrow.ddd.bill.application;

import com.example.tomorrow.ddd.bill.model.Bill;
import com.example.tomorrow.ddd.bill.command.CommandBill;
import com.example.tomorrow.ddd.bill.repository.IBillRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BillApplication {
    @Autowired
    private IBillRepository iBillRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Bill create(CommandBill commandBill) throws Exception {
        if (commandBill.getBillingDetail() == null) {
            throw new Exception("missing params");
        }
        Bill bill = Bill.builder()
                .idUser(commandBill.getIdUser())
                .billingDetail(commandBill.getBillingDetail())
                .createdAt(System.currentTimeMillis())
                .build();
        return iBillRepository.insert(bill);
    }

    public Bill getById(ObjectId id) {
        Bill bill = mongoTemplate.findById(id, Bill.class);
        if (bill != null) {
            if (bill.getIsDelete()) {
                return null;
            }
            return bill;
        } else {
            return null;
        }
    }

    public Boolean delete(ObjectId id) {
        Long current_time = System.currentTimeMillis();
        Bill bill = mongoTemplate.findById(id, Bill.class);
        if (bill != null) {
            bill.setIsDelete(true);
            bill.setCreatedAt(current_time);
            mongoTemplate.save(bill, "bill");
            return true;
        } else {
            return false;
        }
    }

    public List<Bill> getAll() {
        List<Bill> bills = mongoTemplate.findAll(Bill.class);
        List<Bill> validBill = new ArrayList<>();
        for (Bill item : bills) {
            if (!item.getIsDelete()) {
                validBill.add(item);
            }
        }
        return validBill;
    }

    public Bill update(CommandBill commandBill, String id) throws Exception {
        Long current_time = System.currentTimeMillis();
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        query.addCriteria(Criteria.where("isDelete").is(false));
        Bill updateBill = mongoTemplate.findOne(query, Bill.class);
        if (updateBill != null) {
            if (commandBill.getBillingDetail() == null) {
                throw new Exception("missing params");
            }
            updateBill.setIdUser(commandBill.getIdUser());
            updateBill.setBillingDetail(commandBill.getBillingDetail());
            updateBill.setCreatedAt(current_time);
            return mongoTemplate.save(updateBill,"Bill");
        }
        return null;
    }
}
