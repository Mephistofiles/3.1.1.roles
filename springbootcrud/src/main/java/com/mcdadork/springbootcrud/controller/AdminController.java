package com.mcdadork.springbootcrud.controller;

import com.mcdadork.springbootcrud.model.RequestWrapper;
import com.mcdadork.springbootcrud.model.Role;
import com.mcdadork.springbootcrud.model.User;
import com.mcdadork.springbootcrud.service.AdminService;
import com.mcdadork.springbootcrud.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {



    private final AdminService adminService;
    private final UserService userService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", adminService.index());
        return "users";
    }


    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") long id) {
        User user = userService.show(id);
        model.addAttribute("user", userService.show(id));
        model.addAttribute("roles", userService.getRoles());
        model.addAttribute("name", user.getRoles());
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("user") User updatedUser, @PathVariable("id") long id, @RequestParam("Raw_Roles") Set<String> rawRoles) {

        adminService.update(updatedUser, id,rawRoles);
        System.out.println("Raw Roles: "+ rawRoles.toString());
        return "redirect:/admin";

    }
//    @PatchMapping("/{id}")
//    public ResponseEntity<String> handleRequest(@RequestBody RequestWrapper request) {
//        Map<String, String> requestData = request.getData();
//        for (String string : requestData.keySet()){
//            log.info("key info "+string);
//        }
//        for (String string : requestData.values()){
//            log.info("values "+string);
//        }
//        return ResponseEntity.ok("Request processed successfully");
//    }
    @DeleteMapping("/edit/{id}")
    public String delete(@PathVariable("id") long id) {
        adminService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/user/{id}")
    public String show(@PathVariable("id") long id, Model model) {
        User user = adminService.showUser(id);
        model.addAttribute("user", user);
        return "user-info"; // Вернуть имя представления для "user-info.html"
    }
}

