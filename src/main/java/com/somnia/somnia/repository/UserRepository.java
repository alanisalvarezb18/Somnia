package com.somnia.somnia.repository;

import com.somnia.somnia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByCorreo(String correo);
    User findByCorreoAndContrasena(String correo, String contrasena);
}