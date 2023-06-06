package com.example.tomorrow.ddd.product.application;

import com.example.tomorrow.ddd.product.Product;
import com.example.tomorrow.ddd.product.command.CommandProduct;
import com.example.tomorrow.ddd.product.repository.IProductRepository;
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
public class ProductApplication {
    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public Product create(CommandProduct commandProduct) throws Exception {
        if (StringUtils.isBlank(commandProduct.getNameProduct())
                || StringUtils.isBlank(commandProduct.getIdCategory())
                || StringUtils.isBlank(commandProduct.getIdBrand())
                || commandProduct.getPriceProduct() == null
                || commandProduct.getNumInventory() == null) {
            throw new Exception("missing params");
        }
        Product product = Product.builder()
                .nameProduct(commandProduct.getNameProduct())
                .priceProduct(commandProduct.getPriceProduct())
                .idCategory(commandProduct.getIdCategory())
                .idBrand(commandProduct.getIdBrand())
                .descriptionProduct(commandProduct.getDescriptionProduct())
                .urlImageProduct(commandProduct.getUrlImageProduct())
                .numInventory(commandProduct.getNumInventory())
                .createdAt(System.currentTimeMillis())
                .build();
        return iProductRepository.insert(product);
    }

    public Product getById(Object id) {
        Product product = mongoTemplate.findById(id, Product.class);
        if (product != null) {
            if (product.getIsDelete()) {
                return null;
            }
            return product;
        } else {
            return null;
        }
    }

    public Boolean delete(ObjectId id){
        Long current_time = System.currentTimeMillis();
        Product product = mongoTemplate.findById(id, Product.class);
        if(product != null){
            product.setIsDelete(true);
            product.setCreatedAt(current_time);
            mongoTemplate.save(product, "product");
            return true;
        }else{
            return false;
        }
    }

    public List<Product> getAll(){
        List<Product> products = mongoTemplate.findAll(Product.class);
        List<Product> validProduct = new ArrayList<>();
        for (Product item : products){
            if(!item.getIsDelete()){
                validProduct.add(item);
            }
        }
        return validProduct;
    }

    public Long countByBrand(String hexString) {
        Query query = new Query();
        query.addCriteria(Criteria.where("idBrand").is(hexString));//where cột, is giá trị
        return mongoTemplate.count(query, Product.class);
    }

    public Product update(CommandProduct commandProduct, String id) throws Exception{
        Long current_time = System.currentTimeMillis();
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        query.addCriteria(Criteria.where("isDelete").is(false));
        Product updateProduct = mongoTemplate.findOne(query, Product.class);
        if(updateProduct != null){
            if (StringUtils.isBlank(commandProduct.getNameProduct())
                    || StringUtils.isBlank(commandProduct.getIdCategory())
                    || StringUtils.isBlank(commandProduct.getIdBrand())
                    || commandProduct.getPriceProduct() == null
                    || commandProduct.getNumInventory() == null) {
                throw new Exception("missing params");
            }
            updateProduct.setNameProduct(commandProduct.getNameProduct());
            updateProduct.setPriceProduct(commandProduct.getPriceProduct());
            updateProduct.setIdCategory(commandProduct.getIdCategory());
            updateProduct.setIdBrand(commandProduct.getIdBrand());
            updateProduct.setDescriptionProduct(commandProduct.getDescriptionProduct());
            updateProduct.setUrlImageProduct(commandProduct.getUrlImageProduct());
            updateProduct.setNumInventory(commandProduct.getNumInventory());
            updateProduct.setCreatedAt(current_time);
            return mongoTemplate.save(updateProduct,"product");
        }else {
            return null;
        }
    }
}
