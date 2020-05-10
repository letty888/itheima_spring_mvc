package com.itheima.dao.impl;

import com.itheima.dao.UserDao;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: zhangHuan
 * @date: 2020/05/10/19:17
 * @Description:
 */
@Repository
public class UserDaoImpl implements UserDao {
    public void save() {
        System.out.println("save method is running...");
    }
}
