package org.example.servlets.consult2.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

public class ProfessorInitFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean hasName = false;
        Enumeration<String> names = ((HttpServletRequest)servletRequest).getSession().getAttributeNames();
        while(names.hasMoreElements()) {
            HttpSession s = ((HttpServletRequest) servletRequest).getSession();
            if (names.nextElement().equals("isprofessor") && ((HttpServletRequest)servletRequest).getSession().getAttribute("isprofessor").equals("really")) {
                hasName = true;
            }
        }
        if (hasName) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher("concrush.jsp").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
