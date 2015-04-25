/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.tasks;

import java.io.IOException;
import java.io.PrintWriter;
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
@WebServlet(name = "userCre8", urlPatterns = {"/userCre8"})
public class userCre8 extends HttpServlet {

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
            String fn     = request.getParameter("firstName");
            String ln     = request.getParameter("lastName");
            String un     = request.getParameter("userName");
            String eml    = request.getParameter("email");
            // TODO: turn this in to a String
            BigInteger ex = new BigInteger(request.getParameter("extension"));
            Boolean ac    = request.getParameter("active").equals("Y");

            Query q = em.createNamedQuery("User.findByUserName");
            q.setParameter("userName", un);
            if(!q.getResultList().isEmpty()){
                error = error + "User name, " + un + " in use.<br/>";
            }

            q = em.createNamedQuery("User.findByUserEmail");
            q.setParameter("userEmail", eml);
            if(!q.getResultList().isEmpty()){
                error = error + "User eMail " + eml + " in use.<br/>";
            }
            
            String roleCode = request.getParameter("permCode");
            roleCode = roleCode.substring(0, roleCode.length() - 4);

            if(error.equals("")){
                try{
                    User user = new User(fn, ln, un, eml, ex, "password", ac);
                    em.getTransaction().begin();
                    em.persist(user);
                    em.merge(user);
                    em.getTransaction().commit();
                    em.getTransaction().begin();
                    q = em.createNamedQuery("Roles.findByRoleID");
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
                    error = error +  "1: " + e.getMessage() + "<br/>";
                }
            }
        }catch(Exception e){
                error = error +  "2: " + e.getMessage() + "<br/>";
        }

        if(!error.equals("")){
            request.setAttribute("error", error);
        }
        
        request.getRequestDispatcher("/userAddEdit.jsp").forward(request, response);
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
            // TODO: Verify that logged in user has permission to do this
            String permCode = (String)request.getAttribute("permCode");
            if(permCode == null)
                permCode = "userCre8";
            Query q = em.createNamedQuery("Permissions.findByPermissionCode");
            q.setParameter("permissionCode", permCode);
            Permissions perm = (Permissions)q.getSingleResult();

            request.setAttribute("task"  , perm.getPermissionDesc());
            request.setAttribute("taskID", perm.getPermissionID());
            request.setAttribute("permCode", permCode);
            request.getRequestDispatcher("/userAddEdit.jsp").forward(request, response);
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
