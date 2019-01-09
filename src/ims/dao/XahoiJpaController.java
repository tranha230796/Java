/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dao;

import ims.dao.exceptions.IllegalOrphanException;
import ims.dao.exceptions.NonexistentEntityException;
import ims.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ims.dto.Nhanvien;
import ims.dto.Xahoi;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hades
 */
public class XahoiJpaController implements Serializable {

    public XahoiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Xahoi xahoi) throws PreexistingEntityException, Exception {
        if (xahoi.getNhanvienList() == null) {
            xahoi.setNhanvienList(new ArrayList<Nhanvien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Nhanvien> attachedNhanvienList = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienListNhanvienToAttach : xahoi.getNhanvienList()) {
                nhanvienListNhanvienToAttach = em.getReference(nhanvienListNhanvienToAttach.getClass(), nhanvienListNhanvienToAttach.getIdnhanvien());
                attachedNhanvienList.add(nhanvienListNhanvienToAttach);
            }
            xahoi.setNhanvienList(attachedNhanvienList);
            em.persist(xahoi);
            for (Nhanvien nhanvienListNhanvien : xahoi.getNhanvienList()) {
                Xahoi oldIdxahoiOfNhanvienListNhanvien = nhanvienListNhanvien.getIdxahoi();
                nhanvienListNhanvien.setIdxahoi(xahoi);
                nhanvienListNhanvien = em.merge(nhanvienListNhanvien);
                if (oldIdxahoiOfNhanvienListNhanvien != null) {
                    oldIdxahoiOfNhanvienListNhanvien.getNhanvienList().remove(nhanvienListNhanvien);
                    oldIdxahoiOfNhanvienListNhanvien = em.merge(oldIdxahoiOfNhanvienListNhanvien);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findXahoi(xahoi.getIdxahoi()) != null) {
                throw new PreexistingEntityException("Xahoi " + xahoi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Xahoi xahoi) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Xahoi persistentXahoi = em.find(Xahoi.class, xahoi.getIdxahoi());
            List<Nhanvien> nhanvienListOld = persistentXahoi.getNhanvienList();
            List<Nhanvien> nhanvienListNew = xahoi.getNhanvienList();
            List<String> illegalOrphanMessages = null;
            for (Nhanvien nhanvienListOldNhanvien : nhanvienListOld) {
                if (!nhanvienListNew.contains(nhanvienListOldNhanvien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nhanvien " + nhanvienListOldNhanvien + " since its idxahoi field is not nullable.");
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
            xahoi.setNhanvienList(nhanvienListNew);
            xahoi = em.merge(xahoi);
            for (Nhanvien nhanvienListNewNhanvien : nhanvienListNew) {
                if (!nhanvienListOld.contains(nhanvienListNewNhanvien)) {
                    Xahoi oldIdxahoiOfNhanvienListNewNhanvien = nhanvienListNewNhanvien.getIdxahoi();
                    nhanvienListNewNhanvien.setIdxahoi(xahoi);
                    nhanvienListNewNhanvien = em.merge(nhanvienListNewNhanvien);
                    if (oldIdxahoiOfNhanvienListNewNhanvien != null && !oldIdxahoiOfNhanvienListNewNhanvien.equals(xahoi)) {
                        oldIdxahoiOfNhanvienListNewNhanvien.getNhanvienList().remove(nhanvienListNewNhanvien);
                        oldIdxahoiOfNhanvienListNewNhanvien = em.merge(oldIdxahoiOfNhanvienListNewNhanvien);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = xahoi.getIdxahoi();
                if (findXahoi(id) == null) {
                    throw new NonexistentEntityException("The xahoi with id " + id + " no longer exists.");
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
            Xahoi xahoi;
            try {
                xahoi = em.getReference(Xahoi.class, id);
                xahoi.getIdxahoi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The xahoi with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Nhanvien> nhanvienListOrphanCheck = xahoi.getNhanvienList();
            for (Nhanvien nhanvienListOrphanCheckNhanvien : nhanvienListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Xahoi (" + xahoi + ") cannot be destroyed since the Nhanvien " + nhanvienListOrphanCheckNhanvien + " in its nhanvienList field has a non-nullable idxahoi field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(xahoi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Xahoi> findXahoiEntities() {
        return findXahoiEntities(true, -1, -1);
    }

    public List<Xahoi> findXahoiEntities(int maxResults, int firstResult) {
        return findXahoiEntities(false, maxResults, firstResult);
    }

    private List<Xahoi> findXahoiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Xahoi.class));
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

    public Xahoi findXahoi(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Xahoi.class, id);
        } finally {
            em.close();
        }
    }

    public int getXahoiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Xahoi> rt = cq.from(Xahoi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
