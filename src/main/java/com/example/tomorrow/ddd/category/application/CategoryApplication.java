package com.example.tomorrow.ddd.category.application;

import com.example.tomorrow.ddd.brand.application.BrandApplication;
import com.example.tomorrow.ddd.category.Category;
import com.example.tomorrow.ddd.category.command.CommandCategory;
import com.example.tomorrow.ddd.category.repository.ICategoryRepository;
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
public class CategoryApplication  {
    @Autowired
    private ICategoryRepository iCategoryRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private BrandApplication brandApplication;

    public Category create (CommandCategory commandCategory) throws Exception{
        if (StringUtils.isBlank(commandCategory.getNameCategory())){
            throw new Exception("missing params");
        }
        Category category = Category.builder()
                .nameCategory(commandCategory.getNameCategory())
                .descriptionCategory(commandCategory.getDescriptionCategory())
                .createdAt(System.currentTimeMillis())
                .build();
        return iCategoryRepository.insert(category);
    }

    public Category getById (ObjectId id){
        Category category = mongoTemplate.findById(id, Category.class);
        if(category != null){
            if(category.getIsDelete()){
                return null;
            }
            return category;
        }else{
            return null;
        }
    }

    public Boolean delete(ObjectId id) throws Exception {
        Long current_time = System.currentTimeMillis();
        Category category = mongoTemplate.findById(id, Category.class);
        if(category != null){
            Long total = brandApplication.countByCategory(category.get_id().toHexString());
            if(total > 0){
                throw new Exception("category contains something");
            } else {
                category.setIsDelete(true);
                category.setCreatedAt(current_time);
                mongoTemplate.save(category, "category");
                return true;
            }
        }else {
            return false;
        }
    }

    public List<Category> getAll(){
        List<Category> categories = mongoTemplate.findAll(Category.class);
        List<Category> validCategory = new ArrayList<>();
        for(Category item : categories){
            if(!item.getIsDelete()){
                validCategory.add(item);
            }
        }
        return validCategory;
    }
    public Category update (CommandCategory commandCategory, String id) throws Exception{
        Long current_time = System.currentTimeMillis();
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));
        query.addCriteria(Criteria.where("isDelete").is(false));
        Category updateCategory = mongoTemplate.findOne(query, Category.class);
        if(updateCategory != null){
            if (StringUtils.isBlank(commandCategory.getNameCategory())){
                throw new Exception("missing params");
            }
            updateCategory.setNameCategory(commandCategory.getNameCategory());
            updateCategory.setDescriptionCategory(commandCategory.getDescriptionCategory());
            updateCategory.setCreatedAt(current_time);
            return mongoTemplate.save(updateCategory, "category");
        }else{
            return null;
        }
    }
}
