package jm.task.core.jdbc.util;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private final static String URL = "jdbc:mysql://localhost:3306/mydbstest";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "123456as";
    //hibernate код
    private static final SessionFactory concreteSessionFactory;
    static {
        try {
            Properties prop = new Properties();
            prop.setProperty("hibernate.connection.url", URL);
            prop.setProperty("hibernate.connection.username", USERNAME);
            prop.setProperty("hibernate.connection.password", PASSWORD);
            prop.setProperty("hibernate.show_sql","true");
            prop.setProperty("hibernate.default_schema","mydbstest");
            prop.setProperty("hibernate.hibernate.format_sql","true");
            prop.setProperty("hibernate.hibernate.current_session_context_class", "thread");
            prop.setProperty("hibernate.hbm2ddl.auto", "create");

            concreteSessionFactory = new org.hibernate.cfg.Configuration()
                    .addProperties(prop)
                    //.addPackage("com.kat")
                    .addAnnotatedClass(jm.task.core.jdbc.model.User.class)
                    .buildSessionFactory()
            ;
        }
        catch (Exception ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static SessionFactory getSessionFactory() throws HibernateException {
        return concreteSessionFactory;
    }
    //jdbc код



    public static Connection getConnection() {
        Connection connection;
        try {
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }




    // реализуйте настройку соеденения с БД
}
