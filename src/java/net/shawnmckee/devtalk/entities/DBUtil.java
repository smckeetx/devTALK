/*
 * Copyright 2015 Shawn McKee All Rights Reserved
 */
package net.shawnmckee.devtalk.entities;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author smckee
 */
public class DBUtil {
    public DBUtil(){
        
    }
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("devTALKPU");

    public static EntityManagerFactory getEmFactory(){
        return emf;
    }
}
