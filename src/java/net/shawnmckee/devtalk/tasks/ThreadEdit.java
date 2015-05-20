/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
import net.shawnmckee.devtalk.entities.DBUtil;
import net.shawnmckee.devtalk.entities.Posts;
import net.shawnmckee.devtalk.entities.Conversation;

/**
 *
 * @author smckee
 */
@WebServlet(name = "ThreadEdit", urlPatterns = {"/thrdUpdt"})
public class ThreadEdit extends HttpServlet {

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

        String postStr = request.getParameter("postID");
        if(postStr != null){
            Integer postID = Integer.parseInt(postStr);
            // Right now all we do with a post is "delete" it
            Query q = em.createNamedQuery("Posts.findByPostID");
            q.setParameter("postID", postID);
            Posts post = (Posts)q.getSingleResult();
            em.getTransaction().begin();
            //em.persist(post);
            post.setActive(false);
            em.merge(post);
            em.getTransaction().commit();
        }

        String threadStr = request.getParameter("threadID");
        if(threadStr != null){
            Integer threadID = Integer.parseInt(threadStr);
            // Right now all we do with a thread is lock or unlock it
            Query q = em.createNamedQuery("Conversation.findByConversationID");
            q.setParameter("threadID", threadID);
            Conversation thread = (Conversation)q.getSingleResult();
            em.getTransaction().begin();
            Boolean tLocked = request.getParameter("threadLocked").equals("true");
            thread.setThreadLocked(!tLocked);
            em.merge(thread);
            em.getTransaction().commit();
            request.getSession().setAttribute("thread", thread);
        }
        request.getRequestDispatcher("/thrdRead").forward(request, response);
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
