package epam.project.database.pool;

import epam.project.database.dao.exception.ConnectionPoolException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class ConnectionPoolImpl implements ConnectionPool {

    private static Logger LOGGER = Logger.getLogger(ConnectionPoolImpl.class.getName());

    private static final String DRIVER_CLASS = "driver";
    private static final String DB_HOST = "host";
    private static final String DB_PASS = "password";
    private static final String DB_URL = "url";
    private static final String POOL_CAPACITY = "poolCapacity";
    private static final String DB_PROPERTIES_NAME = "db.properties";

    private String url;
    private String host;
    private String password;
    private Semaphore semaphore;
    private static Lock lock = new ReentrantLock();

    private BlockingDeque<Connection> connections = new LinkedBlockingDeque<>();

    private static ConnectionPool instance;

    private ConnectionPoolImpl() {
    }

    public static ConnectionPool getInstance() throws ConnectionPoolException {
        lock.lock();
        try {
            if (instance == null) {
                instance = new ConnectionPoolImpl();
                ((ConnectionPoolImpl) instance).init();
            }

        } finally {
            lock.unlock();
        }

        return instance;
    }

    public void init() throws ConnectionPoolException {

        Properties properties = new Properties();
        try (InputStream inputStream = ConnectionPoolImpl.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_NAME)) {
            properties.load(inputStream);

            String url= properties.getProperty(DB_URL);
            host= properties.getProperty(DB_HOST);
            password= properties.getProperty(DB_PASS);

            this.url = url +
                    "?verifyServerCertificate=false" +
                    "&useSSL=false" +
                    "&requireSSL=false" +
                    "&useLegacyDatetimeCode=false" +
                    "&amp" +
                    "&serverTimezone=UTC" +
                    "&allowPublicKeyRetrieval=true";


            int poolCapacity = Integer.parseInt(properties.getProperty(POOL_CAPACITY));
            Class.forName(properties.getProperty(DRIVER_CLASS));
            semaphore = new Semaphore(poolCapacity);
            connections = new LinkedBlockingDeque<>();
            for (int i = 0; i < poolCapacity; i++) {
                createConnection();
            }

        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new ConnectionPoolException("Initialize error", e);
        }
    }

    @Override
    public Connection getConnection() throws ConnectionPoolException {

        try {
            semaphore.acquire();

            return connections.take();

        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error taking the connection", e);
        }

    }

    @Override
    public void releaseConnection(Connection connection) {
        connections.add(connection);
        semaphore.release();
    }

    @Override
    public void destroyPool() throws ConnectionPoolException {
        try {
            for (Connection connection : connections) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException(e);
        }
    }

    private void createConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(url, host,password);

        InvocationHandler connectionHandler = (Object proxy, Method method, Object[] args) -> {
            if (method.getName().equals("close")) {
                releaseConnection((Connection) proxy);
                return null;
            }
            return method.invoke(connection, args);
        };

        Proxy.newProxyInstance(connection.getClass().getClassLoader(),
                connection.getClass().getInterfaces(), connectionHandler);
        connections.add(connection);
    }
}
