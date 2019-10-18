package com.ags.example.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;
//import javax.xml.*;

public class UserServlet extends HttpServlet {
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String nome = request.getParameter("nome");
        final String cognome = request.getParameter("cognome");
        final String eta = request.getParameter("eta");
        final String sSubmit=request.getParameter("submit");
        request.setAttribute("rnome", nome);
        request.setAttribute("rcognome", cognome);
        request.setAttribute("reta", eta);
        request.setAttribute("rsSubmit",sSubmit);
        final RequestDispatcher dispatcher = request.getRequestDispatcher("user.jsp");
        dispatcher.forward((ServletRequest)request, (ServletResponse)response);
    }
}
