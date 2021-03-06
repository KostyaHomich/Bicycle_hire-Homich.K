package epam.project.database.dao;

import epam.project.database.dao.exception.DaoException;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.List;

public abstract class AbstractJdbcDao<T extends Identified<PK>, PK extends Number> implements EntityDao<T, PK> {

    private static Logger LOGGER = Logger.getLogger(AbstractJdbcDao.class.getName());

    protected Connection connection;

    protected abstract List<T> parseResultSet(ResultSet rs) throws SQLException, DaoException;

    protected abstract void prepareStatementForInsert(PreparedStatement statement, T object) throws SQLException;

    protected abstract void prepareStatementForUpdate(PreparedStatement statement, T object) throws SQLException;

    public abstract String getSelectQuery();

    public abstract String getCreateQuery();

    public abstract String getUpdateQuery();

    public abstract String getDeleteQuery();


    @Override
    @AutoConnection
    public T getByPK(PK key) throws DaoException {

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(getSelectQuery() + " WHERE id = " + key)) {
            ResultSet entityResultSet=preparedStatement.executeQuery();
            List<T> entities = parseResultSet(entityResultSet);
            if(entities.stream().findFirst().isPresent()) {
                return entities.get(0);
            }
            else {
                throw new DaoException("Failed to get by pk entity.");
            }
        } catch (SQLException e) {
            throw new DaoException("Failed to get by pk entity.",e);
        }
    }

    @Override
    @AutoConnection
    public List<T> getAll() throws DaoException {
        List<T> list;
        String sql = getSelectQuery();
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            list = parseResultSet(rs);
        } catch (SQLException  e) {
            throw new DaoException("Failed to get all entity's.",e);
        }
        return list;
    }

    @Override
    @AutoConnection
    public T persist(T object) throws DaoException {
        try (PreparedStatement insertStatement = this.connection.prepareStatement(getCreateQuery(), Statement.RETURN_GENERATED_KEYS)) {
            prepareStatementForInsert(insertStatement, object);
            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                object.setId(generatedKeys.getInt(1));
                return object;
            } else {
                throw new SQLException("Creating user failed, no ID obtained.");
            }

        } catch (SQLException e) {
            throw new DaoException("Failed to insert entity.", e);
        }
    }

    @Override
    @AutoConnection
    public void update(T object) throws DaoException {
        try (PreparedStatement statement = this.connection.prepareStatement(getUpdateQuery())) {
            prepareStatementForUpdate(statement, object);
            statement.executeUpdate();
        } catch (Exception e) {
            throw new DaoException("Cannot update entity.",e);
        }

    }

    @Override
    @AutoConnection
    public void delete(T object) throws DaoException {
        String sql = getDeleteQuery();
        try (PreparedStatement statement = this.connection.prepareStatement(sql)) {
            statement.setObject(1, object.getId());
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Cannot delete entity.", e);
        }

    }
}