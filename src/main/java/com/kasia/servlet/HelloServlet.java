package com.kasia.servlet;

import com.kasia.controller.Controller;
import com.kasia.controller.UserController;
import org.apache.commons.lang3.StringUtils;
import sda.file.FileOperations;
import sda.file.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by RENT on 2017-03-01.
 */
public class HelloServlet extends HttpServlet {

    private Map<String, Controller> controllerMap;


    @Override
    public void init() throws ServletException {
        System.out.println("Hello from HelloServlet init method");

        this.controllerMap = new HashMap<String, Controller>();
        controllerMap.put("users", new UserController());
        controllerMap.put("default", (request, response) ->
                response.getWriter()
                        .write("<h1>Hello from HelloServlet init method</h1>"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String requestURI = req.getRequestURI();
        String relativePath = requestURI.substring(StringUtils.ordinalIndexOf(requestURI, "/", 2) + 1);

        PrintWriter writer = resp.getWriter();
        writer.write("<h1>Hello World</h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        String body = reader.readLine();
        String[] split = body.split(" ");

        User user = new User();
        user.setFirstName(split[0]);
        user.setLastName(split[1]);
        user.setAge(new Integer(split[2]));

        File file = new File("C:\\Users\\RENT\\Desktop\\test.txt");
        FileOperations.addUserToFile(user, file);

        resp.getWriter().write("Wszystko ok");
        resp.setStatus(201);
    }
}
