package com.mcdadork.springbootcrud.service;

import com.mcdadork.springbootcrud.model.Role;
import com.mcdadork.springbootcrud.model.User;
import com.mcdadork.springbootcrud.repository.RoleRepository;
import com.mcdadork.springbootcrud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    public List<User> index() {
        return userRepository.findAll();
    }

    public void update(User updatedUser, long id, Set<String> rawRoles) {
        Optional<User> userToUpdate = userRepository.findById(id);
        if (userToUpdate.isPresent()) {
            User user = userToUpdate.get();
            user.setName(updatedUser.getName());
            user.setLastName(updatedUser.getLastName());
            user.setAge(updatedUser.getAge());
            Set<Role> role = rawRoles.stream().map(roleRepository::findByName).collect(Collectors.toSet());
            user.setRoles(role);
            System.out.println("Updated User Roles: " + updatedUser.getRoles());
            userRepository.save(user);

        } else {
            System.out.println("произошла ошибка в AdminService update");
        }
    }

    public User showUser(long id) {
        log.info(userRepository.findById(id).get().toString());
        return userRepository.findById(id).get();
    }
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
