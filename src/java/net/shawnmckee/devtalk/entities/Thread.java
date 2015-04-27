/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author smckee
 */
@Entity
@Table(name = "thread")
@NamedQueries({
    @NamedQuery(name = "Thread.findAll", query = "SELECT t FROM Thread t"),
    @NamedQuery(name = "Thread.findByThreadID", query = "SELECT t FROM Thread t WHERE t.threadID = :threadID"),
    @NamedQuery(name = "Thread.findByProjectID", query = "SELECT t FROM Thread t WHERE t.projectID = :projectID"),
    @NamedQuery(name = "Thread.findByUserID", query = "SELECT t FROM Thread t WHERE t.userID = :userID"),
    @NamedQuery(name = "Thread.findByThreadTitle", query = "SELECT t FROM Thread t WHERE t.threadTitle = :threadTitle"),
    @NamedQuery(name = "Thread.findByThreadActive", query = "SELECT t FROM Thread t WHERE t.threadActive = :threadActive")})
public class Thread implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public Thread() {
    }

    public Thread(String threadTitle, Integer projectID, Integer userID, boolean threadActive) {
        this.threadTitle = threadTitle;
        this.userID = userID;
        this.projectID = projectID;
        this.threadActive = threadActive;
    }

    public Integer getThreadID(){
        return threadID;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (threadID != null ? hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Thread)) {
            return false;
        }
        Thread other = (Thread) object;
        if ((this.threadID == null && other.threadID != null) || (this.threadID != null && !this.equals(other.threadID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.shawnmckee.devtalk.entities.Thread[ threadID=" + threadID + " ]";
    }
    
}
