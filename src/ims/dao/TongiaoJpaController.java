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
import ims.dto.Tongiao;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Hades
 */
public class TongiaoJpaController implements Serializable {

    public TongiaoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tongiao tongiao) throws PreexistingEntityException, Exception {
        if (tongiao.getNhanvienList() == null) {
            tongiao.setNhanvienList(new ArrayList<Nhanvien>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Nhanvien> attachedNhanvienList = new ArrayList<Nhanvien>();
            for (Nhanvien nhanvienListNhanvienToAttach : tongiao.getNhanvienList()) {
                nhanvienListNhanvienToAttach = em.getReference(nhanvienListNhanvienToAttach.getClass(), nhanvienListNhanvienToAttach.getIdnhanvien());
                attachedNhanvienList.add(nhanvienListNhanvienToAttach);
            }
            tongiao.setNhanvienList(attachedNhanvienList);
            em.persist(tongiao);
            for (Nhanvien nhanvienListNhanvien : tongiao.getNhanvienList()) {
                Tongiao oldIdtongiaoOfNhanvienListNhanvien = nhanvienListNhanvien.getIdtongiao();
                nhanvienListNhanvien.setIdtongiao(tongiao);
                nhanvienListNhanvien = em.merge(nhanvienListNhanvien);
                if (oldIdtongiaoOfNhanvienListNhanvien != null) {
                    oldIdtongiaoOfNhanvienListNhanvien.getNhanvienList().remove(nhanvienListNhanvien);
                    oldIdtongiaoOfNhanvienListNhanvien = em.merge(oldIdtongiaoOfNhanvienListNhanvien);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTongiao(tongiao.getIdtongiao()) != null) {
                throw new PreexistingEntityException("Tongiao " + tongiao + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tongiao tongiao) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tongiao persistentTongiao = em.find(Tongiao.class, tongiao.getIdtongiao());
            List<Nhanvien> nhanvienListOld = persistentTongiao.getNhanvienList();
            List<Nhanvien> nhanvienListNew = tongiao.getNhanvienList();
            List<String> illegalOrphanMessages = null;
            for (Nhanvien nhanvienListOldNhanvien : nhanvienListOld) {
                if (!nhanvienListNew.contains(nhanvienListOldNhanvien)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Nhanvien " + nhanvienListOldNhanvien + " since its idtongiao field is not nullable.");
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
            tongiao.setNhanvienList(nhanvienListNew);
            tongiao = em.merge(tongiao);
            for (Nhanvien nhanvienListNewNhanvien : nhanvienListNew) {
                if (!nhanvienListOld.contains(nhanvienListNewNhanvien)) {
                    Tongiao oldIdtongiaoOfNhanvienListNewNhanvien = nhanvienListNewNhanvien.getIdtongiao();
                    nhanvienListNewNhanvien.setIdtongiao(tongiao);
                    nhanvienListNewNhanvien = em.merge(nhanvienListNewNhanvien);
                    if (oldIdtongiaoOfNhanvienListNewNhanvien != null && !oldIdtongiaoOfNhanvienListNewNhanvien.equals(tongiao)) {
                        oldIdtongiaoOfNhanvienListNewNhanvien.getNhanvienList().remove(nhanvienListNewNhanvien);
                        oldIdtongiaoOfNhanvienListNewNhanvien = em.merge(oldIdtongiaoOfNhanvienListNewNhanvien);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tongiao.getIdtongiao();
                if (findTongiao(id) == null) {
                    throw new NonexistentEntityException("The tongiao with id " + id + " no longer exists.");
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
            Tongiao tongiao;
            try {
                tongiao = em.getReference(Tongiao.class, id);
                tongiao.getIdtongiao();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tongiao with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Nhanvien> nhanvienListOrphanCheck = tongiao.getNhanvienList();
            for (Nhanvien nhanvienListOrphanCheckNhanvien : nhanvienListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tongiao (" + tongiao + ") cannot be destroyed since the Nhanvien " + nhanvienListOrphanCheckNhanvien + " in its nhanvienList field has a non-nullable idtongiao field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(tongiao);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tongiao> findTongiaoEntities() {
        return findTongiaoEntities(true, -1, -1);
    }

    public List<Tongiao> findTongiaoEntities(int maxResults, int firstResult) {
        return findTongiaoEntities(false, maxResults, firstResult);
    }

    private List<Tongiao> findTongiaoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tongiao.class));
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

    public Tongiao findTongiao(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tongiao.class, id);
        } finally {
            em.close();
        }
    }

    public int getTongiaoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tongiao> rt = cq.from(Tongiao.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
