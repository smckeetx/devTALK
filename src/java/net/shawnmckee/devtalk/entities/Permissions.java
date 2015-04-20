/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author smckee
 */
@Entity
@Table(name = "permissions")
@NamedQueries({
    @NamedQuery(name = "Permissions.findAll", query = "SELECT p FROM Permissions p"),
    @NamedQuery(name = "Permissions.findByPermissionID", query = "SELECT p FROM Permissions p WHERE p.permissionID = :permissionID"),
    @NamedQuery(name = "Permissions.findByPermissionDesc", query = "SELECT p FROM Permissions p WHERE p.permissionDesc = :permissionDesc"),
    @NamedQuery(name = "Permissions.findByPermissionCode", query = "SELECT p FROM Permissions p WHERE p.permissionCode = :permissionCode")})
public class Permissions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "permissionID")
    private Integer permissionID;
    @Basic(optional = false)
    @Column(name = "permissionDesc")
    private String permissionDesc;
    @Basic(optional = false)
    @Column(name = "permissionCode")
    private String permissionCode;
    @ManyToMany(mappedBy = "permissionsList")
    private List<Roles> rolesList;

    public Permissions() {
    }

    public Permissions(Integer permissionID) {
        this.permissionID = permissionID;
    }

    public Permissions(Integer permissionID, String permissionDesc, String permissionCode) {
        this.permissionID = permissionID;
        this.permissionDesc = permissionDesc;
        this.permissionCode = permissionCode;
    }

    public Integer getPermissionID() {
        return permissionID;
    }

    public void setPermissionID(Integer permissionID) {
        this.permissionID = permissionID;
    }

    public String getPermissionDesc() {
        return permissionDesc;
    }

    public void setPermissionDesc(String permissionDesc) {
        this.permissionDesc = permissionDesc;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public List<Roles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Roles> rolesList) {
        this.rolesList = rolesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (permissionID != null ? permissionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permissions)) {
            return false;
        }
        Permissions other = (Permissions) object;
        if ((this.permissionID == null && other.permissionID != null) || (this.permissionID != null && !this.permissionID.equals(other.permissionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.shawnmckee.devtalk.entities.Permissions[ permissionID=" + permissionID + " ]";
    }
    
}
