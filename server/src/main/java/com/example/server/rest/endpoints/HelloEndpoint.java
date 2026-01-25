package com.example.server.rest.endpoints;

import java.io.IOException;

import com.example.server.BaseServerImpl;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloEndpoint extends HttpServlet {

    private static final Logger _log = LoggerFactory.getLogger(HelloEndpoint.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        _log.debug("request: method = {}, URI = {}, from = {}",
                request.getMethod(),
                request.getRequestURI(),
                request.getRemoteAddr());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);

        String jsonResponse = "{\"status\": \"ok\"}";
        response.getWriter().print(jsonResponse);

        _log.debug("response: status = {}, body = {}",
                response.getStatus(),
                jsonResponse);
    }

}
