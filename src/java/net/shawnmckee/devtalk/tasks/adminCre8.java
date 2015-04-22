/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.tasks;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
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
import net.shawnmckee.devtalk.entities.Roles;
import net.shawnmckee.devtalk.entities.User;

/**
 *
 * @author smckee
 */
@WebServlet(name = "adminCre8", urlPatterns = {"/task/adminCre8"})
public class adminCre8 extends HttpServlet {

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

        try{
            String fn = request.getParameter("firstName");
            String ln = request.getParameter("lastName");
            String un = request.getParameter("userName");
            String eml = request.getParameter("email");
            BigInteger ex = new BigInteger(request.getParameter("extension"));
            Boolean ac = request.getParameter("active").equals("Y") ? true : false;
            
            User user = new User(fn, ln, un, eml, ex, "password", ac);
            try{
                em.getTransaction().begin();
                em.persist(user);
                em.merge(user);
                em.getTransaction().commit();
                em.getTransaction().begin();
                Query q = em.createNamedQuery("Roles.findByRoleID");
                q.setParameter("roleID", 2);
                List<Roles> role = q.getResultList();
                q.setParameter("roleID", 3);
                role.addAll(q.getResultList());
                user.setRolesList(role);
                em.merge(user);
                em.getTransaction().commit();
                
                System.out.println(role.get(0));
                request.getSession().setAttribute("user", user);
            } catch (Exception e) {
                request.setAttribute("error", e.getMessage());
            }
        }catch(Exception e){
            
        }
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

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        HttpSession session = request.getSession(true);

        try{
            
            Query q = em.createNamedQuery("Permissions.findByPermissionCode");
            q.setParameter("permissionCode", "adminCre8");
            Permissions perm = (Permissions)q.getSingleResult();

            session.setAttribute("task", perm.getPermissionDesc());
            request.getRequestDispatcher("/adminAddEdit.jsp").forward(request, response);
        }catch(Exception e){
            System.out.println(e.getMessage());
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
