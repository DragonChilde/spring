package com.spring.tx.xml.server;

import com.spring.tx.xml.dao.BookShopDao;
import com.spring.tx.xml.exception.UserAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lee
 * @create 2019/10/24 14:40
 */
@Service
public class BookShopServiceImpl implements BookShopService {

    @Autowired
    private BookShopDao bookShopDao;


    @Override
    public void buyBook(String username, String isbn) {
        int price = bookShopDao.findPriceByIsbn(isbn);
        bookShopDao.updateStock(isbn);
        bookShopDao.updateUserAccount(username,price);
    }
}
