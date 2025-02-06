package com.lorin.springSecurity.repo;

import com.lorin.springSecurity.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer>//integer primery ey value umun type ı
{
    Users findByUsername(String username);
}

//hashleme kısmı bcrypt  kullanarak