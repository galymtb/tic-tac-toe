package com.example.server.rest;

import com.example.server.Bootable;
import jakarta.servlet.http.HttpServlet;

public interface RestServer extends Bootable {

    void addEndpoint(HttpServlet endpoint, String path);

}
