/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test123;

import dto.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import service.LoginService;

/**
 *
 * @author Shikhar Jain
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId,password;
        userId = request.getParameter("userId");
        password = request.getParameter("password");
        
        LoginService loginService = new LoginService();
        boolean result = loginService.authenticate(userId, password);
        if(result) {
            User user = loginService.getUsername(userId);
            request.setAttribute("user", user);
            RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");
            dispatcher.forward(request, response);
            return;
        }
        else {
            response.sendRedirect("login.jsp");
            return;
        }
        
    }
}
