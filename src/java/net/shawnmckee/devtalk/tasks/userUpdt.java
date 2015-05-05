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
import net.shawnmckee.devtalk.entities.Projects;
import net.shawnmckee.devtalk.entities.User;

/**
 *
 * @author smckee
 */
@WebServlet(name = "userUpdt", urlPatterns = {"/userUpdt"})
public class userUpdt extends HttpServlet {

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

        String url = request.getRequestURL().toString();
        String permCode = url.substring(url.lastIndexOf("/") + 1);

        // TODO: Verify that logged in user has permission to do this
        Query q = em.createNamedQuery("Permissions.findByPermissionCode");
        q.setParameter("permissionCode", permCode);
        Permissions perm = (Permissions)q.getSingleResult();

        request.setAttribute("task"  , perm.getPermissionDesc());
        request.setAttribute("taskID", perm.getPermissionID());
        request.setAttribute("permCode", permCode);

        // get the list of active projects and store it
        q = em.createNamedQuery("Projects.findByProjectActive");
        q.setParameter("projectActive", true);
        List<Projects> projects = q.getResultList();
        request.setAttribute("projects", projects);

        // get the list of ALL users and store that
        q = em.createNamedQuery("User.findAll");
        List<User> users = q.getResultList();
        request.setAttribute("users", users);

        // if a user has been selected from the drop down then get and store them
        if(request.getParameter("user") != null &&
           !request.getParameter("user").equals("")){
            q = em.createNamedQuery("User.findByUserID");
            q.setParameter("userID", Integer.parseInt(request.getParameter("user")));
            User user = (User)q.getSingleResult();
            request.setAttribute("user", user);
        }

        // we have a specific user to update now
        if(request.getParameter("userID") != null && 
           request.getParameter("userID") != ""){

            q = em.createNamedQuery("User.findByUserID");
            q.setParameter("userID", Integer.parseInt(request.getParameter("userID")));
            User user = (User)q.getSingleResult();

            String fn     = request.getParameter("firstName");
            String ln     = request.getParameter("lastName");
            String un     = request.getParameter("userName");
            String eml    = request.getParameter("email");
            BigInteger pn = null;
            try{
                pn = new BigInteger(request.getParameter("userPhone"));
            }catch(java.lang.NumberFormatException e){
                // error message is set below to keep them in a logical order
            }
            Boolean ac = request.getParameter("active").equals("Y");
            String[] projectIDs = request.getParameterValues("projects");

            em.getTransaction().begin();
            em.persist(user);

            if(!fn.equals(""))
                user.setUserFirstName(fn);
            else
                error +=  "First name required<br/>";

            if(!ln.equals(""))
                user.setUserLastName(ln);
            else
                error +=  "Last name required<br/>";

            if(!un.equals("")){
                q = em.createNamedQuery("User.findByUserName");
                q.setParameter("userName", un);
                if(q.getResultList().isEmpty() ||
                   q.getResultList().get(0).equals(user)){
                    user.setUserName(un);
                } else {
                    error += "User name, " + un + " in use.<br/>";
                }
            }else
                error +=  "User name required<br/>";

            if(!eml.equals("")){
                q = em.createNamedQuery("User.findByUserEmail");
                q.setParameter("userEmail", eml);
                if(q.getResultList().isEmpty() ||
                   q.getResultList().get(0).equals(user)){
                    user.setUserEmail(eml);
                } else {
                    error += "User eMail " + eml + " in use.<br/>";
                }
            }else
                error +=  "eMail required<br/>";

            if(pn != null)
                user.setUserExtension(pn);
            else
                error +=  "Phone Number required<br/>";

            if(ac != null)
                user.setUserActive(ac);
            else
                error +=  "Active status required<br/>";

            if(projectIDs != null &&
               projectIDs.length > 0){
                q = em.createNamedQuery("Projects.findByProjectID");
                q.setParameter("projectID", Integer.parseInt(projectIDs[0]));
                List<Projects> userProjects = q.getResultList();

                for(Integer i=1; i<projectIDs.length; i++){
                    q.setParameter("projectID", Integer.parseInt(projectIDs[i]));
                    userProjects.addAll(q.getResultList());
                }
                if(!userProjects.isEmpty())
                    user.setProjectsList(userProjects);
                else
                    error +=  "At least one project is required<br/>";
            }else{
                error +=  "At least one project is required<br/>";
            }

            em.merge(user);
            em.getTransaction().commit();
            request.setAttribute("user", user);

            if(!error.equals("")){
                request.setAttribute("error", error);
            }
        }
        request.getRequestDispatcher("/WEB-INF/userAddEdit.jsp").forward(request, response);
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
