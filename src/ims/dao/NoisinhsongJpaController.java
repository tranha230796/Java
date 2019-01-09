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
        if (noisinhsong.getNhanvienList() == null) {
            noisinhsong.setNhanvienList(new ArrayList<Nhanvien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Nhanvien> attachedNhanvienList = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienListNhanvienToAttach : noisinhsong.getNhanvienList()) {
                nhanvienListNhanvienToAttach = em.getReference(nhanvienListNhanvienToAttach.getClass(), nhanvienListNhanvienToAttach.getIdnhanvien());
                attachedNhanvienList.add(nhanvienListNhanvienToAttach);
            }
            noisinhsong.setNhanvienList(attachedNhanvienList);
            em.persist(noisinhsong);
            for (Nhanvien nhanvienListNhanvien : noisinhsong.getNhanvienList()) {
                Noisinhsong oldIdnoisinhsongOfNhanvienListNhanvien = nhanvienListNhanvien.getIdnoisinhsong();
                nhanvienListNhanvien.setIdnoisinhsong(noisinhsong);
                nhanvienListNhanvien = em.merge(nhanvienListNhanvien);
                if (oldIdnoisinhsongOfNhanvienListNhanvien != null) {
                    oldIdnoisinhsongOfNhanvienListNhanvien.getNhanvienList().remove(nhanvienListNhanvien);
                    oldIdnoisinhsongOfNhanvienListNhanvien = em.merge(oldIdnoisinhsongOfNhanvienListNhanvien);
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
            List<Nhanvien> nhanvienListOld = persistentNoisinhsong.getNhanvienList();
            List<Nhanvien> nhanvienListNew = noisinhsong.getNhanvienList();
            List<String> illegalOrphanMessages = null;
            for (Nhanvien nhanvienListOldNhanvien : nhanvienListOld) {
                if (!nhanvienListNew.contains(nhanvienListOldNhanvien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nhanvien " + nhanvienListOldNhanvien + " since its idnoisinhsong field is not nullable.");
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
            noisinhsong.setNhanvienList(nhanvienListNew);
            noisinhsong = em.merge(noisinhsong);
            for (Nhanvien nhanvienListNewNhanvien : nhanvienListNew) {
                if (!nhanvienListOld.contains(nhanvienListNewNhanvien)) {
                    Noisinhsong oldIdnoisinhsongOfNhanvienListNewNhanvien = nhanvienListNewNhanvien.getIdnoisinhsong();
                    nhanvienListNewNhanvien.setIdnoisinhsong(noisinhsong);
                    nhanvienListNewNhanvien = em.merge(nhanvienListNewNhanvien);
                    if (oldIdnoisinhsongOfNhanvienListNewNhanvien != null && !oldIdnoisinhsongOfNhanvienListNewNhanvien.equals(noisinhsong)) {
                        oldIdnoisinhsongOfNhanvienListNewNhanvien.getNhanvienList().remove(nhanvienListNewNhanvien);
                        oldIdnoisinhsongOfNhanvienListNewNhanvien = em.merge(oldIdnoisinhsongOfNhanvienListNewNhanvien);
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
            List<Nhanvien> nhanvienListOrphanCheck = noisinhsong.getNhanvienList();
            for (Nhanvien nhanvienListOrphanCheckNhanvien : nhanvienListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Noisinhsong (" + noisinhsong + ") cannot be destroyed since the Nhanvien " + nhanvienListOrphanCheckNhanvien + " in its nhanvienList field has a non-nullable idnoisinhsong field.");
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
