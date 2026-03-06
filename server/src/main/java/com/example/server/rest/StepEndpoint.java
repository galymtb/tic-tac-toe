package com.example.server.rest;

import java.io.IOException;

import com.example.server.handler.BaseHandler;
import com.example.server.transport.RestTransport;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class StepEndpoint extends HttpServlet {

    private final BaseHandler _handler;
    private final ObjectMapper _mapper;

    public StepEndpoint(BaseHandler handler, ObjectMapper mapper) {
        _handler = handler;
        _mapper = mapper;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RestTransport tx = new RestTransport(request, response, _mapper);
        _handler.step(tx);
    }

}
