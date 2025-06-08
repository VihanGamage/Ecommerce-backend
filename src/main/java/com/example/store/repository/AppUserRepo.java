package com.example.store.repository;

import com.example.store.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser,Long> {

    AppUser findByUserName(String userName);

    boolean existsAppUserByUserName(String name);

}
