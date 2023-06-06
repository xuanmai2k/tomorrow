package com.example.tomorrow.ddd.brand.application;

import com.example.tomorrow.ddd.brand.Brand;
import com.example.tomorrow.ddd.brand.command.CommandBrand;
import com.example.tomorrow.ddd.brand.repository.IBrandRepository;
import com.example.tomorrow.ddd.product.application.ProductApplication;
import io.micrometer.common.util.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BrandApplication {
    @Autowired
    private IBrandRepository iBrandRepository;
    @Autowired
    private ProductApplication productApplication;
    ;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Brand create(CommandBrand commandBrand) throws Exception {
        if (StringUtils.isBlank(commandBrand.getNameBrand())) {
            throw new Exception("missing params");
        }
        Brand brand = Brand.builder()
                .nameBrand(commandBrand.getNameBrand())
                .idCategory(commandBrand.getIdCategory())
                .descriptionBrand(commandBrand.getDescriptionBrand())
                .createdAt(System.currentTimeMillis())
                .build();
        return iBrandRepository.insert(brand);
    }

    public Brand getById(ObjectId id) {
        Brand brand = mongoTemplate.findById(id, Brand.class);
        if (brand != null) {
            if (brand.getIsDelete()) {
                return null;
            }
            return brand;
        } else {
            return null;
        }
    }

    public Boolean delete(ObjectId id) throws Exception {
        Long current_time = System.currentTimeMillis();
        Brand brand = mongoTemplate.findById(id, Brand.class);
        if (brand != null) {
            Long total = productApplication.countByBrand(brand.get_id().toHexString());
            if (total > 0) {
                throw new Exception("category contains something");
            } else {
                brand.setIsDelete(true);
                brand.setCreatedAt(current_time);
                mongoTemplate.save(brand, "brand");
                return true;
            }
        } else {
            return false;
        }
    }

    public List<Brand> getAll() {
        List<Brand> brands = mongoTemplate.findAll(Brand.class);
        List<Brand> validBrand = new ArrayList<>();
        for (Brand item : brands) {
            if (!item.getIsDelete()) {
                validBrand.add(item);
            }
        }
        return validBrand;
    }

    public Long countByCategory(String hexString) {
        Query query = new Query();
        query.addCriteria(Criteria.where("idCategory").is(hexString));
        query.addCriteria(Criteria.where("isDelete").is(false));
        return mongoTemplate.count(query, Brand.class);
    }

    public Brand update(CommandBrand commandBrand, String id) throws Exception {
        Long current_time = System.currentTimeMillis();
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        query.addCriteria(Criteria.where("isDelete").is(false));
        Brand updateBrand = mongoTemplate.findOne(query, Brand.class);
        if (updateBrand != null){
            if (StringUtils.isBlank(commandBrand.getNameBrand())) {
                throw new Exception("missing params");
            }
            updateBrand.setNameBrand(commandBrand.getNameBrand());
            updateBrand.setIdCategory(commandBrand.getIdCategory());
            updateBrand.setDescriptionBrand(commandBrand.getDescriptionBrand());
            updateBrand.setCreatedAt(current_time);
            return mongoTemplate.save(updateBrand, "brand");
        }else {
            return null;
        }
    }
}
