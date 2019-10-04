package hr.fer.zemris.java.hw15.model;

import javax.persistence.*;
import java.util.List;

/**
 * Class represents blog user.
 *
 * @author Stjepan Kovačić
 */
@Entity
@Table(name = "blog_users")
@Cacheable()
public class BlogUser {

    /**
     * Blog user id.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Blog user first name.
     */
    @Column(length = 30, nullable = false)
    private String firstName;

    /**
     * Blog user last name.
     */
    @Column(length = 30, nullable = false)
    private String lastName;

    /**
     * Blog user nick.
     */
    @Column(length = 20, unique = true, nullable = false)
    private String nick;

    /**
     * Blog user email.
     */
    @Column(length = 30, nullable = false)
    private String email;

    /**
     * Blog user password hash.
     */
    @Column(length = 40, nullable = false)
    private String passwordHash;

    /**
     * Blog user blog entries.
     */
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<BlogEntry> entries;


    /**
     * Getter of the blog user id.
     *
     * @return blog user id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter of the blog user first name.
     *
     * @return blog user first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter of the blog user last name.
     *
     * @return blog user last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter of the blog user nick.
     *
     * @return blog user nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * Getter of the blog user email.
     *
     * @return blog user email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Getter of the blog user password hash.
     *
     * @return blog user password hash
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Getter of the blog user blog entries.
     *
     * @return blog user blog entries
     */
    public List<BlogEntry> getEntries() {
        return entries;
    }

    //**********************************
    //          SETTERS
    //**********************************


    /**
     * Sets blog user id.
     *
     * @param id id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Sets blog user first name.
     *
     * @param firstName first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets blog user last name.
     *
     * @param lastName last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets blog user nick.
     *
     * @param nick nick to set
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Sets blog user email.
     *
     * @param email email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets blog user password hash.
     *
     * @param passwordHash password hash to set
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     * Sets blog entries of the blog user.
     *
     * @param entries entries to set
     */
    public void setEntries(List<BlogEntry> entries) {
        this.entries = entries;
    }
}
