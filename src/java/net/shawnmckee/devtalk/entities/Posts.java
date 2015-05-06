/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author smckee
 */
@Entity
@Table(name = "posts")
@NamedQueries({
    @NamedQuery(name = "Posts.findAll", query = "SELECT p FROM Posts p WHERE p.active = true ORDER BY p.threadID, p.postTime"),
    @NamedQuery(name = "Posts.findByPostID", query = "SELECT p FROM Posts p WHERE p.postID = :postID"),
    @NamedQuery(name = "Posts.findByThreadID", query = "SELECT p FROM Posts p WHERE p.active = true  AND p.threadID = :threadID ORDER BY p.postTime DESC"),
    @NamedQuery(name = "Posts.findByUserID", query = "SELECT p FROM Posts p WHERE p.active = true  AND p.userID = :userID ORDER BY p.threadID, p.postTime DESC"),
    @NamedQuery(name = "Posts.findByPostTime", query = "SELECT p FROM Posts p WHERE p.active = true  AND p.postTime >= :postTime")})
public class Posts implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "postID")
    private Integer postID;
    @Basic(optional = false)
    @Column(name = "userID")
    private Integer userID;
    @JoinColumn(name = "userID", referencedColumnName = "userID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @Basic(optional = false)
    @Column(name = "threadID")
    private Integer threadID;
    @Basic(optional = false)
    @Column(name = "postContent")
    private String postContent;
    @Basic(optional = false)
    @Column(name = "postTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date postTime;
    @Column(name = "active")
    private boolean active = true;

    public Posts() {
    }

    public Posts(Integer threadID, Integer userID, String postContent) {
        this.threadID = threadID;
        this.userID = userID;
        this.postContent = postContent;
        this.postTime = new Date(System.currentTimeMillis());
        
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Date getPostTime() {
        return postTime;
    }
    
    public Integer getUserID(){
        return userID;
    }
    
    public Integer getPostID(){
        return postID;
    }
    
    public User getUser(){
        return user;
    }

    public Boolean getActive(){
        return active;
    }

    public void setActive(Boolean active){
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (postID != null ? postID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Posts)) {
            return false;
        }
        Posts other = (Posts) object;
        if ((this.postID == null && other.postID != null) || (this.postID != null && !this.postID.equals(other.postID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "net.shawnmckee.devtalk.entities.Posts[ postsPK=" + postID + " ]";
    }
    
}
