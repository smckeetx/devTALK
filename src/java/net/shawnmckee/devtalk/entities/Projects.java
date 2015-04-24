/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author smckee
 */
@Entity
@Table(name = "projects")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projects.findAll", query = "SELECT p FROM Projects p"),
    @NamedQuery(name = "Projects.findByProjectID", query = "SELECT p FROM Projects p WHERE p.projectID = :projectID"),
    @NamedQuery(name = "Projects.findByProjectDesc", query = "SELECT p FROM Projects p WHERE p.projectDesc = :projectDesc"),
    @NamedQuery(name = "Projects.findByUserID", query = "SELECT p FROM Projects p WHERE p.user.userID = :userID"),
    @NamedQuery(name = "Projects.findByProjectActive", query = "SELECT p FROM Projects p WHERE p.projectActive = :projectActive")})
public class Projects implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "projectID")
    private Integer projectID;
    @Basic(optional = false)
    @Column(name = "projectDesc")
    private String projectDesc;
    @Basic(optional = false)
    @Column(name = "userID")
    private Integer userID;
    @Basic(optional = false)
    @Column(name = "projectActive")
    private boolean projectActive;
    @JoinTable(name = "projectUsers", joinColumns = {
        @JoinColumn(name = "projectID", referencedColumnName = "projectID")}, inverseJoinColumns = {
        @JoinColumn(name = "userID", referencedColumnName = "userID")})
    @ManyToMany
    private List<User> userList;
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Projects() {
    }

    public Projects(String projectDesc, Integer userID, boolean projectActive) {
        this.projectDesc = projectDesc;
        this.userID = userID;
        this.projectActive = projectActive;
    }

    public String getProjectDesc() {
        return projectDesc;
    }

    public void setProjectDesc(String projectDesc) {
        this.projectDesc = projectDesc;
    }

    public boolean getProjectActive() {
        return projectActive;
    }

    public void setProjectActive(boolean projectActive) {
        this.projectActive = projectActive;
    }

    @XmlTransient
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (projectID != null ? projectID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projects)) {
            return false;
        }
        Projects other = (Projects) object;
        if ((this.projectID == null && other.projectID != null) || (this.projectID != null && !this.projectID.equals(other.projectID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.shawnmckee.devtalk.entities.Projects[ projectID=" + projectID + " ]";
    }
    
}
