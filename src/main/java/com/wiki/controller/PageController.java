package com.wiki.controller;

import com.wiki.model.dto.PageDTO;
import com.wiki.service.PageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("pages")
@CrossOrigin
public class PageController {

    private final PageService pageService;


    public PageController(PageService pageService) {
        this.pageService = pageService;
    }

    @PostMapping
    public ResponseEntity<Void> saveNewPage(@RequestBody PageDTO page) {
        pageService.save(page);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Void> updateEntity(@RequestBody PageDTO page, Authentication authentication) {
         if(pageService.update(page, authentication)) {
             return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping
    public ResponseEntity<List<PageDTO>> getAll(@RequestParam(name = "author", required = false) Long author,
                                                @RequestParam(name = "link", required = false) String link, @RequestParam(name = "lastVersion", required = false) Boolean lastVersion,
                                                Authentication authentication) {
        Optional<List<PageDTO>> pages = pageService.getAll(author, link, lastVersion, authentication);
        return pages.map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.FORBIDDEN));
    }
    @GetMapping("/available")
    public ResponseEntity<List<PageDTO>> getAvailable(@RequestParam("id") Long id, Authentication authentication) {
        return pageService.getAvailable(id, authentication).map(ResponseEntity::ok).orElse(new ResponseEntity<>(HttpStatus.FORBIDDEN));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, Authentication authentication) {
        if(pageService.delete(id, authentication)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}