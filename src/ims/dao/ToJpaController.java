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
import ims.dto.To;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hades
 */
public class ToJpaController implements Serializable {

    public ToJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(To to) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Nhanvien nhanvienOrphanCheck = to.getNhanvien();
        if (nhanvienOrphanCheck != null) {
            To oldToOfNhanvien = nhanvienOrphanCheck.getTo();
            if (oldToOfNhanvien != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Nhanvien " + nhanvienOrphanCheck + " already has an item of type To whose nhanvien column cannot be null. Please make another selection for the nhanvien field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nhanvien nhanvien = to.getNhanvien();
            if (nhanvien != null) {
                nhanvien = em.getReference(nhanvien.getClass(), nhanvien.getIdnhanvien());
                to.setNhanvien(nhanvien);
            }
            em.persist(to);
            if (nhanvien != null) {
                nhanvien.setTo(to);
                nhanvien = em.merge(nhanvien);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTo(to.getMato()) != null) {
                throw new PreexistingEntityException("To " + to + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(To to) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            To persistentTo = em.find(To.class, to.getMato());
            Nhanvien nhanvienOld = persistentTo.getNhanvien();
            Nhanvien nhanvienNew = to.getNhanvien();
            List<String> illegalOrphanMessages = null;
            if (nhanvienNew != null && !nhanvienNew.equals(nhanvienOld)) {
                To oldToOfNhanvien = nhanvienNew.getTo();
                if (oldToOfNhanvien != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Nhanvien " + nhanvienNew + " already has an item of type To whose nhanvien column cannot be null. Please make another selection for the nhanvien field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (nhanvienNew != null) {
                nhanvienNew = em.getReference(nhanvienNew.getClass(), nhanvienNew.getIdnhanvien());
                to.setNhanvien(nhanvienNew);
            }
            to = em.merge(to);
            if (nhanvienOld != null && !nhanvienOld.equals(nhanvienNew)) {
                nhanvienOld.setTo(null);
                nhanvienOld = em.merge(nhanvienOld);
            }
            if (nhanvienNew != null && !nhanvienNew.equals(nhanvienOld)) {
                nhanvienNew.setTo(to);
                nhanvienNew = em.merge(nhanvienNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = to.getMato();
                if (findTo(id) == null) {
                    throw new NonexistentEntityException("The to with id " + id + " no longer exists.");
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
            To to;
            try {
                to = em.getReference(To.class, id);
                to.getMato();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The to with id " + id + " no longer exists.", enfe);
            }
            Nhanvien nhanvien = to.getNhanvien();
            if (nhanvien != null) {
                nhanvien.setTo(null);
                nhanvien = em.merge(nhanvien);
            }
            em.remove(to);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<To> findToEntities() {
        return findToEntities(true, -1, -1);
    }

    public List<To> findToEntities(int maxResults, int firstResult) {
        return findToEntities(false, maxResults, firstResult);
    }

    private List<To> findToEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(To.class));
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

    public To findTo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(To.class, id);
        } finally {
            em.close();
        }
    }

    public int getToCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<To> rt = cq.from(To.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
