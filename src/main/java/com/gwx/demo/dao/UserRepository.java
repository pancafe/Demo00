package com.gwx.demo.dao;

import com.gwx.demo.po.UserDemo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDemo,Long> {
    UserDemo findByEmailAndPassword(String email,String password);
}
