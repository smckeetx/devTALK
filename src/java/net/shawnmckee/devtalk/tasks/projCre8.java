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
import net.shawnmckee.devtalk.entities.Permissions;
import net.shawnmckee.devtalk.entities.Projects;
import net.shawnmckee.devtalk.entities.User;

/**
 *
 * @author smckee
 */
@WebServlet(name = "projCre8", urlPatterns = {"/projCre8"})
public class projCre8 extends HttpServlet {

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
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String error = "";

        try{
            String pn  = request.getParameter("projectDesc");
            Boolean ac = request.getParameter("active").equals("Y");

            Query q = em.createNamedQuery("Projects.findByProjectDesc");
            q.setParameter("projectDesc", pn);
            if(!q.getResultList().isEmpty()){
                error += "Project description, " + pn + " in use.<br/>";
            }

            if(error.equals("")){
                try{
                    HttpSession session = request.getSession();
                    User user = (User)session.getAttribute("User");
                    Projects proj = new Projects(pn, user.getUserID(), ac);
                    em.getTransaction().begin();
                    em.persist(proj);
                    em.merge(proj);
                    em.getTransaction().commit();

                    request.getSession().setAttribute("proj", proj);
                } catch (Exception e) {
                    error += "1: " + e.getMessage() + "<br/>";
                }
            }
        }catch(Exception e){
                error += "2: " + e.getMessage() + "<br/>";
        }

        if(!error.equals("")){
            request.setAttribute("error", error);
        }
        
        request.getRequestDispatcher("/projAddEdit.jsp").forward(request, response);
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

        HttpSession session = request.getSession(false);

        if(session == null){
            response.sendRedirect("/devTALK/?error=Your+session+timed+out!");
        }else{

            EntityManager em = DBUtil.getEmFactory().createEntityManager();

            try{
                // TODO: Verify that user has permission
                Query q = em.createNamedQuery("Permissions.findByPermissionCode");
                q.setParameter("permissionCode", "projCre8");
                Permissions perm = (Permissions)q.getSingleResult();
                request.setAttribute("permCode", "projCre8");
                request.setAttribute("task", perm.getPermissionDesc());
                request.getRequestDispatcher("/projAddEdit.jsp").forward(request, response);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
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
