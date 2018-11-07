package com.wiki.repository;

import com.wiki.Utils;
import com.wiki.model.Identity;
import com.wiki.model.Page;
import com.wiki.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Order(2)
public class RepositoryInit implements CommandLineRunner {

    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private UserRepository userRepository;

    public void run(String... args) {
        addIdentity();
        addPage();
    }

    private void addIdentity() {
        Identity maks = new Identity("maxkapralov997@gmail.com", "$2a$04$eT.cUSdIDxVYIHABdQPfw.spAm2qNjLz6GUv3ajy1DiZgg.H1y0FW", Utils.setOf("ROLE_ADMIN", "ROLE_USER"));
        Identity ala = new Identity("alakowalska@gmail.com", "$2a$04$eT.cUSdIDxVYIHABdQPfw.spAm2qNjLz6GUv3ajy1DiZgg.H1y0FW", Utils.setOf("ROLE_USER"));
        userRepository.save(new User("Maks", "Kapralov", maks));
        userRepository.save(new User("Ala", "Kowalska", ala));
    }

    private void addPage() {
        userRepository.findByName("Maks").ifPresent(user -> pageRepository.save(new Page(user, "Test content", "Test title",
                Instant.now(), Utils.setOf(userRepository.findByName("Ala").get()))));
        userRepository.findByName("Maks").ifPresent(user -> pageRepository.save(new Page(user, "Test content2", "Test title2",
                Instant.now(), Utils.setOf(userRepository.findByName("Ala").get()))));
    }
}
