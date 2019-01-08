/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dao;

import ims.dao.exceptions.NonexistentEntityException;
import ims.dao.exceptions.PreexistingEntityException;
import ims.dto.Tinhtrangsuckhoe;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Hades
 */
public class TinhtrangsuckhoeJpaController implements Serializable {

    public TinhtrangsuckhoeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tinhtrangsuckhoe tinhtrangsuckhoe) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(tinhtrangsuckhoe);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTinhtrangsuckhoe(tinhtrangsuckhoe.getIdsuckhoe()) != null) {
                throw new PreexistingEntityException("Tinhtrangsuckhoe " + tinhtrangsuckhoe + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tinhtrangsuckhoe tinhtrangsuckhoe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            tinhtrangsuckhoe = em.merge(tinhtrangsuckhoe);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tinhtrangsuckhoe.getIdsuckhoe();
                if (findTinhtrangsuckhoe(id) == null) {
                    throw new NonexistentEntityException("The tinhtrangsuckhoe with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tinhtrangsuckhoe tinhtrangsuckhoe;
            try {
                tinhtrangsuckhoe = em.getReference(Tinhtrangsuckhoe.class, id);
                tinhtrangsuckhoe.getIdsuckhoe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tinhtrangsuckhoe with id " + id + " no longer exists.", enfe);
            }
            em.remove(tinhtrangsuckhoe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tinhtrangsuckhoe> findTinhtrangsuckhoeEntities() {
        return findTinhtrangsuckhoeEntities(true, -1, -1);
    }

    public List<Tinhtrangsuckhoe> findTinhtrangsuckhoeEntities(int maxResults, int firstResult) {
        return findTinhtrangsuckhoeEntities(false, maxResults, firstResult);
    }

    private List<Tinhtrangsuckhoe> findTinhtrangsuckhoeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tinhtrangsuckhoe.class));
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

    public Tinhtrangsuckhoe findTinhtrangsuckhoe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tinhtrangsuckhoe.class, id);
        } finally {
            em.close();
        }
    }

    public int getTinhtrangsuckhoeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tinhtrangsuckhoe> rt = cq.from(Tinhtrangsuckhoe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
