package com.wiki.repository;


import com.wiki.model.Identity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IdentityRepository extends CrudRepository<Identity, Long> {
    Identity findByUsername(String username);
}
