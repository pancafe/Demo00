package com.gwx.demo.po;

import javax.persistence.*;

//@Entity表示这是一个实体bean，@Table指定实体所要映射的表，缺省会默认映射类名 user_demo
@Entity
@Table(name="userdemo")
public class UserDemo {

    /**
     * postgresql不同于mysql等，id自增的设置在建表时应设置为serial，如下
     * create table user(
     *      id serial not null,...
     * )
     *默认以一开始，以一为增量
     *需要在id上使用注解并设置策略为Identity,不能设置为auto，否则会有hibernate_sequence错误
     *
     * **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String sex;
    private Integer age;
    private String email;
    private String address;
    private String password;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDemo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
