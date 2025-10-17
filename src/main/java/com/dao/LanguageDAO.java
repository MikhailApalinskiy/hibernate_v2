package com.dao;

import com.entity.Language;
import org.hibernate.SessionFactory;

public class LanguageDAO extends AbstractDAO<Language> {
    public LanguageDAO(SessionFactory sessionFactory) {
        super(Language.class, sessionFactory);
    }
}
