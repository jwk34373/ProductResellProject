package com.example.ProductResellProject.configuration;

import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilter {

    private final JwtToken

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }
}
