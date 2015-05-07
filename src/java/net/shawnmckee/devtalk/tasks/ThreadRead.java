/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.tasks;

import java.io.IOException;
import java.util.Iterator;
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
import net.shawnmckee.devtalk.entities.Conversation;
import net.shawnmckee.devtalk.entities.Permissions;

/**
 *
 * @author smckee
 */
@WebServlet(name = "thrdRead", urlPatterns = {"/thrdRead"})
public class ThreadRead extends HttpServlet {

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

        HttpSession session = request.getSession(false);

        response.setContentType("text/html;charset=UTF-8");
        EntityManager em = DBUtil.getEmFactory().createEntityManager();

        TaskUtils.setAttributes(request);
        
        Query q = null;
        List<Projects> projects = null;
        User user = (User)session.getAttribute("User");

        // get the projects the user can see
        projects = user.getProjectsList();
        request.setAttribute("projects", projects);

        Conversation thread = (Conversation)session.getAttribute("thread");
        if(thread != null){
            q = em.createNamedQuery("Posts.findByConversationID");
            q.setParameter("threadID", thread.getThreadID());
            List<Posts> posts = q.getResultList();
            request.setAttribute("posts", posts);
        }else{
            request.setAttribute("posts", null);
        }
        request.getRequestDispatcher("/WEB-INF/threadList.jsp").forward(request, response);
    }

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

        if(request.getParameter("thread") != null){
            Query q = em.createNamedQuery("Conversation.findByConversationID");
            q.setParameter("threadID", Integer.parseInt(request.getParameter("thread")));
            List<Conversation> threads = q.getResultList();
            Conversation thread = threads.get(0);
            request.getSession().setAttribute("thread", thread);
        }else if(request.getParameter("project") != null){
            Query q = em.createNamedQuery("Conversation.findByProjectID");
            q.setParameter("projectID", Integer.parseInt(request.getParameter("project")));
            User user = (User) request.getSession().getAttribute("User");
            List<Conversation> threads = q.getResultList();
            Iterator itr = threads.iterator();

            while(itr.hasNext()){
                Conversation thrd = (Conversation) itr.next();
                List<User> tUsers = thrd.getUserList();
                if(!thrd.getThreadPublic() &&
                   !tUsers.contains(user) &&
                   !thrd.getUserID().equals(user.getUserID()))
                    itr.remove();
            }

            if(threads.isEmpty()){
                request.setAttribute("error", "You are not connected to any conversations in this project");
            }else{
                request.setAttribute("threads", threads);
            }
            
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
