package com.mcdadork.springbootcrud.service;


import com.mcdadork.springbootcrud.model.Role;
import com.mcdadork.springbootcrud.model.User;

import java.util.List;

public interface UserService {
    void add(User user);
    User show(long id);
    List<Role> getRoles();

    boolean createUser(User user);




}
