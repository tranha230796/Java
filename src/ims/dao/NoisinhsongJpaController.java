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
import ims.dto.Noisinhsong;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hades
 */
public class NoisinhsongJpaController implements Serializable {

    public NoisinhsongJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Noisinhsong noisinhsong) throws PreexistingEntityException, Exception {
        if (noisinhsong.getNhanvienCollection() == null) {
            noisinhsong.setNhanvienCollection(new ArrayList<Nhanvien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Nhanvien> attachedNhanvienCollection = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienCollectionNhanvienToAttach : noisinhsong.getNhanvienCollection()) {
                nhanvienCollectionNhanvienToAttach = em.getReference(nhanvienCollectionNhanvienToAttach.getClass(), nhanvienCollectionNhanvienToAttach.getIdnhanvien());
                attachedNhanvienCollection.add(nhanvienCollectionNhanvienToAttach);
            }
            noisinhsong.setNhanvienCollection(attachedNhanvienCollection);
            em.persist(noisinhsong);
            for (Nhanvien nhanvienCollectionNhanvien : noisinhsong.getNhanvienCollection()) {
                Noisinhsong oldIdnoisinhsongOfNhanvienCollectionNhanvien = nhanvienCollectionNhanvien.getIdnoisinhsong();
                nhanvienCollectionNhanvien.setIdnoisinhsong(noisinhsong);
                nhanvienCollectionNhanvien = em.merge(nhanvienCollectionNhanvien);
                if (oldIdnoisinhsongOfNhanvienCollectionNhanvien != null) {
                    oldIdnoisinhsongOfNhanvienCollectionNhanvien.getNhanvienCollection().remove(nhanvienCollectionNhanvien);
                    oldIdnoisinhsongOfNhanvienCollectionNhanvien = em.merge(oldIdnoisinhsongOfNhanvienCollectionNhanvien);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNoisinhsong(noisinhsong.getIdnoisinhsong()) != null) {
                throw new PreexistingEntityException("Noisinhsong " + noisinhsong + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Noisinhsong noisinhsong) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Noisinhsong persistentNoisinhsong = em.find(Noisinhsong.class, noisinhsong.getIdnoisinhsong());
            Collection<Nhanvien> nhanvienCollectionOld = persistentNoisinhsong.getNhanvienCollection();
            Collection<Nhanvien> nhanvienCollectionNew = noisinhsong.getNhanvienCollection();
            List<String> illegalOrphanMessages = null;
            for (Nhanvien nhanvienCollectionOldNhanvien : nhanvienCollectionOld) {
                if (!nhanvienCollectionNew.contains(nhanvienCollectionOldNhanvien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nhanvien " + nhanvienCollectionOldNhanvien + " since its idnoisinhsong field is not nullable.");
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
            noisinhsong.setNhanvienCollection(nhanvienCollectionNew);
            noisinhsong = em.merge(noisinhsong);
            for (Nhanvien nhanvienCollectionNewNhanvien : nhanvienCollectionNew) {
                if (!nhanvienCollectionOld.contains(nhanvienCollectionNewNhanvien)) {
                    Noisinhsong oldIdnoisinhsongOfNhanvienCollectionNewNhanvien = nhanvienCollectionNewNhanvien.getIdnoisinhsong();
                    nhanvienCollectionNewNhanvien.setIdnoisinhsong(noisinhsong);
                    nhanvienCollectionNewNhanvien = em.merge(nhanvienCollectionNewNhanvien);
                    if (oldIdnoisinhsongOfNhanvienCollectionNewNhanvien != null && !oldIdnoisinhsongOfNhanvienCollectionNewNhanvien.equals(noisinhsong)) {
                        oldIdnoisinhsongOfNhanvienCollectionNewNhanvien.getNhanvienCollection().remove(nhanvienCollectionNewNhanvien);
                        oldIdnoisinhsongOfNhanvienCollectionNewNhanvien = em.merge(oldIdnoisinhsongOfNhanvienCollectionNewNhanvien);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = noisinhsong.getIdnoisinhsong();
                if (findNoisinhsong(id) == null) {
                    throw new NonexistentEntityException("The noisinhsong with id " + id + " no longer exists.");
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
            Noisinhsong noisinhsong;
            try {
                noisinhsong = em.getReference(Noisinhsong.class, id);
                noisinhsong.getIdnoisinhsong();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The noisinhsong with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Nhanvien> nhanvienCollectionOrphanCheck = noisinhsong.getNhanvienCollection();
            for (Nhanvien nhanvienCollectionOrphanCheckNhanvien : nhanvienCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Noisinhsong (" + noisinhsong + ") cannot be destroyed since the Nhanvien " + nhanvienCollectionOrphanCheckNhanvien + " in its nhanvienCollection field has a non-nullable idnoisinhsong field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(noisinhsong);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Noisinhsong> findNoisinhsongEntities() {
        return findNoisinhsongEntities(true, -1, -1);
    }

    public List<Noisinhsong> findNoisinhsongEntities(int maxResults, int firstResult) {
        return findNoisinhsongEntities(false, maxResults, firstResult);
    }

    private List<Noisinhsong> findNoisinhsongEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Noisinhsong.class));
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

    public Noisinhsong findNoisinhsong(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Noisinhsong.class, id);
        } finally {
            em.close();
        }
    }

    public int getNoisinhsongCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Noisinhsong> rt = cq.from(Noisinhsong.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
