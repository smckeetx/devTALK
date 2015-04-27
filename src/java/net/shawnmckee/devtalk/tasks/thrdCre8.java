/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.tasks;

import java.io.IOException;
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
import net.shawnmckee.devtalk.entities.Posts;
import net.shawnmckee.devtalk.entities.User;
import net.shawnmckee.devtalk.entities.Thread;

/**
 *
 * @author smckee
 */
@WebServlet(name = "thrdCre8", urlPatterns = {"/thrdCre8"})
public class thrdCre8 extends HttpServlet {

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
        HttpSession session = request.getSession();
        User user = (User)session.getAttribute("User");

        try{
            Integer proj = Integer.parseInt(request.getParameter("project"));
            if(proj.equals(0))
                error = error +  "You must select a project.<br/>";
            
            String title = request.getParameter("title");
            if(title.trim().equals(""))
                error = error +  "You must enter a title.<br/>";
            
            String postTxt = request.getParameter("post");
            if(postTxt.trim().equals(""))
                error = error +  "You must some content.<br/>";
            

            if(error.equals("")){
                try{
                    Thread thread = new Thread(title, proj, user.getUserID(), true);

                    em.getTransaction().begin();
                    em.persist(thread);
                    em.merge(thread);
                    em.getTransaction().commit();

                    Posts post = new Posts(thread.getThreadID(), user.getUserID(), postTxt);
                    em.getTransaction().begin();
                    em.persist(post);
                    em.merge(post);
                    em.getTransaction().commit();

                    request.getSession().setAttribute("thread", thread);
                    Query q = em.createNamedQuery("Posts.findByThreadID");
                    q.setParameter("threadID", thread.getThreadID());
                    List<Posts> posts = q.getResultList();
                    request.setAttribute("posts", posts);
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

        request.getRequestDispatcher("/threadList.jsp").forward(request, response);
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
            String url = request.getRequestURL().toString();
            String permCode = url.substring(url.lastIndexOf("/") + 1);
            // String permCode = (String)request.getAttribute("permCode");
            if(permCode == null)
                permCode = "thrdCre8";
            // TODO: Verify that logged in user has permission to do this
            Query q = em.createNamedQuery("Permissions.findByPermissionCode");
            q.setParameter("permissionCode", permCode);
            Permissions perm = (Permissions)q.getSingleResult();

            request.setAttribute("task"  , perm.getPermissionDesc());
            request.setAttribute("taskID", perm.getPermissionID());
            request.setAttribute("permCode", permCode);
            request.getRequestDispatcher("/threadAddEdit.jsp").forward(request, response);
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
