/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dal;

import ims.dto.Dantoc;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Hades
 */
public class BaseDAL {
    protected Session session;
    protected SessionFactory sessionFactory;
    
    public BaseDAL(Serializable T) throws Exception{
            connect(T);
    }

    private void connect(Serializable T) throws Exception {
            sessionFactory = new Configuration().configure().addAnnotatedClass(T.getClass()).buildSessionFactory();
            session = sessionFactory.openSession();
    }
    public void close() throws Exception {
		if (session != null) {
                    session.close();
                    sessionFactory.close();
		}
    }
}
