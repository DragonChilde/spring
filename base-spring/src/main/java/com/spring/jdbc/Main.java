package com.spring.jdbc;

import com.spring.jdbc.bean.Employee;
import com.spring.jdbc.dao.EmployeeDao;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Lee
 * @create 2019/10/23 11:35
 */
public class Main {

    private static JdbcTemplate jdbcTemplate;

    private static NamedParameterJdbcTemplate npjt;

    public static void main(String[] args) {
        //test1();
        //test2();
        //test3();
        //test4();
        //test5();
        //test6();
        //test7();
        test8();
    }

        /**
     * update():  增删改操作
     */
     private static void test1()
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
        String sql = "INSERT INTO tbl_employee (last_name,email,gender) VALUES ( ? , ? ,?)";
        //jdbcTemplate.update(sql,"李四","test@test.com",1);
        jdbcTemplate.update(sql,new Object[]{"张三","test2@test.com",2});
    }

    /**
     * batchUpdate(): 批量增删改
     */
    private static void test2()
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
        String sql = "INSERT INTO tbl_employee (last_name,email,gender) VALUES ( ? , ? ,?)";

        ArrayList<Object[]> list = new ArrayList<Object[]>();
        list.add(new Object[]{"苍老师","changlaoshi@gmail.com",2});
        list.add(new Object[]{"三上老师","sanshang@gmail.com",2});
        list.add(new Object[]{"Julia","julia@gmial.com",2});
        jdbcTemplate.batchUpdate(sql,list);
    }

    /**
     * queryForObject():
     * 		1. 查询单行数据 返回一个对象
     *      2. 查询单值 返回单个值
     */
    private static void test3()
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
        String sql = "SELECT id,last_name,email,gender From tbl_employee Where id = ?";

        //rowMapper: 行映射  将结果集的一条数据映射成具体的一个java对象.
        RowMapper<Employee> mapper = new BeanPropertyRowMapper<Employee>(Employee.class);

        Employee employee = jdbcTemplate.queryForObject(sql, mapper, 5);
        System.out.println(employee);
    }

    private static void test4()
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
        String sql = "SELECT COUNT(id) FROM tbl_employee";

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println(count);
    }

    private static void test5()
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        jdbcTemplate = context.getBean("jdbcTemplate",JdbcTemplate.class);
        String sql = "SELECT id,last_name,email,gender FROM tbl_employee";

        RowMapper<Employee> rowMapper = new BeanPropertyRowMapper<Employee>(Employee.class);
        List<Employee> employeeList = jdbcTemplate.query(sql, rowMapper);
        System.out.println(employeeList);
    }

    private static void test6()
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        npjt = context.getBean("namedParameterJdbcTemplate", NamedParameterJdbcTemplate.class);
        String sql = "INSERT INTO tbl_employee (last_name,email,gender) VALUES ( :ln , :e ,:g)";

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ln","王五");
        map.put("e","wangwu@test.com");
        map.put("g","1");

        npjt.update(sql,map);
    }

    private static void test7()
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        npjt = context.getBean("namedParameterJdbcTemplate", NamedParameterJdbcTemplate.class);
        String sql = "INSERT INTO tbl_employee (last_name,email,gender) VALUES ( :lastName , :email ,:gender)";

        Employee employee = new Employee("赵六", "zhaoliu@test.com", 1);

        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(employee);

        npjt.update(sql,parameterSource);
    }

    private static void test8()
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-jdbc.xml");
        EmployeeDao bean = context.getBean("employeeDao", EmployeeDao.class);
        Employee employee = new Employee("洪七", "hongqi@test.com", 1);
        bean.insertEmployee(employee);
    }
}
