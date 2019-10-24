package com.spring.tx.annotation.server;

import java.util.List;

/**
 * @author Lee
 * @create 2019/10/24 16:54
 */
public interface Cashier {

    public void purchase(String username, List<String> isbns);
}
