package api;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter("/*")
public class CorsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Разрешаем preflight (OPTIONS) запросы
        if ("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
            httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:5174");  // Исправлено
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");  // Добавлены методы
            httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            httpResponse.setStatus(HttpServletResponse.SC_OK);
            return; // Завершаем обработку для OPTIONS запроса
        }

        // Основные заголовки для всех запросов
        httpResponse.setHeader("Access-Control-Allow-Origin", "http://localhost:5174");  // Исправлено
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");  // Добавлены методы
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");

        // Пропускаем запрос дальше по цепочке
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}