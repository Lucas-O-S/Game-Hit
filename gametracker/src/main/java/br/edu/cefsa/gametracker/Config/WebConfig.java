package br.edu.cefsa.gametracker.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import br.edu.cefsa.gametracker.Interceptor.AutenticacaoInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AutenticacaoInterceptor autenticacaoInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Registrar o interceptor
        registry.addInterceptor(autenticacaoInterceptor)
                .addPathPatterns("/**") // Intercepta todas as requisições
                .excludePathPatterns("/Usuario/Login", "/Usuario/Cadastro"); // Exclui login e cadastro
    }
}


