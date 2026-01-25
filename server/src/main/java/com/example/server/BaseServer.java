package com.example.server;

import jakarta.servlet.http.HttpServlet;

public interface BaseServer extends Bootable {

    void addEndpoint(HttpServlet endpoint, String path);

}
