package hr.fer.zemris.java.hw15.model;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * Class represents comment on some blog entry.
 *
 * @author Stjepan Kovačić
 */
@Entity
@Table(name = "blog_comments")
public class BlogComment {

    /**
     * Comment id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Blog entry on which comment references.
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    private BlogEntry blogEntry;

    /**
     * Email of the comment author.
     */
    @Column(length = 100, nullable = false)
    private String usersEMail;

    /**
     * Comment message.
     */
    @Column(length = 4096, nullable = false)
    private String message;

    /**
     * Date of post.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date postedOn;


    /**
     * Getter of the comment id.
     *
     * @return comment id
     */

    public Long getId() {
        return id;
    }

    /**
     * Getter of the comment blog entry.
     *
     * @return comment blog entry
     */

    public BlogEntry getBlogEntry() {
        return blogEntry;
    }

    /**
     * Getter of the comment author email.
     *
     * @return users email
     */
    public String getUsersEMail() {
        return usersEMail;
    }

    /**
     * Getter of the comment message.
     *
     * @return comment message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Getter of the date of comment post.
     *
     * @return comment post date
     */

    public Date getPostedOn() {
        return postedOn;
    }

    //**********************************
    //          SETTERS
    //**********************************

    /**
     * Sets comment id.
     *
     * @param id comment id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets blog entry.
     *
     * @param blogEntry blog entry to set
     */
    public void setBlogEntry(BlogEntry blogEntry) {
        this.blogEntry = blogEntry;
    }

    /**
     * Sets users email.
     *
     * @param usersEMail user email to set
     */
    public void setUsersEMail(String usersEMail) {
        this.usersEMail = usersEMail;
    }

    /**
     * Sets comment message.
     *
     * @param message comment message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Sets posted on date.
     *
     * @param postedOn date to set
     */
    public void setPostedOn(Date postedOn) {
        this.postedOn = postedOn;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogComment that = (BlogComment) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}