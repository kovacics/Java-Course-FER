package hr.fer.zemris.java.hw14.filters;

import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Web filter whose function is setting connection of the connection provider.
 */
@WebFilter(urlPatterns = {"/servleti/*"})
public class ConnectionSetterFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        DataSource ds = (DataSource) request.getServletContext().getAttribute("hr.fer.zemris.dbpool");
        Connection con;
        try {
            con = ds.getConnection();
        } catch (SQLException e) {
            throw new IOException("Baza podataka nije dostupna.", e);
        }
        SQLConnectionProvider.setConnection(con);
        try {
            chain.doFilter(request, response);
        } finally {
            SQLConnectionProvider.setConnection(null);
            try {
                con.close();
            } catch (SQLException ignore) {
            }
        }
    }
}