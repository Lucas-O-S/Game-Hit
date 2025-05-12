package br.edu.cefsa.gametracker.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import br.edu.cefsa.gametracker.Controller.PadraoController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AutenticacaoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod metodo = (HandlerMethod) handler;
            Object controller = metodo.getBean();

            if (controller instanceof PadraoController) {
                PadraoController<?> padrao = (PadraoController<?>) controller;

                if (padrao.getPrecisaLogar()) {
                    HttpSession session = request.getSession();
                    if (session.getAttribute("usuario") == null) {
                        response.sendRedirect("/Usuario/Login");
                        return false;
                    }
                }
            }
        }

        return true;
    }
}
