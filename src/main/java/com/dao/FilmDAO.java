package com.dao;

import com.entity.Film;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class FilmDAO extends AbstractDAO<Film> {
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getFirstAvailableFilmForRent() {
        Query<Film> query = getCurrentSession().createQuery("from Film f where f.filmId not in (select i.filmId.filmId from Inventory i )", Film.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
