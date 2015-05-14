/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.shawnmckee.devtalk.tasks;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletRequest;
import net.shawnmckee.devtalk.entities.DBUtil;
import net.shawnmckee.devtalk.entities.Permissions;
import net.shawnmckee.devtalk.entities.Projects;

/**
 *
 * @author smckee
 */
public class TaskUtils {
    public TaskUtils(){}
    
    /**
     *
     * @param request
     */
    public static void setAttributes(HttpServletRequest request) {

        String url = request.getRequestURL().toString();
        String permCode = url.substring(url.lastIndexOf("/") + 1);

        EntityManager em = DBUtil.getEmFactory().createEntityManager();
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
    }
}
