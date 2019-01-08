/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dao;

import ims.dao.exceptions.IllegalOrphanException;
import ims.dao.exceptions.NonexistentEntityException;
import ims.dao.exceptions.PreexistingEntityException;
import ims.dto.Giadinh;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ims.dto.Nhanvien;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hades
 */
public class GiadinhJpaController implements Serializable {

    public GiadinhJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Giadinh giadinh) throws PreexistingEntityException, Exception {
        if (giadinh.getNhanvienCollection() == null) {
            giadinh.setNhanvienCollection(new ArrayList<Nhanvien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Nhanvien> attachedNhanvienCollection = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienCollectionNhanvienToAttach : giadinh.getNhanvienCollection()) {
                nhanvienCollectionNhanvienToAttach = em.getReference(nhanvienCollectionNhanvienToAttach.getClass(), nhanvienCollectionNhanvienToAttach.getIdnhanvien());
                attachedNhanvienCollection.add(nhanvienCollectionNhanvienToAttach);
            }
            giadinh.setNhanvienCollection(attachedNhanvienCollection);
            em.persist(giadinh);
            for (Nhanvien nhanvienCollectionNhanvien : giadinh.getNhanvienCollection()) {
                Giadinh oldIdgiadinhOfNhanvienCollectionNhanvien = nhanvienCollectionNhanvien.getIdgiadinh();
                nhanvienCollectionNhanvien.setIdgiadinh(giadinh);
                nhanvienCollectionNhanvien = em.merge(nhanvienCollectionNhanvien);
                if (oldIdgiadinhOfNhanvienCollectionNhanvien != null) {
                    oldIdgiadinhOfNhanvienCollectionNhanvien.getNhanvienCollection().remove(nhanvienCollectionNhanvien);
                    oldIdgiadinhOfNhanvienCollectionNhanvien = em.merge(oldIdgiadinhOfNhanvienCollectionNhanvien);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findGiadinh(giadinh.getIdgiadinh()) != null) {
                throw new PreexistingEntityException("Giadinh " + giadinh + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Giadinh giadinh) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Giadinh persistentGiadinh = em.find(Giadinh.class, giadinh.getIdgiadinh());
            Collection<Nhanvien> nhanvienCollectionOld = persistentGiadinh.getNhanvienCollection();
            Collection<Nhanvien> nhanvienCollectionNew = giadinh.getNhanvienCollection();
            List<String> illegalOrphanMessages = null;
            for (Nhanvien nhanvienCollectionOldNhanvien : nhanvienCollectionOld) {
                if (!nhanvienCollectionNew.contains(nhanvienCollectionOldNhanvien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nhanvien " + nhanvienCollectionOldNhanvien + " since its idgiadinh field is not nullable.");
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
            giadinh.setNhanvienCollection(nhanvienCollectionNew);
            giadinh = em.merge(giadinh);
            for (Nhanvien nhanvienCollectionNewNhanvien : nhanvienCollectionNew) {
                if (!nhanvienCollectionOld.contains(nhanvienCollectionNewNhanvien)) {
                    Giadinh oldIdgiadinhOfNhanvienCollectionNewNhanvien = nhanvienCollectionNewNhanvien.getIdgiadinh();
                    nhanvienCollectionNewNhanvien.setIdgiadinh(giadinh);
                    nhanvienCollectionNewNhanvien = em.merge(nhanvienCollectionNewNhanvien);
                    if (oldIdgiadinhOfNhanvienCollectionNewNhanvien != null && !oldIdgiadinhOfNhanvienCollectionNewNhanvien.equals(giadinh)) {
                        oldIdgiadinhOfNhanvienCollectionNewNhanvien.getNhanvienCollection().remove(nhanvienCollectionNewNhanvien);
                        oldIdgiadinhOfNhanvienCollectionNewNhanvien = em.merge(oldIdgiadinhOfNhanvienCollectionNewNhanvien);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = giadinh.getIdgiadinh();
                if (findGiadinh(id) == null) {
                    throw new NonexistentEntityException("The giadinh with id " + id + " no longer exists.");
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
            Giadinh giadinh;
            try {
                giadinh = em.getReference(Giadinh.class, id);
                giadinh.getIdgiadinh();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The giadinh with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Nhanvien> nhanvienCollectionOrphanCheck = giadinh.getNhanvienCollection();
            for (Nhanvien nhanvienCollectionOrphanCheckNhanvien : nhanvienCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Giadinh (" + giadinh + ") cannot be destroyed since the Nhanvien " + nhanvienCollectionOrphanCheckNhanvien + " in its nhanvienCollection field has a non-nullable idgiadinh field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(giadinh);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Giadinh> findGiadinhEntities() {
        return findGiadinhEntities(true, -1, -1);
    }

    public List<Giadinh> findGiadinhEntities(int maxResults, int firstResult) {
        return findGiadinhEntities(false, maxResults, firstResult);
    }

    private List<Giadinh> findGiadinhEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Giadinh.class));
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

    public Giadinh findGiadinh(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Giadinh.class, id);
        } finally {
            em.close();
        }
    }

    public int getGiadinhCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Giadinh> rt = cq.from(Giadinh.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
