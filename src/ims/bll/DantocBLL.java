/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.bll;

import ims.dal.DantocDAL;
import ims.dao.DantocJpaController;
import ims.dto.Dantoc;
import java.util.List;
import javax.persistence.Persistence;

/**
 *
 * @author Hades
 */
public class DantocBLL {
    private DantocDAL dal;
    
    public DantocBLL() throws Exception{
        dal = new DantocDAL();
    }
    public List<Dantoc> listAll()  {
		return new DantocJpaController(Persistence.createEntityManagerFactory("InventoryManagementPU")).findDantocEntities();
	}
	
	public Integer save(Dantoc dt) throws Exception {		            
            return dal.save(dt);		                          
	}
	
	public void delete(Dantoc dt) throws Exception {
		dal.delete(dt);
	}
}
