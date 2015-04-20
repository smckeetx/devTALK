/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
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
@Table(name = "users")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByUserID", query = "SELECT u FROM User u WHERE u.userID = :userID"),
    @NamedQuery(name = "User.findByUserName", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    @NamedQuery(name = "User.findByUserFirstName", query = "SELECT u FROM User u WHERE u.userFirstName = :userFirstName"),
    @NamedQuery(name = "User.findByUserLastName", query = "SELECT u FROM User u WHERE u.userLastName = :userLastName"),
    @NamedQuery(name = "User.findByUserEmail", query = "SELECT u FROM User u WHERE u.userEmail = :userEmail"),
    @NamedQuery(name = "User.findByUserExtension", query = "SELECT u FROM User u WHERE u.userExtension = :userExtension"),
    @NamedQuery(name = "User.findByUserPassword", query = "SELECT u FROM User u WHERE u.userPassword = :userPassword"),
    @NamedQuery(name = "Users.findByUserActive", query = "SELECT u FROM User u WHERE u.userActive = :userActive")})
public class User implements Serializable {
    @JoinTable(name = "userroles", joinColumns = {
        @JoinColumn(name = "userID", referencedColumnName = "userID")}, inverseJoinColumns = {
        @JoinColumn(name = "roleID", referencedColumnName = "roleID")})
    @ManyToMany(fetch=javax.persistence.FetchType.EAGER)
    private List<Roles> rolesList;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "userID")
    private Integer userID;
    @Basic(optional = false)
    @Column(name = "userName")
    private String userName;
    @Column(name = "userFirstName")
    private String userFirstName;
    @Column(name = "userLastName")
    private String userLastName;
    @Column(name = "userEmail")
    private String userEmail;
    @Column(name = "userExtension")
    private BigInteger userExtension;
    @Basic(optional = false)
    @Column(name = "userPassword")
    private String userPassword;
    @Basic(optional = false)
    @Column(name = "userActive")
    private boolean userActive = true;
/*
    @ManyToMany(mappedBy = "usersList")
    private List<Projects> projectsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Projects> projectsList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Thread> threadList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Pinnedposts> pinnedpostsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private List<Posts> postsList;
*/
    public User() {
    }

    public User(Integer userID) {
        this.userID = userID;
    }

    public User(Integer userID, String userName, String userPassword, boolean userActive) {
        this.userID = userID;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userActive = userActive;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public BigInteger getUserExtension() {
        return userExtension;
    }

    public void setUserExtension(BigInteger userExtension) {
        this.userExtension = userExtension;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public boolean getUserActive() {
        return userActive;
    }

    public void setUserActive(boolean userActive) {
        this.userActive = userActive;
    }

    public List<Roles> getRolesList() {
        return rolesList;
    }

    public void setRolesList(List<Roles> rolesList) {
        this.rolesList = rolesList;
    }
    
    public boolean hasRole(Integer roleID){
        
        for(Roles role:rolesList){
            if(Objects.equals(role.getRoleID(), roleID))
                return true;
        }
        return false;
    }

/*
    public List<Projects> getProjectsList() {
        return projectsList;
    }

    public void setProjectsList(List<Projects> projectsList) {
        this.projectsList = projectsList;
    }

    public List<Projects> getProjectsList1() {
        return projectsList1;
    }

    public void setProjectsList1(List<Projects> projectsList1) {
        this.projectsList1 = projectsList1;
    }

    public List<Thread> getThreadList() {
        return threadList;
    }

    public void setThreadList(List<Thread> threadList) {
        this.threadList = threadList;
    }

    public List<Pinnedposts> getPinnedpostsList() {
        return pinnedpostsList;
    }

    public void setPinnedpostsList(List<Pinnedposts> pinnedpostsList) {
        this.pinnedpostsList = pinnedpostsList;
    }

    public List<Posts> getPostsList() {
        return postsList;
    }

    public void setPostsList(List<Posts> postsList) {
        this.postsList = postsList;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.shawnmckee.devtalk.entities.Users[ userID=" + userID + " ]";
    }

}
