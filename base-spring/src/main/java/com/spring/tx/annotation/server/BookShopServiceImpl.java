package com.spring.tx.annotation.server;

import com.spring.tx.annotation.dao.BookShopDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Lee
 * @create 2019/10/24 14:40
 */
//@Transactional       //对当前类中所有的方法都起作用
@Service
public class BookShopServiceImpl implements BookShopService{

    @Autowired
    private BookShopDao bookShopDao;


    /**
     * 事务属性:
     * 	 1. 事务的传播行为 propagation: 一个事务方法被另外一个事务方法调用时，当前的事务如何使用事务.
     * 			Propagation.REQUIRED  默认值.  使用调用者的事务.
     * 			Propagation.REQUIRES_NEW     将调用者的事务挂起, 重新开启事务来使用.
     *   2. 事务的隔离级别 isolation
     *   		1    读未提交      脏读
     *   		2    读已提交      不可重复读
     *    		4    可重复读      幻读
     *    		8    串行化         效率低。
     *   3. 事务的回滚与不回滚   默认情况下， Spring会对所有的运行时异常进行事务回滚.
     *   		rollbackFor
     *          rollbackForClassName
     *          noRollbackFor
     *          noRollbackForClassName
     *   4. 事务的只读设置:
     *   		readOnly
     *   			true:  只读     代表着只会对数据库进行读取操作， 不会有修改的操作.
     *    						如果确保当前的事务只有读取操作，就有必要设置为只读，可以帮助数据库
     *    						引擎优化事务
     *   			false: 非只读   不仅会读取数据还会有修改操作。
     *   5. 事务的超时设置:  设置事务在强制回滚之前可以占用的时间.
     *   		timeout:
     *
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)      //只对当前的方法起作用
    public void buyBook(String username, String isbn) {
        int price = bookShopDao.findPriceByIsbn(isbn);
        bookShopDao.updateStock(isbn);
        bookShopDao.updateUserAccount(username,price);
    }
}
