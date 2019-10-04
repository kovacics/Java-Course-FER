package hr.fer.zemris.java.hw15.web.servlets;


import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.LoginForm;
import hr.fer.zemris.java.hw15.model.BlogUser;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Class represents servlet which is used for operation on the main page such as user login process.
 *
 * @author Stjepan Kovačić
 */
@WebServlet("/servleti/main")
public class Main extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Method that process request.
     *
     * @param req  http request
     * @param resp http response
     * @throws ServletException if servlet error happens
     * @throws IOException      if i/o error happens
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<BlogUser> users = DAOProvider.getDAO().getBlogUsers();
        req.setAttribute("users", users);

        // first try, shouldn't validate form because it's still empty
        if (req.getParameter("login") == null) {
            req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
            return;
        }

        LoginForm form = new LoginForm();
        form.fillFromRequest(req);
        form.validate();

        if (form.hasErrors()) {
            req.setAttribute("form", form);
            req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
            return;
        }

        BlogUser user = form.getBlogUser();

        req.getSession().setAttribute("current.user.id", user.getId());
        req.getSession().setAttribute("current.user.fn", user.getFirstName());
        req.getSession().setAttribute("current.user.ln", user.getLastName());
        req.getSession().setAttribute("current.user.nick", user.getNick());

        req.getRequestDispatcher("/WEB-INF/pages/home.jsp").forward(req, resp);
    }
}
