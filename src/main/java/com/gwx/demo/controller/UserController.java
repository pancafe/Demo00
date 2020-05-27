package com.gwx.demo.controller;

import com.gwx.demo.po.UserDemo;
import com.gwx.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class UserController {

    @Autowired
    private  UserService userService;

    @GetMapping("/infoadd")
    public String infoadd(){
        return "infoadd";
    }

    @GetMapping("/infocrud")
    public String infocrud(@PageableDefault(size=5,sort={"id"},direction= Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("users",userService.listUser( pageable));
        return "infocrud";
    }

    @GetMapping("/register")
    public String toregister(){return "register";}

    @PostMapping("/register")
    public String register(@RequestParam String name, @RequestParam Integer age, @RequestParam String email, @RequestParam String password
    , @RequestParam String sex, @RequestParam String address, HttpSession session){
        UserDemo userDemo=userService.checkUser(email,password);
        if(userDemo != null)return "redirect:/register";

        UserDemo user=new UserDemo();
        user.setName(name);
        user.setAddress(address);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setSex(sex);
        userService.saveUser(user);
        return "login";
    }

    @GetMapping("/login")
    public String tologin(){return "login";}
    @PostMapping("/login")
    public String login(@RequestParam String email,@RequestParam String password,HttpSession session){
        UserDemo userDemo=userService.checkUser(email,password);
        if(userDemo!=null){
            session.setAttribute("user",userDemo);
            return "index";
        }

        else
            return "redirect:/login";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){

        UserDemo userDemo=userService.getUser(id);
        userService.deleteUser(userDemo);

        return "redirect:/infocrud";
    }

    @GetMapping("/update/{id}")
    public String toUpdate(@PathVariable("id")Long id,Model model,HttpSession session){
        UserDemo userDemo=userService.getUser(id);
        model.addAttribute("userinfo",userDemo);
        session.setAttribute("id",id);
        return "updateinfo";
    }

    @PostMapping("/update")
    public String update(@RequestParam String name,@RequestParam Integer age,@RequestParam String email,@RequestParam String password,
              @RequestParam String sex,@RequestParam String address,HttpSession session  ){
        Long updateId= (Long) session.getAttribute("id");
        UserDemo user=new UserDemo();
        user.setName(name);
        user.setAddress(address);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setSex(sex);
        userService.updateUser(updateId,user);
        return "redirect:/infocrud";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/login";
    }

}
