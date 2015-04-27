/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.tasks;

import java.io.IOException;
import java.io.PrintWriter;
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
import net.shawnmckee.devtalk.entities.Posts;
import net.shawnmckee.devtalk.entities.Projects;
import net.shawnmckee.devtalk.entities.User;
import net.shawnmckee.devtalk.entities.Thread;

/**
 *
 * @author smckee
 */
@WebServlet(name = "thrdRead", urlPatterns = {"/thrdRead"})
public class thrdRead extends HttpServlet {

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

        Thread thread = (Thread)session.getAttribute("thread");
        if(thread != null){
            Query q = em.createNamedQuery("Posts.findByThreadID");
            q.setParameter("threadID", thread.getThreadID());
            List<Posts> posts = q.getResultList();
            request.setAttribute("posts", posts);
        }else{
            request.setAttribute("posts", null);
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

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String error = "";
        HttpSession session = request.getSession();
        
        if(request.getParameter("thread") != null){
            Query q = em.createNamedQuery("Thread.findByThreadID");
            q.setParameter("threadID", Integer.parseInt(request.getParameter("thread")));
            List<Thread> threads = q.getResultList();
            Thread thread = threads.get(0);
            request.getSession().setAttribute("thread", thread);
        }else if(request.getParameter("project") != null){
            Query q = em.createNamedQuery("Thread.findByProjectID");
            q.setParameter("projectID", Integer.parseInt(request.getParameter("project")));
            List<Thread> threads = q.getResultList();
            request.setAttribute("threads", threads);
            request.getSession().setAttribute("thread", null);
        }
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
