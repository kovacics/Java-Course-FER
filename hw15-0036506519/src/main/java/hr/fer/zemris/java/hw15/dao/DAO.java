package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

import java.util.List;

/**
 * Data access object interface with all needed methods for
 * communication between application and data persistence layer.
 */
public interface DAO {

    /**
     * Method returns blog entry with given id.
     *
     * @param id entry id
     * @return blog entry with specified id
     * @throws DAOException if error happens in the data persistence layer
     */
    BlogEntry getBlogEntry(Long id) throws DAOException;

    /**
     * Method returns list of all blog users.
     *
     * @return list of all blog users
     * @throws DAOException if error happens in the data persistence layer
     */
    List<BlogUser> getBlogUsers() throws DAOException;

    /**
     * Method returns blog user with given nick.
     *
     * @param nick blog user nick
     * @return blog user with specified nick
     * @throws DAOException if error happens in the data persistence layer
     */
    BlogUser getBlogUser(String nick) throws DAOException;

    /**
     * Method returns list of all blog entries of user with specified nick.
     *
     * @param nick nick of the user
     * @return list of entries
     * @throws DAOException if error happens in the data persistence layer
     */
    List<BlogEntry> getAllBlogsOfUser(String nick) throws DAOException;

    /**
     * Method saves new blog user.
     *
     * @param user user to save
     * @throws DAOException if error happens in the data persistence layer
     */
    void saveNewBlogUser(BlogUser user) throws DAOException;

    /**
     * Method saves new blog entry.
     *
     * @param entry entry to save
     * @throws DAOException if error happens in the data persistence layer
     */
    void saveNewBlogEntry(BlogEntry entry) throws DAOException;

    /**
     * Method saves new comment.
     *
     * @param comment comment to save
     * @throws DAOException if error happens in the data persistence layer
     */
    void saveNewComment(BlogComment comment) throws DAOException;
}