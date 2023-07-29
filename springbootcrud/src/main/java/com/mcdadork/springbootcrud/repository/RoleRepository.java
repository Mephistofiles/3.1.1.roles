package com.mcdadork.springbootcrud.repository;

import com.mcdadork.springbootcrud.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
   Role findByName(String RoleName);
}
