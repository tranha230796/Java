/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dal;


import ims.dto.Dantoc;
import java.io.Serializable;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

/**
 *
 * @author Hades
 */
public class DantocDAL extends BaseDAL{
        public DantocDAL() throws Exception{
        super(Dantoc.class);
}
    public Integer save(Object obj) throws Exception {
	session.beginTransaction();
	Integer id = (Integer) session.save(obj);
        session.getTransaction().commit();
        return id;
    }
	
    public void delete(Object obj) throws Exception {
	session.beginTransaction();
	session.delete(obj);
	session.getTransaction().commit();
    }
        
    public List<Dantoc> list() {
        List<Dantoc> query = (List<Dantoc>) session.createQuery("FROM dantoc").list();
        return query;
    }
}
