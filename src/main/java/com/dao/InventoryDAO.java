package com.dao;

import com.entity.Inventory;
import org.hibernate.SessionFactory;

public class InventoryDAO extends AbstractDAO<Inventory> {
    public InventoryDAO(SessionFactory sessionFactory) {
        super(Inventory.class, sessionFactory);
    }
}
