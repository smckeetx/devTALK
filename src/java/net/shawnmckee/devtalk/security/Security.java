/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.security;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.shawnmckee.devtalk.entities.DBUtil;
import net.shawnmckee.devtalk.entities.User;

/**
 *
 * @author smckee
 */
@WebServlet(name = "Security", urlPatterns = {"/security","/security/"})
public class Security extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String destination = "/index.jsp";
        
        switch(request.getParameter("action")){
            case "login":
                destination = login(request);
                break;
            case "logout":
                destination = logout(request);
                break;
        }
        request.getRequestDispatcher(destination)
               .forward(request, response);
    }

    private String login(HttpServletRequest request)throws ServletException{

        // TODO: check to see if the password == "password" and force a change
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String un = request.getParameter("userName");
        String FAILURE_MESSAGE = "Login failed";
        
        try{
            Query q = em.createNamedQuery("User.findByUserName");
            q.setParameter("userName", un);
            User user = (User)q.getSingleResult();
            
            if(user != null){
                if(user.getUserPassword().equals("password") &&
                   user.getUserActive()
                ){
                    HttpSession session = request.getSession(true);
                    session.setAttribute("User", user);
                    request.setAttribute("selfEdit", "Y");
                    request.setAttribute("permCode", "userUpdt");
                    request.setAttribute("resetPW", true);
                    return "/userUpdt?user=" + user.getUserID();
                }else if(user.checkUserPassword(request.getParameter("password")) &&
                         user.getUserActive()
                        ){
                    HttpSession session = request.getSession(true);
                    session.setAttribute("User", user);
                    return "/WEB-INF/main.jsp";
                }else{
                    request.setAttribute("error", "Invalid User Name or Password");
                }
               
            }else{
                request.setAttribute("error", FAILURE_MESSAGE);
            }
        }catch(NoSuchAlgorithmException | InvalidKeySpecException e){
            request.setAttribute("error", FAILURE_MESSAGE);
        }    
        return "/index.jsp";
    }
    private String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        }
        return "";
}

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        processRequest(request, response);
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
        processRequest(request, response);
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
