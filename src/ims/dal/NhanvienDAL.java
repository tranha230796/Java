/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dal;

import ims.dto.Nhanvien;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Hades
 */
public class NhanvienDAL extends BaseDAL{
    public NhanvienDAL() throws Exception{
        super(Nhanvien.class);
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
        
    public List<Nhanvien> list() {
        List<Nhanvien> result = new ArrayList<Nhanvien>();
        result = session.createQuery("FROM nhanvien").list();
        return result;
    }
}
