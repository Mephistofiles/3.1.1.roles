package com.mcdadork.springbootcrud.controller;


import com.mcdadork.springbootcrud.dto.UserDTO;
import com.mcdadork.springbootcrud.model.User;

import com.mcdadork.springbootcrud.service.UserService;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String input() {return "input";}

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration() {
        //System.out.println(userService.getRoles().toString());
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(UserDTO userDTO, Model model) {
        if (!userService.createUser(convertToUser(userDTO))) {
            model.addAttribute("errorMessage", "Пользователь с таким логином существует");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/user") // URL-шаблон с параметром id
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public String show(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        model.addAttribute("user", currentUser);
        return "user-info"; // Вернуть имя представления для "user-info.html"
    }

    private User convertToUser(UserDTO userDTO){
        return modelMapper.map(userDTO, User.class);
    }


}
