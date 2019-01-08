/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.NhanvienDAL;
import ims.dto.Nhanvien;
import java.util.List;

/**
 *
 * @author Hades
 */
public class NhanvienBLL {
    private NhanvienDAL dal;
    
    public NhanvienBLL() throws Exception{
        dal = new NhanvienDAL();
    }
    public List<Nhanvien> listAll()  {
		return dal.list();
	}
	
	public Integer save(Nhanvien nv) throws Exception {		            
            return dal.save(nv);		                          
	}
	
	public void delete(Nhanvien nv) throws Exception {
		dal.delete(nv);
	}
}
