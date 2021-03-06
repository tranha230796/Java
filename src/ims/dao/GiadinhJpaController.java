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
        if (giadinh.getNhanvienList() == null) {
            giadinh.setNhanvienList(new ArrayList<Nhanvien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Nhanvien> attachedNhanvienList = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienListNhanvienToAttach : giadinh.getNhanvienList()) {
                nhanvienListNhanvienToAttach = em.getReference(nhanvienListNhanvienToAttach.getClass(), nhanvienListNhanvienToAttach.getIdnhanvien());
                attachedNhanvienList.add(nhanvienListNhanvienToAttach);
            }
            giadinh.setNhanvienList(attachedNhanvienList);
            em.persist(giadinh);
            for (Nhanvien nhanvienListNhanvien : giadinh.getNhanvienList()) {
                Giadinh oldIdgiadinhOfNhanvienListNhanvien = nhanvienListNhanvien.getIdgiadinh();
                nhanvienListNhanvien.setIdgiadinh(giadinh);
                nhanvienListNhanvien = em.merge(nhanvienListNhanvien);
                if (oldIdgiadinhOfNhanvienListNhanvien != null) {
                    oldIdgiadinhOfNhanvienListNhanvien.getNhanvienList().remove(nhanvienListNhanvien);
                    oldIdgiadinhOfNhanvienListNhanvien = em.merge(oldIdgiadinhOfNhanvienListNhanvien);
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
            List<Nhanvien> nhanvienListOld = persistentGiadinh.getNhanvienList();
            List<Nhanvien> nhanvienListNew = giadinh.getNhanvienList();
            List<String> illegalOrphanMessages = null;
            for (Nhanvien nhanvienListOldNhanvien : nhanvienListOld) {
                if (!nhanvienListNew.contains(nhanvienListOldNhanvien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nhanvien " + nhanvienListOldNhanvien + " since its idgiadinh field is not nullable.");
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
            giadinh.setNhanvienList(nhanvienListNew);
            giadinh = em.merge(giadinh);
            for (Nhanvien nhanvienListNewNhanvien : nhanvienListNew) {
                if (!nhanvienListOld.contains(nhanvienListNewNhanvien)) {
                    Giadinh oldIdgiadinhOfNhanvienListNewNhanvien = nhanvienListNewNhanvien.getIdgiadinh();
                    nhanvienListNewNhanvien.setIdgiadinh(giadinh);
                    nhanvienListNewNhanvien = em.merge(nhanvienListNewNhanvien);
                    if (oldIdgiadinhOfNhanvienListNewNhanvien != null && !oldIdgiadinhOfNhanvienListNewNhanvien.equals(giadinh)) {
                        oldIdgiadinhOfNhanvienListNewNhanvien.getNhanvienList().remove(nhanvienListNewNhanvien);
                        oldIdgiadinhOfNhanvienListNewNhanvien = em.merge(oldIdgiadinhOfNhanvienListNewNhanvien);
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
            List<Nhanvien> nhanvienListOrphanCheck = giadinh.getNhanvienList();
            for (Nhanvien nhanvienListOrphanCheckNhanvien : nhanvienListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Giadinh (" + giadinh + ") cannot be destroyed since the Nhanvien " + nhanvienListOrphanCheckNhanvien + " in its nhanvienList field has a non-nullable idgiadinh field.");
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
