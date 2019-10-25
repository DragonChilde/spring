package com.spring.tx.xml.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Lee
 * @create 2019/10/24 16:55
 */
@Service
public class CashierImpl implements Cashier {

    @Autowired
    private BookShopService bookShopService;


    @Override
    public void purchase(String username, List<String> isbns) {

        for (String isbn : isbns)
        {
            bookShopService.buyBook(username,isbn);
        }
    }
}
