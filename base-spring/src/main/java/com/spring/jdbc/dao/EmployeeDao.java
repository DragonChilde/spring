package com.spring.jdbc.dao;

import com.spring.jdbc.bean.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * @author Lee
 * @create 2019/10/23 16:46
 */
@Repository
public class EmployeeDao {

    @Autowired
    private  JdbcTemplate jdbcTemplate;

    @Autowired
    private  NamedParameterJdbcTemplate npjt;

    public void insertEmployee(Employee employee)
    {
        String sql = "INSERT INTO tbl_employee (last_name,email,gender) VALUES ( :lastName , :email ,:gender)";
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(employee);
        npjt.update(sql,parameterSource);
    }
}
