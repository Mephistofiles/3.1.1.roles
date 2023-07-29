package com.mcdadork.springbootcrud.service;



import com.mcdadork.springbootcrud.model.Role;
import com.mcdadork.springbootcrud.repository.RoleRepository;
import com.mcdadork.springbootcrud.model.User;
import com.mcdadork.springbootcrud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Set;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserServiceImp implements UserService, UserDetailsService {


    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean createUser(User user) {
        String login = user.getLogin();
        if (userRepository.findByLogin(login) != null) return false;
        user.setActive(true);
        String password = user.getPassword();
        if (password == null) {
            log.error("Password cannot be null for user: {}", login);
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(roleRepository.findByName("USER")));
        log.info("Save User", login);
        userRepository.save(user);

        return true;
    }


    @Override
    public void add(User user) {
        userRepository.save(user);
    }


    @Override
    public User show(long id) {
        log.info(userRepository.findById(id).get().toString());
        return userRepository.findById(id).get();
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userRepository.findByLogin(login);
    }


}
