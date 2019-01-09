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
        if (phongban.getNhanvienList() == null) {
            phongban.setNhanvienList(new ArrayList<Nhanvien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Nhanvien> attachedNhanvienList = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienListNhanvienToAttach : phongban.getNhanvienList()) {
                nhanvienListNhanvienToAttach = em.getReference(nhanvienListNhanvienToAttach.getClass(), nhanvienListNhanvienToAttach.getIdnhanvien());
                attachedNhanvienList.add(nhanvienListNhanvienToAttach);
            }
            phongban.setNhanvienList(attachedNhanvienList);
            em.persist(phongban);
            for (Nhanvien nhanvienListNhanvien : phongban.getNhanvienList()) {
                Phongban oldIdphongbanOfNhanvienListNhanvien = nhanvienListNhanvien.getIdphongban();
                nhanvienListNhanvien.setIdphongban(phongban);
                nhanvienListNhanvien = em.merge(nhanvienListNhanvien);
                if (oldIdphongbanOfNhanvienListNhanvien != null) {
                    oldIdphongbanOfNhanvienListNhanvien.getNhanvienList().remove(nhanvienListNhanvien);
                    oldIdphongbanOfNhanvienListNhanvien = em.merge(oldIdphongbanOfNhanvienListNhanvien);
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
            List<Nhanvien> nhanvienListOld = persistentPhongban.getNhanvienList();
            List<Nhanvien> nhanvienListNew = phongban.getNhanvienList();
            List<String> illegalOrphanMessages = null;
            for (Nhanvien nhanvienListOldNhanvien : nhanvienListOld) {
                if (!nhanvienListNew.contains(nhanvienListOldNhanvien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nhanvien " + nhanvienListOldNhanvien + " since its idphongban field is not nullable.");
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
            phongban.setNhanvienList(nhanvienListNew);
            phongban = em.merge(phongban);
            for (Nhanvien nhanvienListNewNhanvien : nhanvienListNew) {
                if (!nhanvienListOld.contains(nhanvienListNewNhanvien)) {
                    Phongban oldIdphongbanOfNhanvienListNewNhanvien = nhanvienListNewNhanvien.getIdphongban();
                    nhanvienListNewNhanvien.setIdphongban(phongban);
                    nhanvienListNewNhanvien = em.merge(nhanvienListNewNhanvien);
                    if (oldIdphongbanOfNhanvienListNewNhanvien != null && !oldIdphongbanOfNhanvienListNewNhanvien.equals(phongban)) {
                        oldIdphongbanOfNhanvienListNewNhanvien.getNhanvienList().remove(nhanvienListNewNhanvien);
                        oldIdphongbanOfNhanvienListNewNhanvien = em.merge(oldIdphongbanOfNhanvienListNewNhanvien);
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
            List<Nhanvien> nhanvienListOrphanCheck = phongban.getNhanvienList();
            for (Nhanvien nhanvienListOrphanCheckNhanvien : nhanvienListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Phongban (" + phongban + ") cannot be destroyed since the Nhanvien " + nhanvienListOrphanCheckNhanvien + " in its nhanvienList field has a non-nullable idphongban field.");
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
