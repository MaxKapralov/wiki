package com.wiki.repository;

import com.wiki.model.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;


@CrossOrigin
@Repository
public interface PageRepository extends CrudRepository<Page, Long> {
    @Query("Select page From Page page Where (:id is null or page.author.id = :id) and " +
            " (:link is null or page.link = :link) and " +
            " (:version is null or page.lastVersion = :version) and " +
            " (page.blocked = false)")
    List<Page> search(@Param("id") Long id, @Param("link") String link, @Param("version")boolean version);

    @Query("Select page From Page page Where exists(select users from page.allowedToRead users where (users.id = :id) and " +
            " (page.lastVersion = :version) and " +
            " (page.blocked = false))")
    List<Page> findAvailable(@Param("id") Long id, @Param("version")boolean version);
}
