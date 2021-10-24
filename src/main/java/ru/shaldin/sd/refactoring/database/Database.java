package ru.shaldin.sd.refactoring.database;

import ru.shaldin.sd.refactoring.domain.Product;

import java.sql.*;
import java.util.*;
import java.util.function.Function;

public class Database {
    public static final String URL = "jdbc:sqlite:test.db";
    public static final String NAME = "PRODUCT";

    private static void executeUpdate(String sql) throws SQLException {
        try (Connection c = DriverManager.getConnection(URL)) {
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }
    }

    private static List<Product> getListOfProductsAsResult(ResultSet rs) {
        try {
            List<Product> products = new ArrayList<>();
            while (rs.next()) {
                String name = rs.getString("name");
                long price = rs.getInt("price");
                products.add(new Product(name, price));
            }
            return products;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static Optional<Long> getLongAsResult(ResultSet rs) {
        try {
            if (rs.next()) {
                return Optional.of(rs.getLong(1));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static <T> T executeQuery(String sql, Function<ResultSet, T> method) throws SQLException {
        try (Connection c = DriverManager.getConnection(URL)) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            T result = method.apply(rs);
            rs.close();
            stmt.close();

            return result;
        }
    }

    public static void createTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + NAME +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " NAME           TEXT    NOT NULL, " +
                " PRICE          INT     NOT NULL)";
        executeUpdate(sql);
    }

    public static void insert(String name, long price) throws SQLException {
        executeUpdate("INSERT INTO " + NAME +
                " (NAME, PRICE) VALUES (\"" + name + "\"," + price + ")");
    }

    public static List<Product> selectAll() throws SQLException {
        String sql = "SELECT * FROM " + NAME;
        return executeQuery(sql, Database::getListOfProductsAsResult);
    }

    public static List<Product> getMax() throws SQLException {
        String sql = "SELECT * FROM " + NAME + " ORDER BY PRICE DESC LIMIT 1";
        return executeQuery(sql, Database::getListOfProductsAsResult);
    }

    public static List<Product> getMin() throws SQLException {
        String sql = "SELECT * FROM " + NAME + " ORDER BY PRICE LIMIT 1";
        return executeQuery(sql, Database::getListOfProductsAsResult);
    }

    public static Optional<Long> getSum() throws SQLException {
        String sql = "SELECT SUM(price) FROM " + NAME;
        return executeQuery(sql, Database::getLongAsResult);
    }

    public static Optional<Long> getCount() throws SQLException {
        String sql = "SELECT COUNT(*) FROM " + NAME;
        return executeQuery(sql, Database::getLongAsResult);
    }
}
