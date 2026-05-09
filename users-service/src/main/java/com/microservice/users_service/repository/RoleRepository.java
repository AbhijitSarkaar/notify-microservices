package com.microservice.users_service.repository;

import com.microservice.users_service.enums.AppRole;
import com.microservice.users_service.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findRoleByRoleName(AppRole roleName);
}
