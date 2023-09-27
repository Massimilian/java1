package app.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(urlPatterns = "/*") // этот фильтр будет выполняться всегда
public class CharFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CharFilter started");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // устанавливаем кодировку в Request
        if (servletRequest.getCharacterEncoding() == null) {
            servletRequest.setCharacterEncoding("UTF-8");
        }
        // устанавливаем кодировку в Response
        if (servletResponse.getCharacterEncoding() == null) {
            servletResponse.setCharacterEncoding("UTF-8");
        }
        // продолжаем выполнение программы
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("CharFilter destroyed");
    }
}
