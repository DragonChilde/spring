package com.spring.tx.xml.dao;

import com.spring.tx.xml.exception.UserAccountException;
import com.spring.tx.xml.exception.BookStockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Lee
 * @create 2019/10/23 17:50
 */
@Repository
public class BookShopDaoImpl implements BookShopDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int findPriceByIsbn(String isbn) {
        String sql =" SELECT price FROM book WHERE isbn = ?";
        Integer price = jdbcTemplate.queryForObject(sql, Integer.class, isbn);
        return price;
    }

    @Override
    public void updateStock(String isbn) {

        //判断库存是否足够
        String sql = "SELECT stock FROM book_stock WHERE isbn = ?";
        Integer stock = jdbcTemplate.queryForObject(sql, Integer.class, isbn);
        if (stock <= 0)
        {
            throw new BookStockException("库存不足.....");
        }

        String updateSql = "UPDATE book_stock SET stock = stock -1 WHERE isbn = ?";
        jdbcTemplate.update(updateSql,isbn);
    }

    @Override
    public void updateUserAccount(String username, Integer price) {
        //判断余额是否足够
        String sql ="SELECT balance FROM account WHERE username = ?";
        Integer balance = jdbcTemplate.queryForObject(sql, Integer.class, username);

        if (balance < price)
        {
            throw new UserAccountException("余额不足......");
        }
        String updateSql = "UPDATE account SET balance = balance - ? WHERE username = ?";
        jdbcTemplate.update(updateSql,price,username);
    }
}
