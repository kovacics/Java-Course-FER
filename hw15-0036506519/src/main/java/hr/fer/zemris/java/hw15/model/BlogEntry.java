package hr.fer.zemris.java.hw15.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Class represents blog entry of some user.
 *
 * @author Stjepan Kovačić
 */
@Entity
@Table(name = "blog_entries")
public class BlogEntry {

    /**
     * Blog entry id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * List of all entry comments.
     */
    @OneToMany(mappedBy = "blogEntry", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("postedOn")
    private List<BlogComment> comments = new ArrayList<>();

    /**
     * Blog entry creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    /**
     * Blog entry last modified date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedAt;

    /**
     * Blog entry title.
     */
    @Column(length = 200, nullable = false)
    private String title;

    /**
     * Blog entry text.
     */
    @Column(length = 4096, nullable = false)
    private String text;

    /**
     * Blog entry creator.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private BlogUser creator;


    /**
     * Getter of the blog entry id.
     *
     * @return blog entry id
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter of the blog entry comments.
     *
     * @return blog entry comments
     */
    public List<BlogComment> getComments() {
        return comments;
    }

    /**
     * Getter of the blog entry creation date.
     *
     * @return blog entry creation date
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Getter of the blog entry last modification date.
     *
     * @return last modification date
     */
    public Date getLastModifiedAt() {
        return lastModifiedAt;
    }

    /**
     * Getter of the blog entry title.
     *
     * @return blog entry title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Getter of the blog entry text.
     *
     * @return blog entry text
     */
    public String getText() {
        return text;
    }

    /**
     * Getter of the blog entry creator.
     *
     * @return blog entry creator
     */
    public BlogUser getCreator() {
        return creator;
    }

    //**********************************
    //          SETTERS
    //**********************************


    /**
     * Sets blog entry id.
     *
     * @param id id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets blog entry comments.
     *
     * @param comments comments to set
     */
    public void setComments(List<BlogComment> comments) {
        this.comments = comments;
    }

    /**
     * Sets date of creation.
     *
     * @param createdAt date to set
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Sets last modification date.
     *
     * @param lastModifiedAt date to set
     */
    public void setLastModifiedAt(Date lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    /**
     * Sets blog entry title.
     *
     * @param title title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets blog entry text.
     *
     * @param text text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Sets blog entry creator.
     *
     * @param creator creator to set
     */
    public void setCreator(BlogUser creator) {
        this.creator = creator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogEntry blogEntry = (BlogEntry) o;
        return Objects.equals(id, blogEntry.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}