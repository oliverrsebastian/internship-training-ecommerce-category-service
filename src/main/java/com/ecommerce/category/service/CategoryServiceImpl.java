package com.ecommerce.category.service;

import com.ecommerce.category.model.Category;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private List<Category> categories;

    public CategoryServiceImpl() {
        categories = setData();
    }

    private ArrayList setData() {
        ArrayList categories = new ArrayList();
        categories.add(new Category("CAT1", "Category 1"));
        categories.add(new Category("CAT2", "Category 2"));
        categories.add(new Category("CAT3", "Category 3"));
        categories.add(new Category("CAT4", "Category 4"));
        categories.add(new Category("CAT5", "Category 5"));
        return categories;
    }

    @Override
    public Category getById(String id) {
        if (id != null && id.startsWith("CAT"))
            for (Category category : categories)
                if (category.getId().equals(id))
                    return category;
        return null;
    }

    @Override
    public List<Category> getAll() {
        return categories;
    }

    @Override
    public Category insertCategory(Category category) {
        categories.add(category);
        return category;
    }

    @Override
    public Category updateCategory(Category category) {
        if (category != null) {
            Category actual = this.getById(category.getId());
            if (actual != null)
                BeanUtils.copyProperties(category, actual);
            categories.add(actual);
            return category;
        }
        return null;
    }

    @Override
    public Category deleteById(String id) {
        if (id != null && id.startsWith("CAT"))
            for (Category category : categories) {
                if (category.getId().equals(id)) {
                    categories.remove(category);
                    return category;
                }
            }
        return null;
    }
}
