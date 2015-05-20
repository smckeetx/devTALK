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
import net.shawnmckee.devtalk.entities.DBUtil;
import net.shawnmckee.devtalk.entities.Permissions;
import net.shawnmckee.devtalk.entities.Projects;

/**
 *
 * @author smckee
 */
@WebServlet(name = "projUpdt", urlPatterns = {"/projUpdt"})
public class ProjectUpdate extends HttpServlet {

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

        try{
            // TODO: Verify that user has permission
            Query q = em.createNamedQuery("Permissions.findByPermissionCode");
            q.setParameter("permissionCode", "projUpdt");
            Permissions perm = (Permissions)q.getSingleResult();
            request.setAttribute("permCode", "projUpdt");
            request.setAttribute("task", perm.getPermissionDesc());

            // get the list of active projects and store it
            q = em.createNamedQuery("Projects.findAll");
            List<Projects> projects = q.getResultList();
            request.setAttribute("projects", projects);

            request.getRequestDispatcher("/WEB-INF/projAddEdit.jsp").forward(request, response);
        }catch(ServletException | IOException e){
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
        
        response.setContentType("text/html;charset=UTF-8");
        EntityManager em = DBUtil.getEmFactory().createEntityManager();
        String error = "";

        TaskUtils.setAttributes(request);
        
        String pn  = request.getParameter("projectDesc");
        String active = request.getParameter("active");
        Boolean ac = false;
        if(active != null){
            ac = active.equals("Y");
        }

        if(request.getParameter("projects") != null){
            Integer pID = Integer.parseInt(request.getParameter("projects"));
            Query q = em.createNamedQuery("Projects.findByProjectID");
            q.setParameter("projectID", pID);
            Projects proj = (Projects)q.getSingleResult();
            request.getSession().setAttribute("proj", proj);
            pn = proj.getProjectDesc();
            ac = proj.getProjectActive();
            active = "N";
            if(ac){
                active = "Y";
            }

            request.setAttribute("projectDesc", pn);
            request.setAttribute("active", active);
            request.setAttribute("pID", pID);

        }else{

            Integer pID = Integer.parseInt(request.getParameter("pID"));
            Query q = em.createNamedQuery("Projects.findByProjectID");
            q.setParameter("projectID", pID);
            Projects proj = (Projects)q.getSingleResult();
            em.getTransaction().begin();
            proj.setProjectDesc(pn);
            proj.setProjectActive(ac);
            em.merge(proj);
            em.getTransaction().commit();

            request.getSession().setAttribute("proj", proj);
        }

        if(!error.isEmpty()){
            request.setAttribute("error", error);
        }
        
        // get the list of active projects and store it
        Query q = em.createNamedQuery("Projects.findAll");
        List<Projects> projects = q.getResultList();
        request.setAttribute("projects", projects);

        request.getRequestDispatcher("/WEB-INF/projAddEdit.jsp").forward(request, response);
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
