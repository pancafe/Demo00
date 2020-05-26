package com.gwx.demo.service;

import com.gwx.demo.NotFoundException;
import com.gwx.demo.dao.UserRepository;
import com.gwx.demo.po.UserDemo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDemo getUser(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public Page<UserDemo> listUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }


    @Override
    public UserDemo saveUser(UserDemo userDemo) {
        return userRepository.save(userDemo);
    }

    @Override
    public UserDemo updateUser(Long id, UserDemo userDemo) {

       UserDemo user = userRepository.getOne(id);
        if(user == null)
            throw new NotFoundException("该用户不存在");
        BeanUtils.copyProperties(userDemo,user);
        //使用org.springframework.beans.BeanUtils.copyProperties方法进行对象之间属性的赋值，避免通过get、set方法一个一个属性的赋值
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UserDemo userDemo) {
        userRepository.delete(userDemo);
    }

    @Override
    public UserDemo checkUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }


}
