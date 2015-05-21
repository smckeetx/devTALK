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
import net.shawnmckee.devtalk.entities.Posts;
import net.shawnmckee.devtalk.entities.Projects;
import net.shawnmckee.devtalk.entities.User;
import net.shawnmckee.devtalk.entities.Conversation;

/**
 *
 * @author smckee
 */
@WebServlet(name = "thrdCre8", urlPatterns = {"/thrdCre8", "/thrdGet"})
public class ThreadCreate extends HttpServlet {

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

        TaskUtils.setAttributes(request);
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        List<Projects> projects = null;
        User user = (User)session.getAttribute("User");

        // get the projects the user can see
        projects = user.getProjectsList();
        request.setAttribute("projects", projects);

        // get the list of ALL users and store that
        Query q = em.createNamedQuery("User.findByUserActive");
        q.setParameter("userActive", true);
        List<User> users = q.getResultList();
        request.setAttribute("users", users);

        request.getRequestDispatcher("/WEB-INF/threadAddEdit.jsp").forward(request, response);
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

        HttpSession session = request.getSession(false);

        response.setContentType("text/html;charset=UTF-8");
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String error = "";

        User user = (User)session.getAttribute("User");
        Query q = null;
        List<Projects> projects = null;

        // get the projects the user can see
        projects = user.getProjectsList();
        request.setAttribute("projects", projects);
        String destination = "";

        try{
            Integer proj = Integer.parseInt(request.getParameter("project"));
            if(proj.equals(0)){
                destination = "/WEB-INF/threadAddEdit.jsp";
                error += "You must select a project.<br/>";
            }else{
                
                if(request.getParameterMap().containsKey("title")){
                    String title = request.getParameter("title");
                
                    destination = "/WEB-INF/threadList.jsp";
                    title = title.replaceAll("<", "&lt;");
                    if(title.trim().isEmpty())
                        error += "You must enter a title.<br/>";

                    String postTxt = request.getParameter("post");
                    if(postTxt.trim().isEmpty())
                        error += "You must enter some content.<br/>";

                    Boolean isPublic = request.getParameter("pubPriv").equals("public");

                    String[] participants = null;
                    try{
                        participants = request.getParameterValues("participants");
                        if(!isPublic && 
                           (participants == null || 
                            (participants.length == 1 && participants[0].equals("0"))
                           )
                          ){
                            error += "You must add at least one participant.<br/>";
                        }
                    }catch(NullPointerException npe){
                        error += "You must add at least one participant.<br/>";
                    }

                    if(error.isEmpty()){
                        try{
                            Conversation thread = new Conversation(title, proj, user.getUserID(), true, isPublic);

                            em.getTransaction().begin();
                            em.persist(thread);

                            if(participants != null){
                                q = em.createNamedQuery("User.findByUserID");
                                q.setParameter("userID", Integer.parseInt(participants[0]));
                                List<User> users = q.getResultList();
                                for(Integer i=1; i<participants.length; i++){
                                    q.setParameter("userID", Integer.parseInt(participants[i]));
                                    users.addAll(q.getResultList());
                                }

                                thread.setUserList(users);
                            }
                            em.merge(thread);
                            em.getTransaction().commit();

                            Posts post = new Posts(thread.getThreadID(), user.getUserID(), postTxt);
                            em.getTransaction().begin();
                            em.persist(post);
                            em.getTransaction().commit();

                            request.getSession().setAttribute("thread", thread);
                            q = em.createNamedQuery("Posts.findByConversationID");
                            q.setParameter("threadID", thread.getThreadID());
                            List<Posts> posts = q.getResultList();
                            request.setAttribute("posts", posts);

                        } catch (Exception e) {
                            error +=  "1: " + e.getMessage() + "<br/>";
                        }
                    }
                
                }else{
                    destination = "/WEB-INF/threadAddEdit.jsp";
                    q = em.createNamedQuery("User.findByUserProjects");
                    q.setParameter("projectID", proj);
                    List<User> users = q.getResultList();
                    request.setAttribute("users", users);
                    request.setAttribute("project", proj);
                }
            }
        }catch(Exception e){
                error +=  "2: " + e.getMessage() + "<br/>";
        }

        if(!error.isEmpty()){
            request.setAttribute("error", error);
        }

        request.getRequestDispatcher(destination).forward(request, response);
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
