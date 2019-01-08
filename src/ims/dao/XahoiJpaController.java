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
import java.util.Collection;
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
        if (xahoi.getNhanvienCollection() == null) {
            xahoi.setNhanvienCollection(new ArrayList<Nhanvien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Nhanvien> attachedNhanvienCollection = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienCollectionNhanvienToAttach : xahoi.getNhanvienCollection()) {
                nhanvienCollectionNhanvienToAttach = em.getReference(nhanvienCollectionNhanvienToAttach.getClass(), nhanvienCollectionNhanvienToAttach.getIdnhanvien());
                attachedNhanvienCollection.add(nhanvienCollectionNhanvienToAttach);
            }
            xahoi.setNhanvienCollection(attachedNhanvienCollection);
            em.persist(xahoi);
            for (Nhanvien nhanvienCollectionNhanvien : xahoi.getNhanvienCollection()) {
                Xahoi oldIdxahoiOfNhanvienCollectionNhanvien = nhanvienCollectionNhanvien.getIdxahoi();
                nhanvienCollectionNhanvien.setIdxahoi(xahoi);
                nhanvienCollectionNhanvien = em.merge(nhanvienCollectionNhanvien);
                if (oldIdxahoiOfNhanvienCollectionNhanvien != null) {
                    oldIdxahoiOfNhanvienCollectionNhanvien.getNhanvienCollection().remove(nhanvienCollectionNhanvien);
                    oldIdxahoiOfNhanvienCollectionNhanvien = em.merge(oldIdxahoiOfNhanvienCollectionNhanvien);
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
            Collection<Nhanvien> nhanvienCollectionOld = persistentXahoi.getNhanvienCollection();
            Collection<Nhanvien> nhanvienCollectionNew = xahoi.getNhanvienCollection();
            List<String> illegalOrphanMessages = null;
            for (Nhanvien nhanvienCollectionOldNhanvien : nhanvienCollectionOld) {
                if (!nhanvienCollectionNew.contains(nhanvienCollectionOldNhanvien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nhanvien " + nhanvienCollectionOldNhanvien + " since its idxahoi field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Nhanvien> attachedNhanvienCollectionNew = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienCollectionNewNhanvienToAttach : nhanvienCollectionNew) {
                nhanvienCollectionNewNhanvienToAttach = em.getReference(nhanvienCollectionNewNhanvienToAttach.getClass(), nhanvienCollectionNewNhanvienToAttach.getIdnhanvien());
                attachedNhanvienCollectionNew.add(nhanvienCollectionNewNhanvienToAttach);
            }
            nhanvienCollectionNew = attachedNhanvienCollectionNew;
            xahoi.setNhanvienCollection(nhanvienCollectionNew);
            xahoi = em.merge(xahoi);
            for (Nhanvien nhanvienCollectionNewNhanvien : nhanvienCollectionNew) {
                if (!nhanvienCollectionOld.contains(nhanvienCollectionNewNhanvien)) {
                    Xahoi oldIdxahoiOfNhanvienCollectionNewNhanvien = nhanvienCollectionNewNhanvien.getIdxahoi();
                    nhanvienCollectionNewNhanvien.setIdxahoi(xahoi);
                    nhanvienCollectionNewNhanvien = em.merge(nhanvienCollectionNewNhanvien);
                    if (oldIdxahoiOfNhanvienCollectionNewNhanvien != null && !oldIdxahoiOfNhanvienCollectionNewNhanvien.equals(xahoi)) {
                        oldIdxahoiOfNhanvienCollectionNewNhanvien.getNhanvienCollection().remove(nhanvienCollectionNewNhanvien);
                        oldIdxahoiOfNhanvienCollectionNewNhanvien = em.merge(oldIdxahoiOfNhanvienCollectionNewNhanvien);
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
            Collection<Nhanvien> nhanvienCollectionOrphanCheck = xahoi.getNhanvienCollection();
            for (Nhanvien nhanvienCollectionOrphanCheckNhanvien : nhanvienCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Xahoi (" + xahoi + ") cannot be destroyed since the Nhanvien " + nhanvienCollectionOrphanCheckNhanvien + " in its nhanvienCollection field has a non-nullable idxahoi field.");
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
