package com.wiki.service;

import com.wiki.model.Page;
import com.wiki.model.User;
import com.wiki.model.dto.PageDTO;
import com.wiki.model.dto.UserDTO;
import com.wiki.repository.PageRepository;
import com.wiki.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PageService {

    private final PageRepository pageRepository;
    private final UserRepository userRepository;

    public PageService(PageRepository pageRepository, UserRepository userRepository) {
        this.pageRepository = pageRepository;
        this.userRepository = userRepository;
    }

    public boolean update(PageDTO page, Authentication authentication) {
        if (!page.getAuthor().getUsername().equals(authentication.getName()) || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return false;
        }
        Long id = page.getId();
        this.pageRepository.findById(id).ifPresent(item -> {
            item.setVersion(false);
            pageRepository.save(item);
        });
        page.setId(null);
        save(page);
        return true;
    }

    public void save(PageDTO page) {
        Set<User> allowedToRead = new HashSet<>();
        userRepository.findById(page.getAuthor().getId()).ifPresent(author -> {
            page.getAllowedToRead().forEach(user ->
                    userRepository.findById(user.getId()).ifPresent(allowedToRead::add));
            Page entity = new Page(author, page.getContent(), page.getTitle(), page.getTimestamp(), allowedToRead);
            pageRepository.save(entity);
        });
    }

    public Optional<List<PageDTO>> getAll(Long id, String link, Boolean lastVersion, Authentication authentication) {
        List<Page> list = pageRepository.search(id, link, lastVersion);
        boolean accessAllowed = list.stream().allMatch(page ->
                page.getAuthor().getIdentity().getUsername().equals(authentication.getName())
                        || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))
                        || page.getAllowedToRead().stream().anyMatch(user -> user.getIdentity().getUsername().equals(authentication.getName())));
        if(accessAllowed) {
            return Optional.of(list.stream().map(this::convertPageToDTO).collect(Collectors.toList()));
        }
        return Optional.ofNullable(null);
    }

    public PageDTO getById(Long id) {
        return pageRepository.findById(id).map(this::convertPageToDTO).orElse(null);
    }

    private PageDTO convertPageToDTO(Page page) {
        UserDTO author = new UserDTO(page.getAuthor().getId(), page.getAuthor().getIdentity().getUsername(),
                page.getAuthor().getName() + " " + page.getAuthor().getSurname());
        Set<UserDTO> allowedToRead = page.getAllowedToRead().stream().map(user -> new UserDTO(user.getId(), user.getIdentity().getUsername(), user.getFullName())).collect(Collectors.toSet());
        return new PageDTO(page.getId(), page.getContent(), page.getTitle(), page.getTimestamp(), author, allowedToRead, page.isLastVersion());
    }

    public boolean delete(Long id, Authentication authentication) {
        return pageRepository.findById(id).map(val -> {
            if (!val.getAuthor().getIdentity().getUsername().equals(authentication.getName()) || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return false;
            }
            val.setBlocked(true);
            this.pageRepository.save(val);
            return true;
        }).orElse(false);
    }

    private boolean checkAccess(Long id, Authentication authentication) {
        return userRepository.findById(id).map(user -> user.getIdentity().getUsername().equals(authentication.getName())).orElse(false);
    }

    public Optional<List<PageDTO>> getAvailable(Long id, Authentication authentication) {
        Optional<List<PageDTO>> pages;
        if(checkAccess(id, authentication)) {
            pages =  Optional.of(pageRepository.findAvailable(id, true).stream().map(this::convertPageToDTO).collect(Collectors.toList()));
            pages.ifPresent(items -> this.getAll(id, null, true, authentication).ifPresent(items::addAll));
            return pages;
        }
        return Optional.ofNullable(null);
    }
}
