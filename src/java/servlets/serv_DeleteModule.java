/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import helpers.HtmlHelper;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import network.Login;

/**
 *
 * @author Tobias
 */
@WebServlet(name = "deleteModule", urlPatterns = {"/deleteModule"})
public class serv_DeleteModule extends HttpServlet {
    Login login = new Login();

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            HtmlHelper site = new HtmlHelper(out);
            site.printHead("Delete module", "delete-module");
            
            out.println("<h1>Servlet deleteModule at " + request.getContextPath() + "</h1>");
            
            Connection conn = login.loginToDB(out);
            
            String module_id = request.getParameter("module_id");
            
            PreparedStatement deleteModuleDetails;
            
            PreparedStatement deleteModule;
            try {
                deleteModuleDetails = conn.prepareStatement("DELETE FROM module_details WHERE module_id = ?");
                deleteModuleDetails.setString(1, module_id);
                int detailsDeleted = deleteModuleDetails.executeUpdate();
                
                
                deleteModule = conn.prepareStatement("DELETE FROM module WHERE module_id = ?;");
                deleteModule.setString(1, module_id);
                
                int amountDeleted = deleteModule.executeUpdate();
                out.println("<div>" + amountDeleted + " modules deleted. " + detailsDeleted + " students affected.</div>");
                out.println("<form action=\"getModule\"><button class=\"button\">Back to module list</button></form>");
            } catch (SQLException ex) {
                out.println("SQL error: " + ex);
            }
            
            site.closeAndPrintEnd(login);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
