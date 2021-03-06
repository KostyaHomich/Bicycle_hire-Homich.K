package epam.project.database.dao;

import epam.project.database.dao.exception.DaoException;


import java.io.Serializable;
import java.util.List;

public interface EntityDao<T extends Identified<PK>, PK extends Serializable> {


    T persist(T object) throws DaoException;

    T getByPK(PK key) throws DaoException;

    void update(T object) throws DaoException;

    void delete(T object) throws DaoException;

    List<T> getAll() throws DaoException;
}
