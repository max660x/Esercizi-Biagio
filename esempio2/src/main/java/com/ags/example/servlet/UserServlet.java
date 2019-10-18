// 
// Decompiled by Procyon v0.5.36
// 

package com.ags.example.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServlet;

public class UserServlet extends HttpServlet
{
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final RequestDispatcher dispatcher = request.getRequestDispatcher("user.xhtml");
        dispatcher.forward((ServletRequest)request, (ServletResponse)response);
    }
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        final String nome = request.getParameter("nome");
        final String cognome = request.getParameter("cognome");
        final String eta = request.getParameter("eta");

        request.setAttribute("rnome", nome);
        request.setAttribute("rcognome", cognome);
        request.setAttribute("reta", eta);
        final RequestDispatcher dispatcher = request.getRequestDispatcher("user-detail.xhtml");
        dispatcher.forward((ServletRequest)request, (ServletResponse)response);
    }
}