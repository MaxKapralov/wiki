package com.wiki.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.UUID;

@SolrDocument(collection = "smpl")
public class Smpl {
    @Id
    @Indexed(name = "id", type = "string")
    private String id;

    @Indexed(name = "content", type = "string")
    private String content;

    @Indexed(name = "title", type = "string")
    private String title;

    @Indexed(name = "pageId", type = "long")
    private long pageId;

    public Smpl() {
    }

    public Smpl(String content, String title, Long pageId) {
        this.content = content;
        this.title = title;
        this.pageId = pageId;
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public long getPageId() {
        return pageId;
    }

    public Smpl(String id, String content, String title, long pageId) {
        this.id = id;
        this.content = content;
        this.title = title;
        this.pageId = pageId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Smpl{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", pageId=" + pageId +
                '}';
    }
}
