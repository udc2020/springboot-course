package com.udc.springboot.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsersRepository extends JpaRepository<Users,Long> {

    @Query("SELECT s FROM Users s WHERE s.name=?1  ")
    Optional<Users> findUsersByName(String name);
}


