package com.github.zastrixarundell.toramsensei;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.net.URI;
import java.sql.*;
import java.util.Optional;

public class Database
{

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds = getDataSource();

    private static HikariDataSource getDataSource()
    {
        URI dbURI = URI.create(System.getenv("SQL_URL"));

        String username;
        if ((username = dbURI.getUserInfo()) != null)
            username = username.split(":")[0];

        String password;
        if ((password = dbURI.getUserInfo()) != null)
            password = password.split(":")[1];


        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");

        if(username != null)
            config.setUsername(username);

        if(password != null)
            config.setPassword(password);

        String uri = "jdbc:mysql://" + dbURI.getHost() + ":" + dbURI.getPort() + dbURI.getPath();

        config.setJdbcUrl(uri);

        return new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException
    {
        return ds.getConnection();
    }

    //  Initializators

    public static void createHashTable(Connection connection) throws SQLException
    {
        createTable(connection, "hash");
    }

    public static void createNewsTable(Connection connection) throws SQLException
    {
        createTable(connection, "news");
    }

    private static void createTable(Connection connection, String tableName) throws SQLException
    {
        String statement = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "id int NOT NULL," +
                "value VARCHAR(256)," +
                "PRIMARY KEY (id)" +
                ");";

        Statement sqlStatement = connection.createStatement();
        sqlStatement.executeUpdate(statement);
    }

    // Getters

    public static Optional<String> getCachedNews(Connection connection)
    {
        return getCachedElement(connection, "news");
    }

    public static Optional<String> getCachedHash(Connection connection)
    {
        return getCachedElement(connection, "hash");
    }

    private static Optional<String> getCachedElement(Connection connection, String element)
    {
        try
        {
            String statement = "SELECT * FROM " + element;
            Statement sqlStatement = connection.createStatement();
            ResultSet resultSet = sqlStatement.executeQuery(statement);

            resultSet.next();

            return Optional.of(resultSet.getString("value"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    // Setters

    public static void setHashElement(Connection connection, String element) throws SQLException
    {
        saveElement(connection, "hash", element);
    }

    public static void setNewsElement(Connection connection, String element) throws SQLException
    {
        saveElement(connection, "news", element);
    }

    private static void saveElement(Connection connection, String table, String element) throws SQLException
    {
        String command = "INSERT INTO " + table + "(id, value)\n" +
                "VALUES (?, ?)\n" +
                "ON DUPLICATE KEY UPDATE\n" +
                "id = VALUES(id)," +
                "value = VALUES(value);";

        PreparedStatement pstmt = connection.prepareStatement(command);
        pstmt.setInt(1, 1);
        pstmt.setString(2, element);
        pstmt.executeUpdate();
    }
}
