package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Util {
    private static String url = "jdbc:mysql://localhost/users";
    private static String username = "rahat";
    private static String password = "mysql";
    // реализуйте настройку соеденения с БД

    public static Connection getConnecting() {
        Connection conn = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();

            try {
                conn = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                System.out.println("Connection failed...");
                System.out.println(e);
            }
        }
        catch(Exception ex){
            System.out.println("Connection failed...");

            System.out.println(ex);
        }
        return conn;
    }


    private static StandardServiceRegistry standardServiceRegistry;
    private static SessionFactory sessionFactory;

    static {
        // Creating StandardServiceRegistryBuilder
        StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

        // Hibernate settings which is equivalent to hibernate.cfg.xml's properties
        Map<String, String> dbSettings = new HashMap<>();
        dbSettings.put(Environment.URL, url);
        dbSettings.put(Environment.USER, username);
        dbSettings.put(Environment.PASS, password);
        dbSettings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
        dbSettings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

        // Apply database settings
        registryBuilder.applySettings(dbSettings);
        // Creating registry
        standardServiceRegistry = registryBuilder.build();
        // Creating MetadataSources
        MetadataSources sources = new MetadataSources(standardServiceRegistry);
        // Creating Metadata
        Metadata metadata = sources.getMetadataBuilder().build();
        // Creating SessionFactory
        sessionFactory = metadata.getSessionFactoryBuilder().build();
    }
    //Utility method to return SessionFactory
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}