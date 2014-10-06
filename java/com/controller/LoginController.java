package com.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    @RequestMapping("loginSearch.htm")
    public ModelAndView loginGateWay(HttpServletRequest request,
                                     HttpServletResponse response) {

        System.out.println("Suneel is smart");

        if (request == null || response == null) {

            System.out.println("Etither request or response is null");
        }

        String username = request.getParameter("USERNAME");

        String password = request.getParameter("PASSWORD");

        String file = request.getParameter("FILE");

        System.out.println(file);

        try {
            InputStream input = request.getInputStream();
            if (input != null) {
                System.out.println("we got input");

                System.out.println(input.toString());

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Logging with userName :" + username
            + " Password : " + password);
        ModelAndView mv = new ModelAndView();

        if (username.equalsIgnoreCase("suneel")
            && password.equalsIgnoreCase("oleti")) {
            mv.setViewName("welcome.jsp");
            mv.addObject("MSG", "Login Successfull");
        } else {
            mv.setViewName("welcome.jsp");
            mv.addObject("MSG", "UNAUTHORISED USER");
        }

        return mv;
    }
}
