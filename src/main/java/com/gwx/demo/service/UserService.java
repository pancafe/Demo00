package com.gwx.demo.service;

import com.gwx.demo.po.UserDemo;
import org.dom4j.util.UserDataElement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

    UserDemo getUser(Long id);

    Page<UserDemo> listUser(Pageable pageable);


    UserDemo saveUser(UserDemo userDemo);

    UserDemo updateUser(Long id,UserDemo userDemo);

    void deleteUser(UserDemo userDemo);

    UserDemo checkUser(String email,String password);
}
