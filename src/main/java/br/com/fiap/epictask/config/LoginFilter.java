package br.com.fiap.epictask.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.fiap.epictask.user.User;
import br.com.fiap.epictask.user.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LoginFilter extends OncePerRequestFilter {

    private UserRepository repository;

    public LoginFilter(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
            
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null){
            var user = (OAuth2User) authentication.getPrincipal();
            var userDb = repository.findById(Long.valueOf(user.getName()));
            
            if(userDb.isEmpty()){
                repository.save(User.convert(user));
            }
            
        }

        filterChain.doFilter(request, response);

    }
    
}
