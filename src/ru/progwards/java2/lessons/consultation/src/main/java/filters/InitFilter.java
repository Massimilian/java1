package filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class InitFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        boolean hasName = false;
        Enumeration<String> names = ((HttpServletRequest)servletRequest).getSession().getAttributeNames();
        while(names.hasMoreElements()) {
            if (names.nextElement().equals("name")) {
                hasName = true;
                break;
            }
        }
        if (hasName) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletRequest.getRequestDispatcher("/constart").forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
