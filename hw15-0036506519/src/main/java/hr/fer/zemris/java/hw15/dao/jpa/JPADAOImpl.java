package hr.fer.zemris.java.hw15.dao.jpa;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

import java.util.List;

/**
 * JPA implementation of the web app's DAO.
 */
public class JPADAOImpl implements DAO {

    @Override
    public BlogEntry getBlogEntry(Long id) throws DAOException {
        return JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
    }

    @Override
    public List<BlogUser> getBlogUsers() throws DAOException {
        return JPAEMProvider.getEntityManager()
                .createQuery("SELECT u FROM BlogUser AS u", BlogUser.class)
                .getResultList();
    }

    @Override
    public BlogUser getBlogUser(String nick) throws DAOException {
        List<BlogUser> blogUser = JPAEMProvider.getEntityManager()
                .createQuery("SELECT b FROM BlogUser AS b WHERE b.nick=:nickname", BlogUser.class)
                .setParameter("nickname", nick)
                .getResultList();

        if (blogUser.isEmpty()) {
            return null;
        } else {
            return blogUser.get(0);
        }
    }

    @Override
    public List<BlogEntry> getAllBlogsOfUser(String nick) throws DAOException {
        return JPAEMProvider.getEntityManager()
                .createQuery("SELECT b FROM BlogEntry b WHERE b.creator.nick=:nick", BlogEntry.class)
                .setParameter("nick", nick)
                .getResultList();
    }

    @Override
    public void saveNewBlogUser(BlogUser user) throws DAOException {
        JPAEMProvider.getEntityManager().persist(user);
    }

    @Override
    public void saveNewBlogEntry(BlogEntry entry) throws DAOException {
        JPAEMProvider.getEntityManager().persist(entry);
    }

    @Override
    public void saveNewComment(BlogComment comment) throws DAOException {
        JPAEMProvider.getEntityManager().persist(comment);
    }
}