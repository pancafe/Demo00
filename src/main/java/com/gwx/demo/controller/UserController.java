package com.gwx.demo.controller;

import com.gwx.demo.po.UserDemo;
import com.gwx.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public String toinfoadd(){
        return "infoadd";
    }

    @PostMapping("/add")
    public String infoadd(@RequestParam String name, @RequestParam Integer age, @RequestParam String email, @RequestParam String password
            , @RequestParam String sex, @RequestParam String address){
        UserDemo userDemo=userService.checkUser(email,password);
        if(userDemo!=null) return "redirect:/infoadd";
        UserDemo user=new UserDemo();
        user.setName(name);
        user.setAddress(address);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setSex(sex);
        userService.saveUser(user);

        return "redirect:/infocrud";
    }

//    @GetMapping("/infocrud")
//    public String infocrud(@PageableDefault(size=5,sort={"id"},direction= Sort.Direction.DESC) Pageable pageable, Model model){
//        model.addAttribute("users",userService.listUser( pageable));
//        return "infocrud";
//    }
      @GetMapping("/infocrud")
      public String infocrud(@RequestParam(value = "start",defaultValue = "0") Integer start,
                             @RequestParam(value = "limit",defaultValue = "3") Integer limit,
                             Model model){
        start= start<0 ? 0:start;
        Sort sort=Sort.by(Sort.Order.asc("id"));//Sort sort=new Sort(Sort.Default_Direction,"id");已过时
        Pageable pageable=PageRequest.of(start,limit,sort);//Pageable page=new PageRequest(start,limit,sort);已过时

          Page<UserDemo> page=userService.listUser(pageable);
          model.addAttribute("page",page);
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
        Long updateId= (Long) session.getAttribute("id");//session.getAttribute获取出的数据类型为object，所以应转换
        UserDemo user=new UserDemo();
        user.setId(updateId);
        //注意：jpa中没有update相关的方法，而是靠save实现，用来保存实体。当实体中包含主键时，JPA会进行更新操作。因此此步需要将id设置进实体中，否则修改失败报错
        user.setName(name);
        user.setAddress(address);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setSex(sex);
        userService.updateUser(user);
        return "redirect:/infocrud";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/login";
    }

}
