package com.wiki.repository;

import com.wiki.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@CrossOrigin
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByName(String name);

    @Query("Select user From User user where exists(select identity from user.identity identity where identity.username = :username)")
    Optional<User> findByUsername(@Param("username")String username);
}
