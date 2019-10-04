package hr.fer.zemris.java.hw15.forms;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.hash.SHA1Util;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.http.HttpServletRequest;

/**
 * Class represents login form.
 *
 * @author Stjepan Kovačić
 */
public class LoginForm extends Form {

    /**
     * Blog user nickname.
     */
    private String nickname;

    /**
     * Blog user password.
     */
    private String password;

    @Override
    public void validate() {
        if (nickname.isBlank()) {
            errors.put("nick", "You must enter your nickname.");
        } else {
            var user = DAOProvider.getDAO().getBlogUser(nickname);
            if (user == null) {
                errors.put("nick", "User or password is incorrect.");
            } else if (password.isBlank()) {
                errors.put("password", "You must enter your password.");
            } else if (!SHA1Util.getHash(password).equals(user.getPasswordHash())) {
                errors.put("nick", "User or password is incorrect.");
            }
        }
    }

    @Override
    public void fillFromRequest(HttpServletRequest req) {
        nickname = req.getParameter("nick");
        password = req.getParameter("password");
    }


    public BlogUser getBlogUser() {
        return DAOProvider.getDAO().getBlogUser(nickname);
    }
}
