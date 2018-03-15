package com.jenius.web.controller;

import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by liyongjun on 17/1/27.
 */
public class HttpController implements HttpRequestHandler {
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("hello","remeo");
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }
}
