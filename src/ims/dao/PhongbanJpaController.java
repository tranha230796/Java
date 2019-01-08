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
import ims.dto.Phongban;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hades
 */
public class PhongbanJpaController implements Serializable {

    public PhongbanJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Phongban phongban) throws PreexistingEntityException, Exception {
        if (phongban.getNhanvienCollection() == null) {
            phongban.setNhanvienCollection(new ArrayList<Nhanvien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Nhanvien> attachedNhanvienCollection = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienCollectionNhanvienToAttach : phongban.getNhanvienCollection()) {
                nhanvienCollectionNhanvienToAttach = em.getReference(nhanvienCollectionNhanvienToAttach.getClass(), nhanvienCollectionNhanvienToAttach.getIdnhanvien());
                attachedNhanvienCollection.add(nhanvienCollectionNhanvienToAttach);
            }
            phongban.setNhanvienCollection(attachedNhanvienCollection);
            em.persist(phongban);
            for (Nhanvien nhanvienCollectionNhanvien : phongban.getNhanvienCollection()) {
                Phongban oldIdphongbanOfNhanvienCollectionNhanvien = nhanvienCollectionNhanvien.getIdphongban();
                nhanvienCollectionNhanvien.setIdphongban(phongban);
                nhanvienCollectionNhanvien = em.merge(nhanvienCollectionNhanvien);
                if (oldIdphongbanOfNhanvienCollectionNhanvien != null) {
                    oldIdphongbanOfNhanvienCollectionNhanvien.getNhanvienCollection().remove(nhanvienCollectionNhanvien);
                    oldIdphongbanOfNhanvienCollectionNhanvien = em.merge(oldIdphongbanOfNhanvienCollectionNhanvien);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPhongban(phongban.getIdphongban()) != null) {
                throw new PreexistingEntityException("Phongban " + phongban + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Phongban phongban) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Phongban persistentPhongban = em.find(Phongban.class, phongban.getIdphongban());
            Collection<Nhanvien> nhanvienCollectionOld = persistentPhongban.getNhanvienCollection();
            Collection<Nhanvien> nhanvienCollectionNew = phongban.getNhanvienCollection();
            List<String> illegalOrphanMessages = null;
            for (Nhanvien nhanvienCollectionOldNhanvien : nhanvienCollectionOld) {
                if (!nhanvienCollectionNew.contains(nhanvienCollectionOldNhanvien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nhanvien " + nhanvienCollectionOldNhanvien + " since its idphongban field is not nullable.");
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
            phongban.setNhanvienCollection(nhanvienCollectionNew);
            phongban = em.merge(phongban);
            for (Nhanvien nhanvienCollectionNewNhanvien : nhanvienCollectionNew) {
                if (!nhanvienCollectionOld.contains(nhanvienCollectionNewNhanvien)) {
                    Phongban oldIdphongbanOfNhanvienCollectionNewNhanvien = nhanvienCollectionNewNhanvien.getIdphongban();
                    nhanvienCollectionNewNhanvien.setIdphongban(phongban);
                    nhanvienCollectionNewNhanvien = em.merge(nhanvienCollectionNewNhanvien);
                    if (oldIdphongbanOfNhanvienCollectionNewNhanvien != null && !oldIdphongbanOfNhanvienCollectionNewNhanvien.equals(phongban)) {
                        oldIdphongbanOfNhanvienCollectionNewNhanvien.getNhanvienCollection().remove(nhanvienCollectionNewNhanvien);
                        oldIdphongbanOfNhanvienCollectionNewNhanvien = em.merge(oldIdphongbanOfNhanvienCollectionNewNhanvien);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = phongban.getIdphongban();
                if (findPhongban(id) == null) {
                    throw new NonexistentEntityException("The phongban with id " + id + " no longer exists.");
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
            Phongban phongban;
            try {
                phongban = em.getReference(Phongban.class, id);
                phongban.getIdphongban();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The phongban with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Nhanvien> nhanvienCollectionOrphanCheck = phongban.getNhanvienCollection();
            for (Nhanvien nhanvienCollectionOrphanCheckNhanvien : nhanvienCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Phongban (" + phongban + ") cannot be destroyed since the Nhanvien " + nhanvienCollectionOrphanCheckNhanvien + " in its nhanvienCollection field has a non-nullable idphongban field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(phongban);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Phongban> findPhongbanEntities() {
        return findPhongbanEntities(true, -1, -1);
    }

    public List<Phongban> findPhongbanEntities(int maxResults, int firstResult) {
        return findPhongbanEntities(false, maxResults, firstResult);
    }

    private List<Phongban> findPhongbanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Phongban.class));
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

    public Phongban findPhongban(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Phongban.class, id);
        } finally {
            em.close();
        }
    }

    public int getPhongbanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Phongban> rt = cq.from(Phongban.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
