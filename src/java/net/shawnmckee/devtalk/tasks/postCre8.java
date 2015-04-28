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
import net.shawnmckee.devtalk.entities.User;
import net.shawnmckee.devtalk.entities.Thread;

/**
 *
 * @author smckee
 */
@WebServlet(name = "postCre8", urlPatterns = {"/postCre8"})
public class postCre8 extends HttpServlet {

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
        String postText = request.getParameter("postTxt");
        postText = postText.replaceAll("<", "&lt;");
        postText = postText.replaceAll("(\r\n|\n)", "<br />");
        
        if(postText.length() < 20000){
            Posts post = new Posts(thread.getThreadID(), user.getUserID(), postText);

            em.getTransaction().begin();
            em.persist(post);
            em.merge(post);
            em.getTransaction().commit();
        }else{
            error = "Post exceeds 20,000 characters.";
            request.setAttribute("error", error);
        }
        
        Query q = em.createNamedQuery("Posts.findByThreadID");
        q.setParameter("threadID", thread.getThreadID());
        List<Posts> posts = q.getResultList();
        request.setAttribute("posts", posts);
        request.getRequestDispatcher("/threadList.jsp").forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    
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
