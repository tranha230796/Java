/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dao;

import ims.dao.exceptions.IllegalOrphanException;
import ims.dao.exceptions.NonexistentEntityException;
import ims.dao.exceptions.PreexistingEntityException;
import ims.dto.Doi;
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
public class DoiJpaController implements Serializable {

    public DoiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Doi doi) throws IllegalOrphanException, PreexistingEntityException, Exception {
        List<String> illegalOrphanMessages = null;
        Nhanvien nhanvienOrphanCheck = doi.getNhanvien();
        if (nhanvienOrphanCheck != null) {
            Doi oldDoiOfNhanvien = nhanvienOrphanCheck.getDoi();
            if (oldDoiOfNhanvien != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Nhanvien " + nhanvienOrphanCheck + " already has an item of type Doi whose nhanvien column cannot be null. Please make another selection for the nhanvien field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Nhanvien nhanvien = doi.getNhanvien();
            if (nhanvien != null) {
                nhanvien = em.getReference(nhanvien.getClass(), nhanvien.getIdnhanvien());
                doi.setNhanvien(nhanvien);
            }
            em.persist(doi);
            if (nhanvien != null) {
                nhanvien.setDoi(doi);
                nhanvien = em.merge(nhanvien);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDoi(doi.getMadoi()) != null) {
                throw new PreexistingEntityException("Doi " + doi + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Doi doi) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Doi persistentDoi = em.find(Doi.class, doi.getMadoi());
            Nhanvien nhanvienOld = persistentDoi.getNhanvien();
            Nhanvien nhanvienNew = doi.getNhanvien();
            List<String> illegalOrphanMessages = null;
            if (nhanvienNew != null && !nhanvienNew.equals(nhanvienOld)) {
                Doi oldDoiOfNhanvien = nhanvienNew.getDoi();
                if (oldDoiOfNhanvien != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Nhanvien " + nhanvienNew + " already has an item of type Doi whose nhanvien column cannot be null. Please make another selection for the nhanvien field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (nhanvienNew != null) {
                nhanvienNew = em.getReference(nhanvienNew.getClass(), nhanvienNew.getIdnhanvien());
                doi.setNhanvien(nhanvienNew);
            }
            doi = em.merge(doi);
            if (nhanvienOld != null && !nhanvienOld.equals(nhanvienNew)) {
                nhanvienOld.setDoi(null);
                nhanvienOld = em.merge(nhanvienOld);
            }
            if (nhanvienNew != null && !nhanvienNew.equals(nhanvienOld)) {
                nhanvienNew.setDoi(doi);
                nhanvienNew = em.merge(nhanvienNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = doi.getMadoi();
                if (findDoi(id) == null) {
                    throw new NonexistentEntityException("The doi with id " + id + " no longer exists.");
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
            Doi doi;
            try {
                doi = em.getReference(Doi.class, id);
                doi.getMadoi();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The doi with id " + id + " no longer exists.", enfe);
            }
            Nhanvien nhanvien = doi.getNhanvien();
            if (nhanvien != null) {
                nhanvien.setDoi(null);
                nhanvien = em.merge(nhanvien);
            }
            em.remove(doi);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Doi> findDoiEntities() {
        return findDoiEntities(true, -1, -1);
    }

    public List<Doi> findDoiEntities(int maxResults, int firstResult) {
        return findDoiEntities(false, maxResults, firstResult);
    }

    private List<Doi> findDoiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Doi.class));
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

    public Doi findDoi(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Doi.class, id);
        } finally {
            em.close();
        }
    }

    public int getDoiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Doi> rt = cq.from(Doi.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
