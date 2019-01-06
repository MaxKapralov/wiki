package com.wiki.repository;

import com.wiki.model.Smpl;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;
import java.util.Optional;

public interface SmplRepository extends SolrCrudRepository<Smpl, String> {

    List<Smpl> findByContentContainsOrTitleContains(List<String> content, List<String> title);
    Optional<Smpl> findByPageId(Long id);
}
