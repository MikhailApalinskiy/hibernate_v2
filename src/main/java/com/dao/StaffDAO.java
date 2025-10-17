package com.dao;

import com.entity.Staff;
import org.hibernate.SessionFactory;

public class StaffDAO extends AbstractDAO<Staff> {
    public StaffDAO(SessionFactory sessionFactory) {
        super(Staff.class, sessionFactory);
    }
}
