/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.tasks;

import java.io.IOException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.shawnmckee.devtalk.entities.DBUtil;
import net.shawnmckee.devtalk.entities.Projects;
import net.shawnmckee.devtalk.entities.User;

/**
 *
 * @author smckee
 */
@WebServlet(name = "projCre8", urlPatterns = {"/projCre8"})
public class ProjectCreate extends HttpServlet {

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

        TaskUtils.setAttributes(request);
        request.getRequestDispatcher("/WEB-INF/projAddEdit.jsp").forward(request, response);
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

        response.setContentType("text/html;charset=UTF-8");
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String error = "";

        String pn  = request.getParameter("projectDesc");
        Boolean ac = request.getParameter("active").equals("Y");

        Query q = em.createNamedQuery("Projects.findByProjectDesc");
        q.setParameter("projectDesc", pn);
        if(!q.getResultList().isEmpty()){
            error += "Project description, " + pn + " in use.<br/>";
        }

        if(error.isEmpty()){
            HttpSession session = request.getSession();
            User user = (User)session.getAttribute("User");
            Projects proj = new Projects(pn, user.getUserID(), ac);
            em.getTransaction().begin();
            em.persist(proj);
            em.getTransaction().commit();

            request.getSession().setAttribute("proj", proj);
        }

        if(!error.isEmpty()){
            request.setAttribute("error", error);
        }
        
        request.getRequestDispatcher("/WEB-INF/projAddEdit.jsp").forward(request, response);
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
