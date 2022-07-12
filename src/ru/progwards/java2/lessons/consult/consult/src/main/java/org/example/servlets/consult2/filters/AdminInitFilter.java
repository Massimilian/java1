package org.example.servlets.consult2.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class AdminInitFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean hasName = false;
        boolean isNamed = false;
        Enumeration<String> names = ((HttpServletRequest) servletRequest).getSession().getAttributeNames();
        while (names.hasMoreElements()) {
            if (names.nextElement().equals("name")) {
                isNamed = true;
                if (((HttpServletRequest)servletRequest).getSession().getAttribute("isadmin").equals("really")) {
                    hasName = true;
                    break;
                }
            }
        }
        if (hasName) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            if (!isNamed) {
                ((HttpServletRequest)servletRequest).getSession().setAttribute("name", "Уважаемый пользователь");
            }
            servletRequest.getRequestDispatcher("concrush.jsp").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
