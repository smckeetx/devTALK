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
import net.shawnmckee.devtalk.entities.Projects;
import net.shawnmckee.devtalk.entities.User;
import net.shawnmckee.devtalk.entities.Thread;

/**
 *
 * @author smckee
 */
@WebServlet(name = "thrdCre8", urlPatterns = {"/thrdCre8"})
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

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        Query q = null;
        List<Projects> projects = null;
        User user = (User)session.getAttribute("User");

        // get the projects the user can see
        if(user.getPrimaryRoleCode().equals("user")){
            projects = user.getProjectsList();
        }else{
            q = em.createNamedQuery("Projects.findByProjectActive");
            q.setParameter("projectActive", true);
            projects = q.getResultList();
        }
        request.setAttribute("projects", projects);

        try{
            String url = request.getRequestURL().toString();
            String permCode = url.substring(url.lastIndexOf("/") + 1);

            if(permCode == null){
                permCode = "thrdCre8";
            }
            // TODO: Verify that logged in user has permission to do this
            q = em.createNamedQuery("Permissions.findByPermissionCode");
            q.setParameter("permissionCode", permCode);
            Permissions perm = (Permissions)q.getSingleResult();

            // get the list of ALL users and store that
            q = em.createNamedQuery("User.findAll");
            List<User> users = q.getResultList();
            request.setAttribute("users", users);

            request.setAttribute("task"  , perm.getPermissionDesc());
            request.setAttribute("taskID", perm.getPermissionID());
            request.setAttribute("permCode", permCode);

            request.getRequestDispatcher("/WEB-INF/threadAddEdit.jsp").forward(request, response);
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

        HttpSession session = request.getSession(false);
        
        if(session == null){
            response.sendRedirect("/devTALK/?error=Your+session+timed+out!");
        }else{

            response.setContentType("text/html;charset=UTF-8");
            EntityManager em = DBUtil.getEmFactory().createEntityManager();
            String error = "";

            User user = (User)session.getAttribute("User");
            Query q = null;
            List<Projects> projects = null;

            // get the projects the user can see
            if(user.getPrimaryRoleCode().equals("user")){
                projects = user.getProjectsList();
            }else{
                q = em.createNamedQuery("Projects.findByProjectActive");
                q.setParameter("projectActive", true);
                projects = q.getResultList();
            }
            request.setAttribute("projects", projects);

            try{
                Integer proj = Integer.parseInt(request.getParameter("project"));
                if(proj.equals(0))
                    error += "You must select a project.<br/>";

                String title = request.getParameter("title");
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
                        Thread thread = new Thread(title, proj, user.getUserID(), true, isPublic);

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
                        q = em.createNamedQuery("Posts.findByThreadID");
                        q.setParameter("threadID", thread.getThreadID());
                        List<Posts> posts = q.getResultList();
                        request.setAttribute("posts", posts);

                    } catch (Exception e) {
                        error +=  "1: " + e.getMessage() + "<br/>";
                    }
                }
            }catch(Exception e){
                    error +=  "2: " + e.getMessage() + "<br/>";
            }

            if(!error.isEmpty()){
                request.setAttribute("error", error);
            }

            request.getRequestDispatcher("/WEB-INF/threadList.jsp").forward(request, response);

        }
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
