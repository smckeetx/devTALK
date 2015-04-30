/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author smckee
 */
@Entity
@Table(name = "roles")
@NamedQueries({
    @NamedQuery(name = "Roles.findAll", query = "SELECT r FROM Roles r"),
    @NamedQuery(name = "Roles.findByRoleID", query = "SELECT r FROM Roles r WHERE r.roleID = :roleID"),
    @NamedQuery(name = "Roles.findByRoleDesc", query = "SELECT r FROM Roles r WHERE r.roleDesc = :roleDesc"),
    @NamedQuery(name = "Roles.findByRoleCode", query = "SELECT r FROM Roles r WHERE r.roleCode = :roleCode")})
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "roleID")
    private Integer roleID;
    @Basic(optional = false)
    @Column(name = "roleDesc")
    private String roleDesc;
    @Basic(optional = false)
    @Column(name = "subRoles")
    private String subRoles;
    @Basic(optional = false)
    @Column(name = "roleCode")
    private String roleCode;
    @JoinTable(name = "roleperms", joinColumns = {
        @JoinColumn(name = "roleID", referencedColumnName = "roleID")}, inverseJoinColumns = {
        @JoinColumn(name = "permissionID", referencedColumnName = "permissionID")})
    @ManyToMany(fetch=javax.persistence.FetchType.EAGER)
    private List<Permissions> permissionsList;

    public Roles() {
    }

    public Roles(Integer roleID) {
        this.roleID = roleID;
    }

    public Roles(Integer roleID, String roleDesc, String roleCode) {
        this.roleID = roleID;
        this.roleDesc = roleDesc;
        this.roleCode = roleCode;
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public List<Permissions> getPermissionsList() {
        return permissionsList;
    }

    public void setPermissionsList(List<Permissions> permissionsList) {
        this.permissionsList = permissionsList;
    }

    public List getSubRoles() {
        List<Integer> rolesList = new ArrayList<>();
        for (String s : subRoles.split(","))
            rolesList.add(new Integer(s));
        return rolesList;
    }

    public void setSubRoles(String subRoles) {
        this.subRoles = subRoles;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roleID != null ? roleID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Roles)) {
            return false;
        }
        Roles other = (Roles) object;
        if ((this.roleID == null && other.roleID != null) || (this.roleID != null && !this.roleID.equals(other.roleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.shawnmckee.devtalk.entities.Roles[ roleID=" + roleID + " ]";
    }

}
