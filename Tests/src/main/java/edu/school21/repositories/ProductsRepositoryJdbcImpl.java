package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        String SQL_QUERY = "SELECT * FROM product";
        List<Product> productList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQL_QUERY)) {
            while (rs.next()) {
                productList.add(new Product(rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price")));
            }
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) throws SQLException {
        String SQL_QUERY = "SELECT * FROM product WHERE id = " + id + ";";
        Product product;

        try (Connection connection = dataSource.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQL_QUERY)) {
            if (!rs.next())
                throw new IllegalArgumentException("Object with id: " + id + " not found!");
            product = new Product(rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("price"));
        }
        return Optional.of(product);
    }

    @Override
    public void update(Product product) throws SQLException {
        String SQL_QUERY = "UPDATE product SET name = ?, price = ? WHERE id = ?;";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY)) {
            ps.setString(1, product.getName());
            ps.setInt(2, product.getPrice());
            ps.setLong(3, product.getId());
            ps.execute();
        }
    }

    @Override
    public void save(Product product) throws SQLException {
        String SQL_QUERY = "INSERT INTO product VALUES (?, ?, ?);";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY)) {
            ps.setLong(1, product.getId());
            ps.setString(2, product.getName());
            ps.setInt(3, product.getPrice());
            ps.execute();
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        String SQL_QUERY = "DELETE FROM product WHERE id = ?;";

        try (Connection connection = dataSource.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_QUERY)) {
            ps.setLong(1, id);
            ps.execute();
        }
    }

}
