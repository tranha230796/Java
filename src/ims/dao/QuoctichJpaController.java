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
import ims.dto.Quoctich;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hades
 */
public class QuoctichJpaController implements Serializable {

    public QuoctichJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Quoctich quoctich) throws PreexistingEntityException, Exception {
        if (quoctich.getNhanvienCollection() == null) {
            quoctich.setNhanvienCollection(new ArrayList<Nhanvien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Nhanvien> attachedNhanvienCollection = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienCollectionNhanvienToAttach : quoctich.getNhanvienCollection()) {
                nhanvienCollectionNhanvienToAttach = em.getReference(nhanvienCollectionNhanvienToAttach.getClass(), nhanvienCollectionNhanvienToAttach.getIdnhanvien());
                attachedNhanvienCollection.add(nhanvienCollectionNhanvienToAttach);
            }
            quoctich.setNhanvienCollection(attachedNhanvienCollection);
            em.persist(quoctich);
            for (Nhanvien nhanvienCollectionNhanvien : quoctich.getNhanvienCollection()) {
                Quoctich oldIdquoctichOfNhanvienCollectionNhanvien = nhanvienCollectionNhanvien.getIdquoctich();
                nhanvienCollectionNhanvien.setIdquoctich(quoctich);
                nhanvienCollectionNhanvien = em.merge(nhanvienCollectionNhanvien);
                if (oldIdquoctichOfNhanvienCollectionNhanvien != null) {
                    oldIdquoctichOfNhanvienCollectionNhanvien.getNhanvienCollection().remove(nhanvienCollectionNhanvien);
                    oldIdquoctichOfNhanvienCollectionNhanvien = em.merge(oldIdquoctichOfNhanvienCollectionNhanvien);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findQuoctich(quoctich.getIdquoctich()) != null) {
                throw new PreexistingEntityException("Quoctich " + quoctich + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Quoctich quoctich) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Quoctich persistentQuoctich = em.find(Quoctich.class, quoctich.getIdquoctich());
            Collection<Nhanvien> nhanvienCollectionOld = persistentQuoctich.getNhanvienCollection();
            Collection<Nhanvien> nhanvienCollectionNew = quoctich.getNhanvienCollection();
            List<String> illegalOrphanMessages = null;
            for (Nhanvien nhanvienCollectionOldNhanvien : nhanvienCollectionOld) {
                if (!nhanvienCollectionNew.contains(nhanvienCollectionOldNhanvien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nhanvien " + nhanvienCollectionOldNhanvien + " since its idquoctich field is not nullable.");
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
            quoctich.setNhanvienCollection(nhanvienCollectionNew);
            quoctich = em.merge(quoctich);
            for (Nhanvien nhanvienCollectionNewNhanvien : nhanvienCollectionNew) {
                if (!nhanvienCollectionOld.contains(nhanvienCollectionNewNhanvien)) {
                    Quoctich oldIdquoctichOfNhanvienCollectionNewNhanvien = nhanvienCollectionNewNhanvien.getIdquoctich();
                    nhanvienCollectionNewNhanvien.setIdquoctich(quoctich);
                    nhanvienCollectionNewNhanvien = em.merge(nhanvienCollectionNewNhanvien);
                    if (oldIdquoctichOfNhanvienCollectionNewNhanvien != null && !oldIdquoctichOfNhanvienCollectionNewNhanvien.equals(quoctich)) {
                        oldIdquoctichOfNhanvienCollectionNewNhanvien.getNhanvienCollection().remove(nhanvienCollectionNewNhanvien);
                        oldIdquoctichOfNhanvienCollectionNewNhanvien = em.merge(oldIdquoctichOfNhanvienCollectionNewNhanvien);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = quoctich.getIdquoctich();
                if (findQuoctich(id) == null) {
                    throw new NonexistentEntityException("The quoctich with id " + id + " no longer exists.");
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
            Quoctich quoctich;
            try {
                quoctich = em.getReference(Quoctich.class, id);
                quoctich.getIdquoctich();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The quoctich with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Nhanvien> nhanvienCollectionOrphanCheck = quoctich.getNhanvienCollection();
            for (Nhanvien nhanvienCollectionOrphanCheckNhanvien : nhanvienCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Quoctich (" + quoctich + ") cannot be destroyed since the Nhanvien " + nhanvienCollectionOrphanCheckNhanvien + " in its nhanvienCollection field has a non-nullable idquoctich field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(quoctich);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Quoctich> findQuoctichEntities() {
        return findQuoctichEntities(true, -1, -1);
    }

    public List<Quoctich> findQuoctichEntities(int maxResults, int firstResult) {
        return findQuoctichEntities(false, maxResults, firstResult);
    }

    private List<Quoctich> findQuoctichEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Quoctich.class));
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

    public Quoctich findQuoctich(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Quoctich.class, id);
        } finally {
            em.close();
        }
    }

    public int getQuoctichCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Quoctich> rt = cq.from(Quoctich.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
