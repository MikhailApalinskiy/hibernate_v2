package com.dao;

import com.entity.Category;
import org.hibernate.SessionFactory;

public class CategoryDAO extends AbstractDAO<Category> {
    public CategoryDAO(SessionFactory sessionFactory) {
        super(Category.class, sessionFactory);
    }
}
