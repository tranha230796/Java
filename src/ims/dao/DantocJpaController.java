/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dao;

import ims.dao.exceptions.IllegalOrphanException;
import ims.dao.exceptions.NonexistentEntityException;
import ims.dao.exceptions.PreexistingEntityException;
import ims.dto.Dantoc;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ims.dto.Nhanvien;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hades
 */
public class DantocJpaController implements Serializable {

    public DantocJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Dantoc dantoc) throws PreexistingEntityException, Exception {
        if (dantoc.getNhanvienList() == null) {
            dantoc.setNhanvienList(new ArrayList<Nhanvien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Nhanvien> attachedNhanvienList = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienListNhanvienToAttach : dantoc.getNhanvienList()) {
                nhanvienListNhanvienToAttach = em.getReference(nhanvienListNhanvienToAttach.getClass(), nhanvienListNhanvienToAttach.getIdnhanvien());
                attachedNhanvienList.add(nhanvienListNhanvienToAttach);
            }
            dantoc.setNhanvienList(attachedNhanvienList);
            em.persist(dantoc);
            for (Nhanvien nhanvienListNhanvien : dantoc.getNhanvienList()) {
                Dantoc oldIddantocOfNhanvienListNhanvien = nhanvienListNhanvien.getIddantoc();
                nhanvienListNhanvien.setIddantoc(dantoc);
                nhanvienListNhanvien = em.merge(nhanvienListNhanvien);
                if (oldIddantocOfNhanvienListNhanvien != null) {
                    oldIddantocOfNhanvienListNhanvien.getNhanvienList().remove(nhanvienListNhanvien);
                    oldIddantocOfNhanvienListNhanvien = em.merge(oldIddantocOfNhanvienListNhanvien);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDantoc(dantoc.getIddantoc()) != null) {
                throw new PreexistingEntityException("Dantoc " + dantoc + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Dantoc dantoc) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dantoc persistentDantoc = em.find(Dantoc.class, dantoc.getIddantoc());
            List<Nhanvien> nhanvienListOld = persistentDantoc.getNhanvienList();
            List<Nhanvien> nhanvienListNew = dantoc.getNhanvienList();
            List<String> illegalOrphanMessages = null;
            for (Nhanvien nhanvienListOldNhanvien : nhanvienListOld) {
                if (!nhanvienListNew.contains(nhanvienListOldNhanvien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nhanvien " + nhanvienListOldNhanvien + " since its iddantoc field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Nhanvien> attachedNhanvienListNew = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienListNewNhanvienToAttach : nhanvienListNew) {
                nhanvienListNewNhanvienToAttach = em.getReference(nhanvienListNewNhanvienToAttach.getClass(), nhanvienListNewNhanvienToAttach.getIdnhanvien());
                attachedNhanvienListNew.add(nhanvienListNewNhanvienToAttach);
            }
            nhanvienListNew = attachedNhanvienListNew;
            dantoc.setNhanvienList(nhanvienListNew);
            dantoc = em.merge(dantoc);
            for (Nhanvien nhanvienListNewNhanvien : nhanvienListNew) {
                if (!nhanvienListOld.contains(nhanvienListNewNhanvien)) {
                    Dantoc oldIddantocOfNhanvienListNewNhanvien = nhanvienListNewNhanvien.getIddantoc();
                    nhanvienListNewNhanvien.setIddantoc(dantoc);
                    nhanvienListNewNhanvien = em.merge(nhanvienListNewNhanvien);
                    if (oldIddantocOfNhanvienListNewNhanvien != null && !oldIddantocOfNhanvienListNewNhanvien.equals(dantoc)) {
                        oldIddantocOfNhanvienListNewNhanvien.getNhanvienList().remove(nhanvienListNewNhanvien);
                        oldIddantocOfNhanvienListNewNhanvien = em.merge(oldIddantocOfNhanvienListNewNhanvien);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = dantoc.getIddantoc();
                if (findDantoc(id) == null) {
                    throw new NonexistentEntityException("The dantoc with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Dantoc dantoc;
            try {
                dantoc = em.getReference(Dantoc.class, id);
                dantoc.getIddantoc();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The dantoc with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Nhanvien> nhanvienListOrphanCheck = dantoc.getNhanvienList();
            for (Nhanvien nhanvienListOrphanCheckNhanvien : nhanvienListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Dantoc (" + dantoc + ") cannot be destroyed since the Nhanvien " + nhanvienListOrphanCheckNhanvien + " in its nhanvienList field has a non-nullable iddantoc field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(dantoc);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Dantoc> findDantocEntities() {
        return findDantocEntities(true, -1, -1);
    }

    public List<Dantoc> findDantocEntities(int maxResults, int firstResult) {
        return findDantocEntities(false, maxResults, firstResult);
    }

    private List<Dantoc> findDantocEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Dantoc.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Dantoc findDantoc(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Dantoc.class, id);
        } finally {
            em.close();
        }
    }

    public int getDantocCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Dantoc> rt = cq.from(Dantoc.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
