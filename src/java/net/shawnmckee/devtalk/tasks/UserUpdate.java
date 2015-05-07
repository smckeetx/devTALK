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
import net.shawnmckee.devtalk.entities.DBUtil;
import net.shawnmckee.devtalk.entities.Permissions;
import net.shawnmckee.devtalk.entities.Projects;
import net.shawnmckee.devtalk.entities.User;

/**
 *
 * @author smckee
 */
@WebServlet(name = "userUpdt", urlPatterns = {"/userUpdt"})
public class UserUpdate extends HttpServlet {

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

        TaskUtils.setAttributes(request);
        
        // get the list of ALL users and store that
        Query q = em.createNamedQuery("User.findAll");
        List<User> users = q.getResultList();
        request.setAttribute("users", users);

        // if a user has been selected from the drop down then get and store them
        if(request.getParameter("user") != null &&
           !request.getParameter("user").isEmpty()
          ){
            q = em.createNamedQuery("User.findByUserID");
            q.setParameter("userID", Integer.parseInt(request.getParameter("user")));
            User user = (User)q.getSingleResult();
            request.setAttribute("user", user);
        }

        // we have a specific user to update now
        if(request.getParameter("userID") != null && 
           !request.getParameter("userID").isEmpty()
          ){

            q = em.createNamedQuery("User.findByUserID");
            q.setParameter("userID", Integer.parseInt(request.getParameter("userID")));
            User user = (User)q.getSingleResult();

            String fn        = request.getParameter("firstName");
            String ln        = request.getParameter("lastName");
            String un        = request.getParameter("userName");
            String eml       = request.getParameter("email");
            String phone     = request.getParameter("userPhone");
            String active    = request.getParameter("active");
            String selfEdit  = request.getParameter("selfEdit");

            em.getTransaction().begin();

            if(fn != null &&
               !fn.isEmpty()){
                user.setUserFirstName(fn);
            }else{
                error +=  "First name required<br/>";
            }
            
            if(ln != null &&
               !ln.isEmpty()){
                user.setUserLastName(ln);
            }else{
                error +=  "Last name required<br/>";
            }
            if(un != null &&
               !un.isEmpty()){
                q = em.createNamedQuery("User.findByUserName");
                q.setParameter("userName", un);
                if(q.getResultList().isEmpty() ||
                   q.getResultList().get(0).equals(user)){
                    user.setUserName(un);
                } else {
                    error += "User name, " + un + " in use.<br/>";
                }
            }else{
                error +=  "User name required<br/>";
            }

            if(eml != null &&
               !eml.isEmpty()){
                q = em.createNamedQuery("User.findByUserEmail");
                q.setParameter("userEmail", eml);
                if(q.getResultList().isEmpty() ||
                   q.getResultList().get(0).equals(user)){
                    user.setUserEmail(eml);
                } else {
                    error += "User eMail " + eml + " in use.<br/>";
                }
            }else{
                error +=  "eMail required<br/>";
            }
            
            if(phone != null &&
               !phone.isEmpty()){
                BigInteger pn = new BigInteger(phone);
                user.setUserExtension(pn);
            }else{
                error +=  "Phone Number required<br/>";
            }
            
            if(selfEdit == null){
                if(active != null &&
                   !active.isEmpty()){
                    Boolean ac = active.equals("Y");
                    user.setUserActive(ac);
                }else{
                    error +=  "Active status required<br/>";
                }

                if(request.getParameterValues("projects") != null){
                    String[] projectIDs = request.getParameterValues("projects");
                    if(projectIDs.length > 0){
                        q = em.createNamedQuery("Projects.findByProjectID");
                        q.setParameter("projectID", Integer.parseInt(projectIDs[0]));
                        List<Projects> userProjects = q.getResultList();

                        for(Integer i=1; i<projectIDs.length; i++){
                            q.setParameter("projectID", Integer.parseInt(projectIDs[i]));
                            userProjects.addAll(q.getResultList());
                        }

                        if(!userProjects.isEmpty()){
                            user.setProjectsList(userProjects);
                        }else{
                            error +=  "At least one project is required<br/>";
                        }
                    }else{
                        error +=  "At least one project is required<br/>";
                    }
                }else{
                    error +=  "At least one project is required<br/>";
                }
            }else{
                request.setAttribute("selfEdit", "Y");
            }
            
            em.merge(user);
            em.getTransaction().commit();
            request.setAttribute("user", user);

            if(!error.isEmpty()){
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

        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        TaskUtils.setAttributes(request);

        if(request.getParameter("myID") != null && 
           !request.getParameter("myID").isEmpty()
          ){
            Integer myID = Integer.parseInt(request.getParameter("myID"));
            Query q = em.createNamedQuery("User.findByUserID");
            q.setParameter("userID", Integer.parseInt(request.getParameter("myID")));
            User user = (User)q.getSingleResult();
            User me = (User)request.getSession().getAttribute("User");
            
            if(!me.getUserID().equals(myID) &&
               me.getPrimaryRoleCode().equals("user")
              ){
                request.setAttribute("error", "User may only edit their own account.");
                request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);
            }else{
                request.setAttribute("user", me);
                request.setAttribute("selfEdit", "Y");
            }
        }
        request.getRequestDispatcher("/WEB-INF/userAddEdit.jsp").forward(request, response);
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
