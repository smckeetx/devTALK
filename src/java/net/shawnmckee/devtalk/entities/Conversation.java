/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "thread")
@NamedQueries({
    @NamedQuery(name = "Conversation.findAll", query = "SELECT t FROM Conversation t"),
    @NamedQuery(name = "Conversation.findByConversationID", query = "SELECT t FROM Conversation t WHERE t.threadID = :threadID"),
    @NamedQuery(name = "Conversation.findByProjectID", query = "SELECT t FROM Conversation t WHERE t.projectID = :projectID"),
    @NamedQuery(name = "Conversation.findByUserID", query = "SELECT t FROM Conversation t WHERE t.userID = :userID"),
    @NamedQuery(name = "Conversation.findByConversationTitle", query = "SELECT t FROM Conversation t WHERE t.threadTitle = :threadTitle"),
    @NamedQuery(name = "Conversation.findByConversationActive", query = "SELECT t FROM Conversation t WHERE t.threadActive = :threadActive")})
    public class Conversation implements Serializable {
    private static final long serialVersionUID = 1L;
    @JoinTable(name = "userConversation", joinColumns = {
        @JoinColumn(name = "threadID", referencedColumnName = "threadID")}, inverseJoinColumns = {
        @JoinColumn(name = "userID", referencedColumnName = "userID")})
    @ManyToMany(cascade=CascadeType.PERSIST)
    private List<User> userList;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "threadID")
    private Integer threadID;
    @Basic(optional = false)
    @Column(name = "projectID")
    private Integer projectID;
    @Basic(optional = false)
    @Column(name = "userID")
    private Integer userID;
    @Basic(optional = false)
    @Column(name = "threadTitle")
    private String threadTitle;
    @Basic(optional = false)
    @Column(name = "threadActive")
    private boolean threadActive;
    @Basic(optional = false)
    @Column(name = "threadPublic")
    private boolean threadPublic;
    @Basic(optional = false)
    @Column(name = "threadLocked")
    private boolean threadLocked;

    public Conversation() {
    }

    public Conversation(String threadTitle, Integer projectID, Integer userID, boolean threadActive, boolean threadPublic) {
        this.threadTitle = threadTitle;
        this.userID = userID;
        this.projectID = projectID;
        this.threadActive = threadActive;
        this.threadActive = threadPublic;
    }

    public Integer getThreadID(){
        return threadID;
    }
    
    public Integer getUserID(){
        return userID;
    }
    
    public String getThreadTitle() {
        return threadTitle;
    }

    public void setThreadTitle(String threadTitle) {
        this.threadTitle = threadTitle;
    }

    public boolean getThreadActive() {
        return threadActive;
    }

    public void setThreadActive(boolean threadActive) {
        this.threadActive = threadActive;
    }

    public boolean getThreadPublic() {
        return threadActive;
    }

    public void setThreadPublic(boolean threadPublic) {
        this.threadPublic = threadPublic;
    }

    public boolean getThreadLocked() {
        return threadActive;
    }

    public void setThreadLocked(boolean threadLocked) {
        this.threadLocked = threadLocked;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (threadID != null ? hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conversation)) {
            return false;
        }
        Conversation other = (Conversation) object;
        if ((this.threadID == null && other.threadID != null) || (this.threadID != null && !this.equals(other.threadID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.shawnmckee.devtalk.entities.Conversation[ threadID=" + threadID + " ]";
    }

    }
